package com.zhangysh.accumulate.back.sys.util;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJob;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（允许并发执行）
 *
 * @author zhangysh
 * @date 2020年02月13日
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, AefsysJob sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
