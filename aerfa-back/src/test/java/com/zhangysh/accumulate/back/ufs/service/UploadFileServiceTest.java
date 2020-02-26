package com.zhangysh.accumulate.back.ufs.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import com.zhangysh.accumulate.back.ufs.config.UfsConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.zhangysh.accumulate.pojo.ufs.dataobj.AefufsUploadFile;
import com.zhangysh.accumulate.pojo.ufs.transobj.AefufsUploadFileDto;

import javax.annotation.Resource;

/**
 *文件上传测试：测试路径要和service路径保持严格一致
 *@author 创建者：zhangysh
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadFileServiceTest {

	@Resource
	private UfsConfig ufsConfig;
	@Autowired
	@Qualifier("FtpFile")
	private IUploadFileService uploadFileService;
	  
	@Test
	public void TestMain() {
		try {
			File file = new File("D://testall//1.jpg");
			FileInputStream fis=new FileInputStream(file);
			byte[] byt = new byte[fis.available()];
			fis.read(byt);
			fis.close();
			Base64.Encoder encoder =  Base64.getEncoder();
			String fileBase64Data=encoder.encodeToString(byt);
			AefufsUploadFileDto uploadFileDto=new AefufsUploadFileDto();
			uploadFileDto.setFileName("1.jpg");
			uploadFileDto.setFileBase64Data(fileBase64Data);
			AefufsUploadFile saveFile=uploadFileService.saveFile(uploadFileDto,ufsConfig);
			System.out.println(saveFile.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
