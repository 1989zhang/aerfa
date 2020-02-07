package com.zhangysh.accumulate.back.iqa.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaAnswer;
import com.zhangysh.accumulate.back.iqa.dao.AnswerDao;
import com.zhangysh.accumulate.back.iqa.service.IAnswerService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
/**
 * 解答回答 服务层实现
 * 
 * @author zhangysh
 * @date 2019年11月03日
 */
@Service
public class AnswerServiceImpl implements IAnswerService {

	@Autowired
	private AnswerDao answerDao;

    @Override
	public AefiqaAnswer getAnswerById(Long id){
	    return answerDao.getAnswerById(id);
	}
	
	@Override
	public BsTableDataInfo listPageAnswer(BsTablePageInfo pageInfo,AefiqaAnswer answer){
	    //pagehelper方法调用
		Page<AefiqaAnswer> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		answerDao.listAnswer(answer);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefiqaAnswer> listBypksAnswer(String ids){
		return answerDao.listBypksAnswer(ConvertUtil.toStrArray(ids));
	}

	@Override
	public List<AefiqaAnswer> listAnswer(AefiqaAnswer answer){
		return answerDao.listAnswer(answer);
	}

	@Override
	public int insertAnswer(AefiqaAnswer answer){
	    return answerDao.insertAnswer(answer);
	}
	
	@Override
	public int updateAnswer(AefiqaAnswer answer){
	    return answerDao.updateAnswer(answer);
	}
	
	@Override
	public int deleteAnswerById(Long id){
		return answerDao.deleteAnswerById(id);
	}
	
	@Override
	public int deleteAnswerByIds(String ids){
		return answerDao.deleteAnswerByIds(ConvertUtil.toStrArray(ids));
	}
	
}
