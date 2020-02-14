package com.zhangysh.accumulate.back.sys.util;

import java.util.Date;

import com.zhangysh.accumulate.back.sys.service.IJobLogService;
import com.zhangysh.accumulate.common.constant.LogConstant;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJob;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJobLog;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * 抽象quartz调用
 *
 * @author zhangysh
 * @date 2020年02月13日
 */
public abstract class JobQuartzAbstract implements Job {

    private static final Logger logger = LoggerFactory.getLogger(JobQuartzAbstract.class);

    /**
     * 线程本地变量
     */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<Date>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        AefsysJob sysJob = new AefsysJob();
        BeanUtils.copyProperties(context.getMergedJobDataMap().get(SysDefineConstant.JOB_EXECUTE_PROPERTIES),sysJob);
        try {
            before(context, sysJob);
            if (sysJob != null) {
                doExecute(context, sysJob);
            }
            after(context, sysJob, null);
        } catch (Exception e) {
            after(context, sysJob, e);
        }
    }

    /**
     * 执行前设置个执行时间，方便统计总共耗时
     *
     * @param context 工作执行上下文对象
     * @param sysJob 系统计划任务
     */
    protected void before(JobExecutionContext context, AefsysJob sysJob) {
        threadLocal.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context 工作执行上下文对象
     * @param sysJob 系统计划任务
     */
    protected void after(JobExecutionContext context, AefsysJob sysJob, Exception e) {
        //获取执行开始时间
        Date startTime = threadLocal.get();
        threadLocal.remove();

        final AefsysJobLog sysJobLog = new AefsysJobLog();
        sysJobLog.setCreateTime(startTime);
        sysJobLog.setJobId(sysJob.getId());
        sysJobLog.setJobName(sysJob.getJobName());
        sysJobLog.setInvokeTarget(sysJob.getInvokeTarget());

        //计算耗时
        long costTimeMs = new Date().getTime() - startTime.getTime();
        sysJobLog.setCostTime(costTimeMs);
        if (e != null) {//有异常执行失败
            sysJobLog.setExecuteStatus(SysDefineConstant.DIC_RESULT_STATUS_FAIL);
            sysJobLog.setLogType(LogConstant.LOG_TYPE_ERROR);
            sysJobLog.setLogContent(StringUtil.substring(e.getMessage(), 0, 2000));
            logger.error("execute 任务执行异常：", e);
        } else {
            sysJobLog.setExecuteStatus(SysDefineConstant.DIC_RESULT_STATUS_SUCESS);
            sysJobLog.setLogType(LogConstant.LOG_TYPE_INFO);
            sysJobLog.setLogContent(sysJob.getJobName()+"执行成功总共耗时：" + costTimeMs + "毫秒");
            logger.error("execute 任务执行成功。");
        }

        // 写入数据库当中
        SpringContextUtil.getBean(IJobLogService.class).insertJobLog(sysJobLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context 工作执行上下文对象
     * @param sysJob 系统计划任务
     * @throws Exception 执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, AefsysJob sysJob) throws Exception;
}
