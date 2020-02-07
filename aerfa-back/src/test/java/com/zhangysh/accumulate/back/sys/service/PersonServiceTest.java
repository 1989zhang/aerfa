package com.zhangysh.accumulate.back.sys.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.util.DateOperate;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOrg;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysPersonVo;
/**
 *人员相关测试：测试路径要和service路径保持严格一致
 *@author 创建者：zhangysh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {
	@Autowired
	private IPersonService personService;
	@Autowired
	private IOrgService orgService;

	@Test
	@SuppressWarnings("unchecked")
	public void TestMain() {

		/*AefsysPerson person=personService.getPersonById(1L);
		System.out.println(person.toString());*/
		
		/*AefsysPerson person=new AefsysPerson();
		//person.setPersonName("li");
		Map<String,Object> paraMap=new HashMap<String,Object>();
		//paraMap.put("startTime", "2018-09-01");
		//paraMap.put("endTime", "2018-09-15");
		person.setParams(paraMap);
		BsTablePageInfo pageInfo=new BsTablePageInfo();
		pageInfo.setPageNum(2);
		pageInfo.setPageSize(2);
		pageInfo.setOrderByColumn("id");
		pageInfo.setIsAsc("asc");
		
		BsTableDataInfo tableInfo=personService.listPagePersons(pageInfo,person);
		System.out.println("总条数："+tableInfo.getTotal());
		List<AefsysPerson> retList=(List<AefsysPerson>)tableInfo.getRows();
		for(AefsysPerson personShow:retList) {
			System.out.println(personShow.getPersonName());
		}*/
	}
}
