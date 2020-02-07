package com.zhangysh.accumulate.back.iqa.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaAnswer;
import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaCategory;
import com.zhangysh.accumulate.pojo.iqa.dataobj.AefiqaQuestion;
import com.zhangysh.accumulate.pojo.iqa.transobj.AefiqaKnowledgeDto;
import com.zhangysh.accumulate.pojo.iqa.viewobj.AefiqaKnowledgeVo;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.back.iqa.dao.QuestionDao;
import com.zhangysh.accumulate.back.iqa.service.IAnswerService;
import com.zhangysh.accumulate.back.iqa.service.ICategoryService;
import com.zhangysh.accumulate.back.iqa.service.IQuestionService;
import com.zhangysh.accumulate.back.sys.base.BaseMybatisDao;
import com.zhangysh.accumulate.common.constant.IqaDefineConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
import com.zhangysh.accumulate.common.util.DateOperate;
/**
 * 提问问题 服务层实现
 * 
 * @author zhangysh
 * @date 2019年11月03日
 */
@Service
public class QuestionServiceImpl implements IQuestionService {

	@Autowired
	private IAnswerService answerService;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private QuestionDao questionDao;
    @Autowired
    private BaseMybatisDao baseMybatisDao;
	
    @Override
	public AefiqaKnowledgeVo getQuestionById(Long id){
    	AefiqaQuestion sourceQuestion=questionDao.getQuestionById(id);
    	
    	AefiqaKnowledgeVo knowledge=new AefiqaKnowledgeVo();
		BeanUtils.copyProperties(sourceQuestion, knowledge);
		knowledge.setQuestionContent(sourceQuestion.getContent());
		AefiqaAnswer answer=answerService.getAnswerById(sourceQuestion.getAnswerId());
		AefiqaCategory category=categoryService.getCategoryById(sourceQuestion.getCategoryId());
		knowledge.setAnswerContent(answer==null?"":answer.getContent());
		knowledge.setCategoryName(category==null?"":category.getCategoryName());
	    return knowledge;
	}
	
    @Override
   	public AefiqaKnowledgeVo getQuestionTogetherById(Long id) {
    	AefiqaKnowledgeVo knowledge=getQuestionById(id);
    	AefiqaQuestion searchQuestion=new AefiqaQuestion();
    	searchQuestion.setAnswerId(knowledge.getAnswerId());
    	searchQuestion.setStandard(IqaDefineConstant.QUESTION_STANDARD_NO);
    	List<AefiqaQuestion> nonstandard=listQuestion(searchQuestion);
    	knowledge.setNonstandard(nonstandard);
    	return knowledge;
    }
    
	@Override
	public BsTableDataInfo listPageQuestion(BsTablePageInfo pageInfo,AefiqaQuestion question){
	    //pagehelper方法调用
		Page<AefiqaQuestion> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		questionDao.listQuestion(question);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		List<AefiqaQuestion> listQuestion=page.getResult();
		List<AefiqaKnowledgeVo> knowledgeList=new ArrayList<AefiqaKnowledgeVo>();
		for(AefiqaQuestion sourceQuestion:listQuestion) {
			AefiqaKnowledgeVo knowledge=new AefiqaKnowledgeVo();
			BeanUtils.copyProperties(sourceQuestion, knowledge);
			knowledge.setQuestionContent(sourceQuestion.getContent());
			AefiqaAnswer answer=answerService.getAnswerById(sourceQuestion.getAnswerId());
			AefiqaCategory category=categoryService.getCategoryById(sourceQuestion.getCategoryId());
			knowledge.setAnswerContent(answer==null?"":answer.getContent());
			knowledge.setCategoryName(category==null?"":category.getCategoryName());
			knowledgeList.add(knowledge);
		}
		tableInfo.setRows(knowledgeList);
	    return tableInfo; 
	}

	@Override
	public List<AefiqaKnowledgeVo> listBypksQuestion(String ids){
		List<AefiqaQuestion> listQuestion=questionDao.listBypksQuestion(ConvertUtil.toStrArray(ids));
		
		List<AefiqaKnowledgeVo> knowledgeList=new ArrayList<AefiqaKnowledgeVo>();
		for(AefiqaQuestion sourceQuestion:listQuestion) {
			AefiqaKnowledgeVo knowledge=new AefiqaKnowledgeVo();
			BeanUtils.copyProperties(sourceQuestion, knowledge);
			knowledge.setQuestionContent(sourceQuestion.getContent());
			AefiqaAnswer answer=answerService.getAnswerById(sourceQuestion.getAnswerId());
			AefiqaCategory category=categoryService.getCategoryById(sourceQuestion.getCategoryId());
			knowledge.setAnswerContent(answer==null?"":answer.getContent());
			knowledge.setCategoryName(category==null?"":category.getCategoryName());
			knowledgeList.add(knowledge);
		}
		return knowledgeList;
	}

	@Override
	public List<AefiqaQuestion> listQuestion(AefiqaQuestion question){
		return questionDao.listQuestion(question);
	}

	@Override
	public int insertQuestion(AefiqaQuestion question){
	    return questionDao.insertQuestion(question);
	}
	
	@Override
	public int updateQuestion(AefiqaQuestion question){
	    return questionDao.updateQuestion(question);
	}
	
	@Override
	public int deleteQuestionById(Long id){
		return questionDao.deleteQuestionById(id);
	}
	
	@Override
	public int deleteQuestionByIds(String ids){
		return questionDao.deleteQuestionByIds(ConvertUtil.toStrArray(ids));
	}
	
	@Override
	@Transactional
	public int deleteTogetherStandardByIds(String ids) {
		String[] idArr=ConvertUtil.toStrArray(ids);
		for(int i=0;i<idArr.length;i++) {
			Long questionId=Long.parseLong(idArr[i]);
			AefiqaQuestion sourceQuestion=questionDao.getQuestionById(questionId);
			Long answerId=sourceQuestion.getAnswerId();
			//先删除回答
			answerService.deleteAnswerById(answerId);
			//再删除标准问题和非标准问题
			String deleteQuestionSql="delete from aefiqa_question where answer_id="+answerId;
			baseMybatisDao.deleteBySql(deleteQuestionSql);
		}
		return idArr.length;
	}
	
	@Override
	@Transactional
	public int insertKnowledgeInfo(AefsysPerson operPerson,AefiqaKnowledgeDto knowledgeDto) {
		//新增答案并获取出答案id
		AefiqaAnswer answer=new AefiqaAnswer();
		answer.setCreateTime(DateOperate.getCurrentUtilDate());
		answer.setCreateBy(operPerson.getPersonName());
		answer.setContent(knowledgeDto.getAnswerContent());
		answer.setCategoryId(knowledgeDto.getCategoryId());
		answer.setBelongOrgId(operPerson.getOrgId());
		answerService.insertAnswer(answer);
		Long answerId=answer.getId();
		//新增标准问法
		AefiqaQuestion standardQuestion=new AefiqaQuestion();
		standardQuestion.setAnswerId(answerId);
		standardQuestion.setBelongOrgId(operPerson.getOrgId());
		standardQuestion.setCategoryId(knowledgeDto.getCategoryId());
		standardQuestion.setContent(knowledgeDto.getQuestionContent());
		standardQuestion.setCreateTime(DateOperate.getCurrentUtilDate());
		standardQuestion.setCreateBy(operPerson.getPersonName());
		standardQuestion.setStandard(IqaDefineConstant.QUESTION_STANDARD_YES);
		questionDao.insertQuestion(standardQuestion);
		//新增非标准问法
		for(int i=0;i<knowledgeDto.getUnStandardQuestionContent().length;i++) {
			//新增标准问法
			AefiqaQuestion unStandardQuestion=new AefiqaQuestion();
			unStandardQuestion.setAnswerId(answerId);
			unStandardQuestion.setBelongOrgId(operPerson.getOrgId());
			unStandardQuestion.setCategoryId(knowledgeDto.getCategoryId());
			unStandardQuestion.setContent(knowledgeDto.getUnStandardQuestionContent()[i]);
			unStandardQuestion.setCreateTime(DateOperate.getCurrentUtilDate());
			unStandardQuestion.setCreateBy(operPerson.getPersonName());
			unStandardQuestion.setStandard(IqaDefineConstant.QUESTION_STANDARD_NO);
			questionDao.insertQuestion(unStandardQuestion);
		}
		return knowledgeDto.getUnStandardQuestionContent().length+1;
	}
	 
	@Override
	@Transactional
	public int updateKnowledgeInfo(AefsysPerson operPerson,AefiqaKnowledgeDto knowledgeDto) {
		return 1;
	}
}
