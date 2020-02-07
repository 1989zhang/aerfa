package com.zhangysh.accumulate.back.adi.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangysh.accumulate.back.adi.dydatasource.DynamicSqlRepository;
import com.zhangysh.accumulate.pojo.adi.dataobj.AefadiDataSource;

/**
 *@author 创建者：zhangysh
 */
@Service
public class TestPersonServiceImpl implements ITestPersonService{

	@Autowired
	private DynamicSqlRepository dynamicSqlRepository;

	@Override
	public String getPersonInfo() {
		try {
			AefadiDataSource adiDataSource=new AefadiDataSource();
			adiDataSource.setId(1111L);
			adiDataSource.setCode("mysqlconn");
			adiDataSource.setName("mysql数据库连接");
			adiDataSource.setUsername("userzhang");
			adiDataSource.setPassword("123456");
			adiDataSource.setUrl("jdbc:mysql://192.168.195.50:3306/aerfa?useUnicode=true&serverTimezone=Asia/Shanghai&useSSL=false");
			adiDataSource.setType("mysql");
			String sql="select * from aeftdm_template";
			List<Map<String, Object>> retListMap=dynamicSqlRepository.doSelect(adiDataSource, sql, null);
			System.out.println(retListMap.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
