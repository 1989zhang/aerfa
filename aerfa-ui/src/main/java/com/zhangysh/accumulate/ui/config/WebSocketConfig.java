package com.zhangysh.accumulate.ui.config;

import com.zhangysh.accumulate.common.constant.UnitConstant;
import com.zhangysh.accumulate.ui.sys.util.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.http.HttpSession;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import org.apache.catalina.session.StandardSessionFacade;

/**
 * 开启WebSocket支持
 * @author zhangysh
 * @date 2019年9月25日
 */
@Configuration  
public class WebSocketConfig extends ServerEndpointConfig.Configurator {

    private static final Logger logger= LoggerFactory.getLogger(WebSocketConfig.class);

    private static final String HttpSession = null;

    /* 修改握手,就是在握手协议建立之前修改其中携带的内容 */
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        /*如果没有监听器,那么这里获取到的HttpSession是null*/
        StandardSessionFacade ssf = (StandardSessionFacade) request.getHttpSession();
        if (ssf != null) {
            HttpSession session = (HttpSession) request.getHttpSession();
            //设置httpSession会话连接参数，方便webSocket取
            session.setAttribute(UnitConstant.HTTP_SESSION_CLIENT_IP_ADDR,ServletUtil.getClientIpAddr());
            session.setAttribute(UnitConstant.HTTP_SESSION_CLIENT_OPERATING_SYSTEM,ServletUtil.getClientOperatingSystem());
            session.setAttribute(UnitConstant.HTTP_SESSION_CLIENT_BROWSER,ServletUtil.getClientBrowser());
            sec.getUserProperties().put(UnitConstant.HTTP_SESSION_NAME, session);
            logger.info("设置获取到的HttpSession的ID：{}",session.getId());
        }
        super.modifyHandshake(sec, request, response);
    }

    @Bean  
    public ServerEndpointExporter serverEndpointExporter() {  
        return new ServerEndpointExporter();  
    }  
  
}  