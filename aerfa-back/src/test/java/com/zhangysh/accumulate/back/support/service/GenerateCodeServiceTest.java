package com.zhangysh.accumulate.back.support.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zhangysh.accumulate.pojo.support.dataobj.ColumnInfo;
import com.zhangysh.accumulate.pojo.support.dataobj.TableInfo;
import com.zhangysh.accumulate.pojo.support.transobj.GenerateCodeParamDto;

/**
 *代码生成相关测试：测试路径要和service路径保持严格一致
 *@author 创建者：zhangysh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerateCodeServiceTest {
	@Autowired
	private IGenerateCodeService generateCodeService;
	
	@Test
	public void TestMain() {
		//生成代码组装参数
		String tableName="aefsys_data_dic";
		String searchColumns="type_code,type_name";
		
		GenerateCodeParamDto codeParamDto=new GenerateCodeParamDto();
		codeParamDto.setTableName(tableName);
		codeParamDto.setReomveTablePrefix("aefsys_");
		codeParamDto.setPackageName("com.zhangysh.accumulate.back.sys");
		codeParamDto.setAuthor("zhangysh");
		codeParamDto.setSearchColumns(searchColumns);
		byte[] retByte=generateCodeService.generatorCode(codeParamDto);
		getFile(retByte,"D://testall","111.zip");
	}
	
	/** 
     * 根据byte数组，生成文件 
     */  
    public static void getFile(byte[] bfile, String filePath,String fileName) {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try {  
            File dir = new File(filePath);  
            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
                dir.mkdirs();  
            }  
            file = new File(filePath+"\\"+fileName);  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bfile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
    }
}
