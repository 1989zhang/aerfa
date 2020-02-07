package com.zhangysh.accumulate.back.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.back.sys.dao.OrgDao;
import com.zhangysh.accumulate.back.sys.service.IOrgService;
import com.zhangysh.accumulate.common.pojo.BsTreeNode;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOrg;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysOrgVo;

/**
 *部门相关service实现
 *@author 创建者：zhangysh
 *@date 2018年7月15日
 */
@Service
public class OrgServiceImpl implements IOrgService{

	@Autowired
	private OrgDao orgDao;
	
	@Override
	public AefsysOrg getOrgById(Long id) {
		return orgDao.getOrgById(id);
	}

	@Override
	public AefsysOrgVo getOrgWithParentOrgNameById(Long id) {
		AefsysOrgVo sysOrgVo=new AefsysOrgVo();
		AefsysOrg sysOrg=getOrgById(id);
		//点击选中了部门的情况
		if(StringUtil.isNotNull(sysOrg)) {
			AefsysOrg sysParentOrg=getOrgById(sysOrg.getParentId());
			sysOrgVo=JSON.parseObject(JSON.toJSONString(sysOrg),AefsysOrgVo.class);
			sysOrgVo.setParentFullName(sysParentOrg==null?"":sysParentOrg.getFullName());
		}
		return sysOrgVo;
	}

	@Override
	public  List<AefsysOrgVo> listAllOrgWithParentStructure(){
		List<AefsysOrgVo> retOrgList=new ArrayList<AefsysOrgVo>();	
		List<AefsysOrg> topOrgList=orgDao.listTopOrg();
		for(AefsysOrg orgDo:topOrgList) {
			AefsysOrgVo orgVo=JSON.parseObject(JSON.toJSONString(orgDo),AefsysOrgVo.class);
			retOrgList.add(orgVo);
			retOrgList.addAll(listChildOrgWithParentStructureByParentId(orgVo.getId()));
		}
		return  retOrgList;
	}
	
	@Override
	public List<BsTreeNode> listAllOrgWithTreeStructure(){
		List<BsTreeNode> retList=new ArrayList<BsTreeNode>();
		List<AefsysOrg> topOrgList=orgDao.listTopOrg();
		for(AefsysOrg orgDo:topOrgList) {
			BsTreeNode vo=new BsTreeNode();	
			vo.setId(orgDo.getId()+"");
			vo.setText(orgDo.getFullName());
			vo.setNodes(listChildOrgWithTreeStructureByParentId(orgDo.getId()));
			retList.add(vo);
		}
		return retList;
	}
	
	@Override
	public List<AefsysOrgVo> listOrg(AefsysOrg org){
		List<AefsysOrgVo> retOrgList=new ArrayList<AefsysOrgVo>();
		List<AefsysOrg> dbListOrg=orgDao.listOrg(org);
		if(dbListOrg!=null && dbListOrg.size()>0) {
			for(AefsysOrg orgDo:dbListOrg) {
				AefsysOrgVo orgVo=JSON.parseObject(JSON.toJSONString(orgDo),AefsysOrgVo.class);
				retOrgList.add(orgVo);
			}
		}
		return retOrgList;
	}
	
	@Override
	public List<AefsysOrgVo> checkOrgUnique(AefsysOrg org){
		List<AefsysOrgVo> retOrgList=new ArrayList<AefsysOrgVo>();
		List<AefsysOrg> dbListOrg=orgDao.checkOrgUnique(org);
		if(dbListOrg!=null && dbListOrg.size()>0) {
			for(AefsysOrg orgDo:dbListOrg) {
				AefsysOrgVo orgVo=JSON.parseObject(JSON.toJSONString(orgDo),AefsysOrgVo.class);
				retOrgList.add(orgVo);
			}
		}
		return retOrgList;
	}
	
	@Override
	public int insertOrg(AefsysOrg org){
	    return orgDao.insertOrg(org);
	}
	
	@Override
	public int updateOrg(AefsysOrg org){
	    return orgDao.updateOrg(org);
	}
	
	@Override
	public int deleteOrgById(Long id){
		return orgDao.deleteOrgById(id);
	}
	
	/**
	 *根据父单位ID，迭代获取子组织父子形列表-列表 
	 *@param parentId 父单位ID
	 *@return 子单位列表
	 *******/
	private List<AefsysOrgVo> listChildOrgWithParentStructureByParentId(Long parentId){
		List<AefsysOrgVo> retChildListOrg=new ArrayList<AefsysOrgVo>();
		AefsysOrg org =new AefsysOrg();
		org.setParentId(parentId);
		List<AefsysOrg> dbListOrg=orgDao.listOrg(org);
		if(dbListOrg!=null && dbListOrg.size()>0) {
			for(AefsysOrg orgDo:dbListOrg) {
				AefsysOrgVo orgVo=JSON.parseObject(JSON.toJSONString(orgDo),AefsysOrgVo.class);
				retChildListOrg.add(orgVo);
				retChildListOrg.addAll(listChildOrgWithParentStructureByParentId(orgVo.getId()));
			}
		}
		return retChildListOrg;
	}
	
	/**
	 *根据父单位ID，迭代获取子组织树形列表-树
	 *@param parentId 父单位ID
	 *@return 子单位列表
	 *******/
	private List<BsTreeNode> listChildOrgWithTreeStructureByParentId(Long parentId){
		List<BsTreeNode> retChildListOrg=new ArrayList<BsTreeNode>();
		AefsysOrg org =new AefsysOrg();
		org.setParentId(parentId);
		List<AefsysOrg> dbListOrg=orgDao.listOrg(org);
		if(dbListOrg!=null && dbListOrg.size()>0) {
			for(AefsysOrg orgDo:dbListOrg) {
				BsTreeNode vo=new BsTreeNode();	
				vo.setId(orgDo.getId()+"");
				vo.setText(orgDo.getFullName());
				vo.setNodes(listChildOrgWithTreeStructureByParentId(orgDo.getId()));
				retChildListOrg.add(vo);
			}
		}
		return retChildListOrg;
	}
}
