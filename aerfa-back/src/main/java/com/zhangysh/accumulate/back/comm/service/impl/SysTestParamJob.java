package com.zhangysh.accumulate.back.comm.service.impl;

import com.zhangysh.accumulate.back.comm.service.ISysTestJob;
import org.springframework.stereotype.Service;

/**
 * 注释这个类是干什么的
 *
 * @author zhangysh
 * @date 2020年02月13日
 */
@Service
public class SysTestParamJob implements ISysTestJob {

    @Override
    public void testNoParam() {}

    @Override
    public void testParam(String param1, Boolean param2, Long param3, Integer param4, Double param5) {
        System.out.println("SysTestParamJob执行有参数定时任务：字符串"+param1+",布尔型"+param2);
    }
}
