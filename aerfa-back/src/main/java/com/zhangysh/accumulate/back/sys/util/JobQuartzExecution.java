package com.zhangysh.accumulate.back.sys.util;

import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysJob;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理
 *
 * @author zhangysh
 * @date 2020年02月13日
 */
public class JobQuartzExecution extends JobQuartzAbstract {
    @Override
    protected void doExecute(JobExecutionContext context, AefsysJob sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
