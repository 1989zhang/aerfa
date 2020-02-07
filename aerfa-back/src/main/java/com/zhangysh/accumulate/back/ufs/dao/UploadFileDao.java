package com.zhangysh.accumulate.back.ufs.dao;
import org.apache.ibatis.annotations.Mapper;

import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFile;

/**
 *@author 创建者：zhangysh
 */
@Mapper
public interface  UploadFileDao {
	/****
	 *保存AefufsUploadFile对象,并返回新增了1条数据。
	 *必须是数字类型接收，要返回对象直接在service返回保存对象，因为保存后会复制id给保存对象。要返回对象带id必须配置useGeneratedKeys="true" keyProperty="id"
	 *@param uploadFile 要保存的对象
	 *@return 影响的记录数
	 ***/
	Long insertUploadFile(AefufsUploadFile uploadFile);
}
