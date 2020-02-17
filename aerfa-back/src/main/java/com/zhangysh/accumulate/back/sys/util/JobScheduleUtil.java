package com.zhangysh.accumulate.back.sys.util;

import com.zhangysh.accumulate.back.sys.plugin.job.JobQuartzAbstract;
import com.zhangysh.accumulate.back.sys.plugin.job.JobQuartzExecution;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时任务工具类
 *
 * @author zhangysh
 * @date 2020年02月13日
 */
public class JobScheduleUtil {

    private static final Logger logger = LoggerFactory.getLogger(JobQuartzAbstract.class);

    /**
     * 获取表达式触发器
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            logger.error("getCronTrigger 异常：", e);
        }
        return null;
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, AefsysJob sysJob) throws Exception {

        Class<? extends Job> jobClass = getQuartzJobClass(sysJob);
        // 构建job信息
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(sysJob.getId())).build();

        // 表达式调度构建器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(sysJob.getCronExpression());
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(sysJob, cronScheduleBuilder);

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(sysJob.getId())).withSchedule(cronScheduleBuilder).build();

        // 放入参数，运行时的方法可以获取
        jobDetail.getJobDataMap().put(SysDefineConstant.JOB_EXECUTE_PROPERTIES, sysJob);

        // 判断是否存在
        if (scheduler.checkExists(getJobKey(sysJob.getId()))) {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(getJobKey(sysJob.getId()));
        }

        scheduler.scheduleJob(jobDetail, trigger);

        // 暂停任务
        if (sysJob.getStatus().equals(SysDefineConstant.DB_USEABLE_STATUS_INVALID)) {
            scheduler.pauseJob(JobScheduleUtil.getJobKey(sysJob.getId()));
        }
    }

    /**
     * 更新定时任务
     */
    public static void updateScheduleJob(Scheduler scheduler, AefsysJob sysJob) throws Exception {
        JobKey jobKey = getJobKey(sysJob.getId());

        // 判断是否存在
        if (scheduler.checkExists(jobKey)) {
            // 先移除，然后做更新操作
            scheduler.deleteJob(jobKey);
        }

        createScheduleJob(scheduler, sysJob);

        // 暂停任务
        if (sysJob.getStatus().equals(SysDefineConstant.DB_USEABLE_STATUS_INVALID)) {
            pauseJob(scheduler, sysJob.getId());
        }
    }

    /**
     * 立即执行任务
     */
    public static void run(Scheduler scheduler, AefsysJob sysJob) throws SchedulerException {
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(SysDefineConstant.JOB_EXECUTE_PROPERTIES, sysJob);

        scheduler.triggerJob(getJobKey(sysJob.getId()), dataMap);
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, Long jobId) throws SchedulerException {
        scheduler.pauseJob(getJobKey(jobId));
    }

    /**
     * 恢复任务等待执行
     */
    public static void resumeJob(Scheduler scheduler, Long jobId) throws SchedulerException {
        scheduler.resumeJob(getJobKey(jobId));
    }

    /**
     * 删除定时任务
     */
    public static void deleteScheduleJob(Scheduler scheduler, Long jobId) throws SchedulerException {
        scheduler.deleteJob(getJobKey(jobId));
    }



    /**
     * 得到quartz任务类
     *
     * @param sysJob 执行计划
     * @return 具体执行任务类
     */
    public static Class<? extends Job> getQuartzJobClass(AefsysJob sysJob) throws Exception{
        return JobQuartzExecution.class;
    }

    /**
     * 构建任务键对象
     */
    public static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(SysDefineConstant.JOB_EXECUTE_NAME + jobId);
    }

    /**
     * 构建任务触发对象
     */
    public static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(SysDefineConstant.JOB_EXECUTE_NAME + jobId);
    }

    /**
     * 设置定时任务策略
     */
    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(AefsysJob sysJob, CronScheduleBuilder cb) {
        switch (sysJob.getExecutePolicy()+"") {
            case SysDefineConstant.JOB_EXECUTE_POLICY_WAITING:
                return cb.withMisfireHandlingInstructionDoNothing();
            case SysDefineConstant.JOB_EXECUTE_POLICY_IMMEDIATELY:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            default:
                return cb.withMisfireHandlingInstructionDoNothing();
        }
    }

    /**
     * 返回一个布尔值代表一个给定的Cron表达式的有效性
     *
     * @param cronExpression Cron表达式
     * @return boolean 表达式是否有效
     */
    public static boolean isValid(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

}