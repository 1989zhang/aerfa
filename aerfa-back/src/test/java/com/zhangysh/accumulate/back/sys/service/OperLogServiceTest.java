package com.zhangysh.accumulate.back.sys.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOperLog;

/**
 *日志相关测试
 *@author 创建者：zhangysh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OperLogServiceTest {
	
	@Autowired
	private IOperLogService operLogService;
	
	@Test
	public void TestMain() {
		AefsysOperLog operLog=new AefsysOperLog();
		operLog.setLogType("info");
        operLog.setOperTime(DateOperate.getCurrentUtilDate());
		operLogService.insertOperLog(operLog);
	}
}
