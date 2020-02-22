package com.zhangysh.accumulate.back.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zhangysh.accumulate.back.sys.service.IRoleService;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.back.sys.dao.ResourceDao;
import com.zhangysh.accumulate.back.sys.service.IResourceService;
import com.zhangysh.accumulate.common.pojo.BsTreeNode;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysResource;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysResourceVo;

/*****
 * 资源相关service接口实现
 * @author zhangysh
 * @date 2019年4月7日
 *****/
@Service
public class ResourceServiceImpl implements IResourceService{

	@Autowired
	private IRoleService roleService;
	@Autowired
	private ResourceDao resourceDao;
	
	@Override
    public AefsysResource getResourceById(Long id) {
		return resourceDao.getResourceById(id);
    }

	@Override
    public AefsysResourceVo getResourceWithParentResourceNameById(Long id) {
		AefsysResource sysResource=resourceDao.getResourceById(id);
		AefsysResource sysParentResource=resourceDao.getResourceById(sysResource.getParentId());
		AefsysResourceVo sysResourceVo=JSON.parseObject(JSON.toJSONString(sysResource),AefsysResourceVo.class);
		sysResourceVo.setParentName(sysParentResource==null?"":sysParentResource.getResourceName());
		return sysResourceVo;
	}
	
	@Override
    public List<BsTreeNode> listAllResourceWithTreeStructure(){
		List<BsTreeNode> retList=new ArrayList<BsTreeNode>();
		List<AefsysResource> topResourceList=resourceDao.listTopResource();
		for(AefsysResource resourceDo:topResourceList) {
			BsTreeNode vo=new BsTreeNode();	
			vo.setId(resourceDo.getId()+"");
			vo.setText(resourceDo.getResourceName());
			vo.setNodes(listChildResourceWithTreeStructureByParentId(resourceDo.getId()));
			retList.add(vo);
		}
		return retList;
	}

	@Override
    public List<AefsysResourceVo> listResource(AefsysResource resource){
		List<AefsysResourceVo> retResourceList=new ArrayList<AefsysResourceVo>();
		List<AefsysResource> dbListResource=listSearchResource(resource);
		if(dbListResource!=null && dbListResource.size()>0) {
			for(AefsysResource resourceDo:dbListResource) {
				AefsysResourceVo resourceVo=JSON.parseObject(JSON.toJSONString(resourceDo),AefsysResourceVo.class);
				retResourceList.add(resourceVo);
			}
		}
		return retResourceList;
	}
	
	@Override
    public List<AefsysResourceVo> listAllResourceWithParentResource(){
		List<AefsysResourceVo> retResourceList=new ArrayList<AefsysResourceVo>();	
		List<AefsysResource> topResourceList=resourceDao.listTopResource();
		for(AefsysResource resourceDo:topResourceList) {
			AefsysResourceVo resourceVo=JSON.parseObject(JSON.toJSONString(resourceDo),AefsysResourceVo.class);
			retResourceList.add(resourceVo);
			retResourceList.addAll(listChildResourceWithParentResourceByParentId(resourceVo.getId()));
		}
		return  retResourceList;
	}
	
	@Override
    public List<AefsysResourceVo> checkResourceUnique(AefsysResource resource){
		List<AefsysResourceVo> retResourceList=new ArrayList<AefsysResourceVo>();
		List<AefsysResource> dbListResource=resourceDao.checkResourceUnique(resource);
		if(dbListResource!=null && dbListResource.size()>0) {
			for(AefsysResource resourceDo:dbListResource) {
				AefsysResourceVo resourceVo=JSON.parseObject(JSON.toJSONString(resourceDo),AefsysResourceVo.class);
				retResourceList.add(resourceVo);
			}
		}
		return retResourceList;
	}
	
	@Override
    public int insertResource(AefsysResource resource) {
		return resourceDao.insertResource(resource);
	}
	
	@Override
    public int updateResource(AefsysResource resource) {
		return resourceDao.updateResource(resource);
	}
	
	@Override
    public int deleteResourceById(Long id) {
		return resourceDao.deleteResourceById(id);
	}

	@Override
	public List<AefsysResourceVo> getPersonStructResourcesByPersonId(Long personId) {
		List<AefsysResource> directResourceList=getDirectResourcesByPersonId(personId);
		List<AefsysResource> allRepeatPersonResource = new ArrayList<AefsysResource>();
		//首先倒序查询出按钮菜单模块所有的资源集合
		for (AefsysResource resource : directResourceList) {
			allRepeatPersonResource.add(resource);
			allRepeatPersonResource.addAll(listParentResourceWithDirectStructureByChirdId(resource.getId()));
		}
		//权限资源进行去重处理
		List<AefsysResource> allPersonResource=new ArrayList<AefsysResource>();
		for(AefsysResource sourceResource:allRepeatPersonResource){
			boolean repeat=false;
			for(AefsysResource resource:allPersonResource){
				if(resource.getId().equals(sourceResource.getId())){
					repeat=true;
				}
			}
			if(!repeat){
				allPersonResource.add(sourceResource);
			}
		}
		//首先找出最大的根节点
		List<AefsysResource> topResourceList=new ArrayList<AefsysResource>();
		for(AefsysResource resource:allPersonResource){
           if(SysDefineConstant.DIC_RESOURCE_TYPE_SYSTEM.equals(resource.getResourceType())){
			   topResourceList.add(resource);
		   }
		}
		//根据根节点找下级节点
		List<AefsysResourceVo> retResourceVoList=new ArrayList<AefsysResourceVo>();
		for(AefsysResource topResource:topResourceList){
			AefsysResourceVo topResourceVo=JSON.parseObject(JSON.toJSONString(topResource),AefsysResourceVo.class);
			topResourceVo.setChildren(getChildStructResources(topResource,allPersonResource));
			retResourceVoList.add(topResourceVo);
		}
		return retResourceVoList;
	}

	@Override
	public List<AefsysResource> getDirectResourcesByPersonId(Long personId){
		List<AefsysResource> directResources=new ArrayList<AefsysResource>();
		//超级管理员添加所有资源
		if(SysDefineConstant.PERSON_ID_SUPERMASTER.equals(personId)){
			List<AefsysRole> roleList=roleService.getPersonRolesByPersonId(personId);
			boolean isSuperAdmin=false;//是否超级管理员判断
			for(AefsysRole role:roleList){
				if(SysDefineConstant.ROLE_ID_SUPERADMIN.equals(role.getId())){
					isSuperAdmin=true;
				}
			}
			if(isSuperAdmin){
				//可以直接查询所有资源，但是资源固定到菜单和按钮貌似好一些
				AefsysResource searchMenuResource=new AefsysResource();
				searchMenuResource.setResourceType(SysDefineConstant.DIC_RESOURCE_TYPE_MENU);
				List<AefsysResource> ListMenuResource=listSearchResource(searchMenuResource);
				AefsysResource searchButtonResource=new AefsysResource();
				searchButtonResource.setResourceType(SysDefineConstant.DIC_RESOURCE_TYPE_BUTTON);
				List<AefsysResource> ListButtonResource=listSearchResource(searchButtonResource);
				directResources.addAll(ListMenuResource);//添加资源
				directResources.addAll(ListButtonResource);//添加资源
			}
			//普通权限人员
		}else{
			List<AefsysResource> personRoleResource = resourceDao.getPersonResourcesByPersonId(personId);
			List<AefsysResource> noAuthorityResource=getNoAuthorityResource();//无权限控制的菜单
			directResources.addAll(personRoleResource);//所有可展示的资源
			directResources.addAll(noAuthorityResource);//所有可展示的资源
		}
		return directResources;
	}



	/**
	 *根据父资源ID，迭代获取子资源树形列表-树
	 *@param parentId 父资源主键
	 *@return 子资源列表
	 *******/
	private List<BsTreeNode> listChildResourceWithTreeStructureByParentId(Long parentId){
		List<BsTreeNode> retChildListOrg=new ArrayList<BsTreeNode>();
		AefsysResource resource =new AefsysResource();
		resource.setParentId(parentId);
		List<AefsysResource> dbListResource=resourceDao.listResource(resource);
		if(dbListResource!=null && dbListResource.size()>0) {
			for(AefsysResource resourceDo:dbListResource) {
				BsTreeNode vo=new BsTreeNode();	
				vo.setId(resourceDo.getId()+"");
				vo.setText(resourceDo.getResourceName());
				vo.setNodes(listChildResourceWithTreeStructureByParentId(resourceDo.getId()));
				retChildListOrg.add(vo);
			}
		}
		return retChildListOrg;
	}
	
	/**
	 *根据父资源ID，迭代获取子资源父子形列表-列表 
	 *@param parentId 父资源ID
	 *@return 子资源列表
	 *******/
	private List<AefsysResourceVo> listChildResourceWithParentResourceByParentId(Long parentId){
		List<AefsysResourceVo> retChildListResource=new ArrayList<AefsysResourceVo>();
		AefsysResource resource =new AefsysResource();
		resource.setParentId(parentId);
		List<AefsysResource> dbListResource=resourceDao.listResource(resource);
		if(dbListResource!=null && dbListResource.size()>0) {
			for(AefsysResource resourceDo:dbListResource) {
				AefsysResourceVo resourceVo=JSON.parseObject(JSON.toJSONString(resourceDo),AefsysResourceVo.class);
				retChildListResource.add(resourceVo);
				retChildListResource.addAll(listChildResourceWithParentResourceByParentId(resourceVo.getId()));
			}
		}
		return retChildListResource;
	}

	/**
	 * 根据子资源的ID 循环获取父资源集合，不分结构不分顺序
	 *
	 **/
	private List<AefsysResource> listParentResourceWithDirectStructureByChirdId(Long chirdId){
		List<AefsysResource> retParentListResource=new ArrayList<AefsysResource>();
		AefsysResource childResource=getResourceById(chirdId);
		AefsysResource parentResource=getResourceById(childResource.getParentId());
		if(StringUtil.isNotNull(parentResource)){
			retParentListResource.add(parentResource);
			retParentListResource.addAll(listParentResourceWithDirectStructureByChirdId(parentResource.getId()));
		}
		return retParentListResource;
	}

	/***
	 * 根据父资源和有权限的所有资源构建结构
	 ***/
	private List<AefsysResourceVo> getChildStructResources(AefsysResource parentResource,List<AefsysResource> allResource){
		List<AefsysResourceVo> childResourceList=new ArrayList<AefsysResourceVo>();
		for(AefsysResource resource:allResource){
			if(parentResource.getId().equals(resource.getParentId())) {
				AefsysResourceVo childResource=JSON.parseObject(JSON.toJSONString(resource), AefsysResourceVo.class);
				childResource.setChildren(getChildStructResources(childResource,allResource));
				childResourceList.add(childResource);
			}
		}
    	return childResourceList;
	}

	/**
	 * 获取无权限控制的直接资源，不管有效状态
	 * 只获取到menu类型的，不管子button类型才符合逻辑
	 ***/
	private List<AefsysResource> getNoAuthorityResource(){
		AefsysResource searchResource=new AefsysResource();
		searchResource.setResourceType(SysDefineConstant.DIC_RESOURCE_TYPE_MENU);
		searchResource.setAuthority(SysDefineConstant.DIC_COMMON_STATUS_NO);
		return listSearchResource(searchResource);
	}

	/****
	 * 根据条件查询出资源集合不分页显示
	 * @param resource 查询条件
	 ****/
	private List<AefsysResource> listSearchResource(AefsysResource resource){
		return resourceDao.listResource(resource);
	}

}
