package com.zhangysh.accumulate.back.ufs.dao;
import org.apache.ibatis.annotations.Mapper;

import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFile;

import java.util.List;

/**
 *@author 创建者：zhangysh
 */
@Mapper
public interface  UploadFileDao {

	/**
	 * 根据ID查询单个文件上传信息
	 *
	 * @param id 主键ID
	 * @return 文件上传信息
	 */
	AefufsUploadFile getUploadFileById(Long id);

	/**
	 * 根据条件查询文件上传列表
	 *
	 * @param uploadFile 条件对象
	 * @return 文件上传条件下的集合
	 */
	List<AefufsUploadFile> listUploadFile(AefufsUploadFile uploadFile);

	/**
	 * 根据主键ids查询文件上传
	 *
	 * @param ids 需要查询的数据ID以,拼装
	 * @return 文件上传条件下的集合
	 */
	List<AefufsUploadFile> listBypksUploadFile(String[] ids);

	/****
	 *保存AefufsUploadFile对象,并返回新增了1条数据。
	 *必须是数字类型接收，要返回对象直接在service返回保存对象，因为保存后会复制id给保存对象。要返回对象带id必须配置useGeneratedKeys="true" keyProperty="id"
	 *@param uploadFile 要保存的对象
	 *@return 影响的记录数
	 ***/
	int insertUploadFile(AefufsUploadFile uploadFile);

	/**
	 * 修改文件上传
	 *
	 * @param uploadFile 文件上传信息
	 * @return 修改结果条数
	 */
	int updateUploadFile(AefufsUploadFile uploadFile);

	/**
	 * 单个删除文件上传
	 *
	 * @param id 文件上传ID
	 * @return 删除结果条数
	 */
	int deleteUploadFileById(Long id);

	/**
	 * 批量删除文件上传
	 *
	 * @param ids 需要删除的数据ID以,拼装
	 * @return 删除结果条数
	 */
	int deleteUploadFileByIds(String[] ids);

}
