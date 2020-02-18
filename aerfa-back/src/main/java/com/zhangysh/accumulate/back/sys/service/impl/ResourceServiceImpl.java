package com.zhangysh.accumulate.back.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
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
		List<AefsysResource> dbListResource=resourceDao.listResource(resource);
		if(dbListResource!=null && dbListResource.size()>0) {
			for(AefsysResource resourceDo:dbListResource) {
				AefsysResourceVo resourceVo=JSON.parseObject(JSON.toJSONString(resourceDo),AefsysResourceVo.class);
				retResourceList.add(resourceVo);
			}
		}
		return retResourceList;
	}
	
	@Override
    public List<AefsysResourceVo> listAllResourceWithParentStructure(){
		List<AefsysResourceVo> retResourceList=new ArrayList<AefsysResourceVo>();	
		List<AefsysResource> topResourceList=resourceDao.listTopResource();
		for(AefsysResource resourceDo:topResourceList) {
			AefsysResourceVo resourceVo=JSON.parseObject(JSON.toJSONString(resourceDo),AefsysResourceVo.class);
			retResourceList.add(resourceVo);
			retResourceList.addAll(listChildResourceWithParentStructureByParentId(resourceVo.getId()));
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
	public List<AefsysResource> getPersonResourcesByPersonId(Long personId){
		return resourceDao.getPersonResourcesByPersonId(personId);
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
	private List<AefsysResourceVo> listChildResourceWithParentStructureByParentId(Long parentId){
		List<AefsysResourceVo> retChildListResource=new ArrayList<AefsysResourceVo>();
		AefsysResource resource =new AefsysResource();
		resource.setParentId(parentId);
		List<AefsysResource> dbListResource=resourceDao.listResource(resource);
		if(dbListResource!=null && dbListResource.size()>0) {
			for(AefsysResource resourceDo:dbListResource) {
				AefsysResourceVo resourceVo=JSON.parseObject(JSON.toJSONString(resourceDo),AefsysResourceVo.class);
				retChildListResource.add(resourceVo);
				retChildListResource.addAll(listChildResourceWithParentStructureByParentId(resourceVo.getId()));
			}
		}
		return retChildListResource;
	}
}
