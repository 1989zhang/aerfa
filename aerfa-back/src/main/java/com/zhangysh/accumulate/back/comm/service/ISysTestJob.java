package com.zhangysh.accumulate.back.comm.service;

/**
 * 注释这个类是测试后台定时任务用的，前台通过job页面维护执行方式
 *
 * @author zhangysh
 * @date 2020年02月13日
 */
public interface ISysTestJob {

    /**
     *测试不带参数的定时任务
     ***/
    void testNoParam();

    /**
     * 测试带参数的定时任务
     *
     ***/
    void testParam(String param1,Boolean param2,Long param3,Integer param4,Double param5);
}
