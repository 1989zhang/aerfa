package com.zhangysh.accumulate.ui.webim.service;

import java.io.IOException;

/**
 *@author 创建者：zhangysh
 */
public interface IMessageService {

	Boolean sendMessageToUser(String toSessionId,String message) throws IOException;
}
