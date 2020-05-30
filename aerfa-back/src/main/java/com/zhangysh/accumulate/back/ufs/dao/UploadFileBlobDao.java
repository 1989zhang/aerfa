package com.zhangysh.accumulate.back.ufs.dao;
import org.apache.ibatis.annotations.Mapper;
import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFileBlob;

import java.util.List;

/**
 *@author 创建者：zhangysh
 */
@Mapper
public interface  UploadFileBlobDao {

	/**
	 * 根据ID查询单个二进制内容文件上传信息
	 *
	 * @param id 主键ID
	 * @return 二进制内容文件上传信息
	 */
	AefufsUploadFileBlob getUploadFileBlobById(Long id);

	/**
	 * 根据条件查询二进制内容文件上传列表
	 *
	 * @param uploadFileBlob 条件对象
	 * @return 二进制内容文件上传条件下的集合
	 */
	List<AefufsUploadFileBlob> listUploadFileBlob(AefufsUploadFileBlob uploadFileBlob);

	/**
	 * 根据主键ids查询二进制内容文件上传
	 *
	 * @param ids 需要查询的数据ID以,拼装
	 * @return 二进制内容文件上传条件下的集合
	 */
	List<AefufsUploadFileBlob> listBypksUploadFileBlob(String[] ids);

	/****
	 *保存AefufsUploadFileBlob对象,并返回新增了1条数据。
	 *必须是数字类型接收，要返回对象直接在service返回保存对象，因为保存后会复制id给保存对象。要返回对象带id必须配置useGeneratedKeys="true" keyProperty="id"
	 *@param uploadFileBlob 要保存的对象
	 *@return 影响的记录数
	 ***/
	int insertUploadFileBlob(AefufsUploadFileBlob uploadFileBlob);

	/**
	 * 修改二进制内容文件上传
	 *
	 * @param uploadFileBlob 二进制内容文件上传信息
	 * @return 修改结果条数
	 */
	int updateUploadFileBlob(AefufsUploadFileBlob uploadFileBlob);

	/**
	 * 单个删除二进制内容文件上传
	 *
	 * @param id 二进制内容文件上传ID
	 * @return 删除结果条数
	 */
	int deleteUploadFileBlobById(Long id);

	/**
	 * 批量删除二进制内容文件上传
	 *
	 * @param ids 需要删除的数据ID以,拼装
	 * @return 删除结果条数
	 */
	int deleteUploadFileBlobByIds(String[] ids);
}
