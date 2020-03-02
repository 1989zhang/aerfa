package com.zhangysh.accumulate.back.iqa.service.impl;
import java.util.ArrayList;
import java.util.List;

import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.util.StringUtil;
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
    	if(StringUtil.isNull(id)){return null;}
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
		List<AefiqaQuestion> nonstandard=new ArrayList<AefiqaQuestion>();
    	//有回答才有非标准问法，没有回答肯定没非标准回答
		if(StringUtil.isNotNull(knowledge.getAnswerId())){
			AefiqaQuestion searchQuestion=new AefiqaQuestion();
			searchQuestion.setAnswerId(knowledge.getAnswerId());
			searchQuestion.setStandard(SysDefineConstant.DIC_COMMON_STATUS_NO);
			nonstandard=listQuestion(searchQuestion);
		}
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
	public List<AefiqaQuestion> listMatchContentQuestion(AefiqaQuestion question){
    	return questionDao.listMatchContentQuestion(question);
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
			//没有回答的问题也要删除
			questionDao.deleteQuestionById(questionId);
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
		Long answerId=dealWithAnswerByKnowledgeDto(operPerson,knowledgeDto);
		//新增标准问法
		dealWithQuestionByKnowledgeDto(operPerson,knowledgeDto,answerId,knowledgeDto.getQuestionContent(),SysDefineConstant.DIC_COMMON_STATUS_YES);
		//新增非标准问法
		for(int i=0;i<knowledgeDto.getUnStandardQuestionContent().length;i++) {
			//新增标准问法
			dealWithQuestionByKnowledgeDto(operPerson,knowledgeDto,answerId,knowledgeDto.getUnStandardQuestionContent()[i],SysDefineConstant.DIC_COMMON_STATUS_NO);
		}
		return knowledgeDto.getUnStandardQuestionContent().length+1;
	}
	 
	@Override
	@Transactional
	public int updateKnowledgeInfo(AefsysPerson operPerson,AefiqaKnowledgeDto knowledgeDto) {
		Long questionId=knowledgeDto.getId();
		Long answerId=dealWithAnswerByKnowledgeDto(operPerson,knowledgeDto);
		//再修改标准问法，
		AefiqaQuestion standardQuestion=new AefiqaQuestion();
		standardQuestion.setId(questionId);
		standardQuestion.setAnswerId(answerId);
		standardQuestion.setCategoryId(knowledgeDto.getCategoryId());
		standardQuestion.setBelongOrgId(operPerson.getOrgId());
		standardQuestion.setContent(knowledgeDto.getQuestionContent());
		standardQuestion.setUpdateTime(DateOperate.getCurrentUtilDate());
		standardQuestion.setUpdateBy(operPerson.getPersonName());
		questionDao.updateQuestion(standardQuestion);
		//再删除非标准问法新增非标准问法
		String deleteQuestionSql="delete from aefiqa_question where answer_id="+answerId +" and standard="+SysDefineConstant.DIC_COMMON_STATUS_NO;
		baseMybatisDao.deleteBySql(deleteQuestionSql);
		//新增非标准问法
		for(int i=0;i<knowledgeDto.getUnStandardQuestionContent().length;i++) {
			dealWithQuestionByKnowledgeDto(operPerson,knowledgeDto,answerId,knowledgeDto.getUnStandardQuestionContent()[i],SysDefineConstant.DIC_COMMON_STATUS_NO);
		}
		return knowledgeDto.getUnStandardQuestionContent().length+1;
	}

	/**
	 * 根据传入参数处理答案对象，并返回答案的ID
	 * @param knowledgeDto 包含各种的知识传输对象
	 * @param operPerson 操作者对象
	 * @return
	 ***/
	public Long dealWithAnswerByKnowledgeDto(AefsysPerson operPerson,AefiqaKnowledgeDto knowledgeDto){
		Long questionId=knowledgeDto.getId();
		AefiqaKnowledgeVo knowledgeVo=getQuestionById(questionId);
		//此处可能是修改答案，也可能是新增答案，
		Long answerId=knowledgeVo==null?null:knowledgeVo.getAnswerId();
		AefiqaAnswer answer=new AefiqaAnswer();
		answer.setCategoryId(knowledgeDto.getCategoryId());
		answer.setBelongOrgId(operPerson.getOrgId());
		answer.setContent(knowledgeDto.getAnswerContent());
		if(StringUtil.isNotNull(answerId)){
			answer.setId(answerId);
			answer.setUpdateTime(DateOperate.getCurrentUtilDate());
			answer.setUpdateBy(operPerson.getPersonName());
			answerService.updateAnswer(answer);
		}else{
			answer.setCreateTime(DateOperate.getCurrentUtilDate());
			answer.setCreateBy(operPerson.getPersonName());
			answerService.insertAnswer(answer);
			answerId=answer.getId();
		}
		return answerId;
	}

	/**
	 * 根据传入参数处理问题对象
	 */
	public Long dealWithQuestionByKnowledgeDto(AefsysPerson operPerson,AefiqaKnowledgeDto knowledgeDto,Long answerId,String questionContent,Long standardTar){
		//新增标准问法
		AefiqaQuestion question=new AefiqaQuestion();
		question.setAnswerId(answerId);
		question.setBelongOrgId(operPerson.getOrgId());
		question.setCategoryId(knowledgeDto.getCategoryId());
		question.setContent(questionContent);
		question.setCreateTime(DateOperate.getCurrentUtilDate());
		question.setCreateBy(operPerson.getPersonName());
		question.setStandard(standardTar);
		questionDao.insertQuestion(question);
		return question.getId();
	}
}
