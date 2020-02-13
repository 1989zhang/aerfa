package com.zhangysh.accumulate.back.comm.service.impl;

import com.zhangysh.accumulate.back.comm.service.ISysTestJob;
import org.springframework.stereotype.Service;

/**
 * 注释这个类是干什么的
 *
 * @author zhangysh
 * @date 2020年02月13日
 */
@Service("thisIsTestJob")
public class SysTestNoParamJob implements ISysTestJob {

    @Override
    public void testNoParam() {
        System.out.println("SysTestNoParamJob执行无参数定时任务");
    }

    @Override
    public void testParam(String param1, Boolean param2, Long param3, Integer param4, Double param5) {}
}
