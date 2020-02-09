package com.zhangysh.accumulate.ui.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.ui.iqa.service.IqaReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangysh.accumulate.common.constant.WebimDefineConstant;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.iqa.transobj.AefiqaAskDto;
import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimMessageDto;

/**
 * WebSocket主服务器，传递sessionid取token
 * @author 创建者：zhangysh
 * @date 2018年9月3日
 */
@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {
			
	private static final Logger logger=LoggerFactory.getLogger(WebSocketServer.class);
	private static IqaReplyService iqaReplyService;

	@Autowired
	public void setIqaReplyService(IqaReplyService iqaReplyService) {
		WebSocketServer.iqaReplyService = iqaReplyService;
	}

	//静态变量，用来记录当前在线连接数。
	private static int onlineCount = 0;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //会话id
    private String sid;
    
    //ConcurrentHashMap是线程安全的，而HashMap是线程不安全的。
    public static ConcurrentHashMap<String,Session> mapUS = new ConcurrentHashMap<String,Session>(); //根据id找session
    private static ConcurrentHashMap<Session,String> mapSU = new ConcurrentHashMap<Session,String>();//根据session找id
	    
    /**
     * 连接建立成功调用的方法
     * @throws IOException 
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("sid") String sid) throws IOException{
    	if(!dealWithOpenBeforeExpand(sid)) {return;}//连接不合法
    	this.session = session;
    	this.sid=sid;
    	mapUS.put(sid,session);
    	mapSU.put(session,sid);
    	addOnlineCount();//在线数加1
    	logger.info("有新窗口开始监听:"+sid+",当前在线人数为:" + getOnlineCount());
    	dealWithOpenAfterExpand(sid);
    	logger.info("连接成功拓展方法执行完成");
    }
    /**
     * 收到客户端消息后调用的方法
     * @throws IOException 
     */
	@OnMessage
    public void onMessage(String message,Session session) throws IOException {
		logger.info("开始处理来自客户端的消息:" + message);
        JSONObject jsonMessage=JSONObject.parseObject(message);
        JSONObject messageInfoJson=jsonMessage.getJSONObject("message");
        JSONObject mineJson=messageInfoJson.getJSONObject("mine");
        JSONObject toJson=messageInfoJson.getJSONObject("to");
        String type = toJson.getString("type");
        sendMessage(mineJson,toJson,type);
    }
    
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
    	mapUS.remove(sid);//删除信息
    	mapSU.remove(session);//删除信息
    	subOnlineCount();//在线数减1
    	logger.info("有一连接关闭！当前在线人数为:" + getOnlineCount());
    }
	/**
     * 发生错误时调用
     * @param session
     * @param error
	 */
    @OnError
    public void onError(Session session, Throwable error) {
    	error.printStackTrace();
    	logger.info("发生错误:" + error.getMessage());
    }
    
    //线程同步的加减人数计算
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
    	WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
    	WebSocketServer.onlineCount--;
    }
    

    /*****以下为单独方法体*****/
   
   /***
    * 消息组装让后发送消息
    * @throws IOException 
    *****/
   public void sendMessage(JSONObject fromJson,JSONObject toJson,String type) throws IOException {
	   switch (type) {
	       case "friend"://单聊
	    	    sendMessageFriend(fromJson,toJson);
	    	    //后期接入智能小法,自动回复
	    	    if(WebimDefineConstant.WEBIM_AIXF_PERSON_ID.equals(toJson.getLong("id"))) {
	    	    	sendMessageAutomaticXf(fromJson);
	    	    }
	    	    break;
	       case "group"://群聊
	    	    logger.info("群聊的消息处理");
		    	/*String[] members=to.split(",");
		    	//发送到在线用户
		    	for(String member:members){
		    		//发给群里所有在线的人
		    		sendMessage(member,"hehe");
		    	}*/
	    	    break;
	       case "all"://所有人
	    	    logger.info("系统发所有的消息处理");
		    	for(Map.Entry<String,Session> entry:mapUS.entrySet()) {
		    		String toUserSessionId=entry.getKey();
		    		sendMessageText(toUserSessionId,"dd");
		    	}
		    	break;
	       case "auto"://智能小法,自动回复
	    	    sendMessageAutomaticXf(fromJson);
		    	break;
	        default:
	    	    break;
	   }
   }

   /**
    * 普通发送消息
	***/
	private void sendMessageFriend(JSONObject fromJson, JSONObject toJson) throws IOException {
		logger.info("单聊的消息处理");
		AefwebimMessageDto messageDto = new AefwebimMessageDto();
		messageDto.setId(fromJson.getString("id"));
		messageDto.setUsername(fromJson.getString("username"));
		messageDto.setAvatar(fromJson.getString("avatar"));
		messageDto.setType("friend");
		messageDto.setContent(fromJson.getString("content"));
		messageDto.setCid(0L);
		messageDto.setMine(false);
		messageDto.setFromid(fromJson.getString("id"));
		messageDto.setTimestamp(System.currentTimeMillis());
		sendMessageText(toJson.getString("id"), JSON.toJSONString(messageDto));
	}
	
   /**
    * 智能回答回复消息
	***/
	private void sendMessageAutomaticXf(JSONObject fromJson) throws IOException {
		String questionContent=fromJson.getString("content");
		logger.info("智能回答的消息处理"+questionContent);
		AefiqaAskDto askDto=new AefiqaAskDto();
		askDto.setIqaToken(fromJson.getString("id").substring(0, 32));
		askDto.setAskContent(questionContent);
		String replyContent="";
		if(StringUtil.isEmpty(questionContent)) {
			replyContent="Hi，我是智能小法。有什么问题可以直接问我！";
		}else{
			String replayStr=iqaReplyService.getReply(askDto);
			JSONObject retJson=JSON.parseObject(replayStr);
			if(MarkConstant.MARK_RESULT_VO_SUCESS.equals(retJson.getInteger(MarkConstant.MARK_RESULT_VO_CODE))){
				replyContent=retJson.getString(MarkConstant.MARK_RESULT_VO_DATA);
			}else{
				replyContent="你问的问题小法不懂，小法正在学习中！";
			}
		}
		AefwebimMessageDto autoMessage=new AefwebimMessageDto();
    	autoMessage.setId(WebimDefineConstant.WEBIM_AIXF_PERSON_ID+"");
    	autoMessage.setUsername("智能小法");
    	autoMessage.setAvatar(fromJson.getString("avatar"));
    	autoMessage.setType("friend");
    	autoMessage.setContent(replyContent);
    	autoMessage.setCid(0L);
    	autoMessage.setMine(false);
    	autoMessage.setFromid(WebimDefineConstant.WEBIM_AIXF_PERSON_ID+"");
    	autoMessage.setTimestamp(System.currentTimeMillis());  
    	sendMessageText(fromJson.getString("id"),JSON.toJSONString(autoMessage));
	}

	/**
	 * 实现服务器主动推送
	 * 
	 * @param toSessionId 推送给哪个sessionid
	 * @param message 消息
	 */
	private void sendMessageText(String toSessionId, String message) throws IOException {
		Session toSession = mapUS.get(toSessionId);
		if (toSession != null) {
			logger.info("发送消息:" + message);
			toSession.getAsyncRemote().sendText(message);
		}
	}

	/****
	 * 页面客户端和服务器连接成功前的拓展方法，例如： 判断用户是否合法
	 ***/
	public boolean dealWithOpenBeforeExpand(String sid) {
		if (sid.startsWith("402843816f85552e016f85552ee20000")) {
			return true;
		}
		return true;
	}

	/****
	 * 页面客户端和服务器连接成功后的拓展方法，例如： 发送智能问答提示信息
	 * @throws IOException 
	 ***/
	public void dealWithOpenAfterExpand(String sid) throws IOException {
		if (sid.startsWith("402843816f85552e016f85552ee20000")) {
			Map<String,Object> fromMap=new HashMap<String,Object>();
			fromMap.put("id", sid);
			fromMap.put("username", "游客"+sid.substring(31, sid.length()));
			fromMap.put("id", sid);
			sendMessage(JSON.parseObject(JSON.toJSONString(fromMap)),null,"auto");
		}
	}
	
}
