package com.zhangysh.accumulate.common.constant;

/*****
 * 网页即时通讯webim定义相关的属性常量，用来做判断和默认值等
 * @author zhangysh
 * @date 2019年10月9日
 *****/
public class WebimDefineConstant {

	/**********网页即时通讯webim静态配置常量**********/
	/** 网页即时通讯webim默认个人头像路径 **/
	public static final String WEBIM_DEFAULT_PERSONAL_AVATAR="/aerfa/myresource/images/default_personal.jpg";
	/** 网页即时通讯webim默认群组头像路径 **/
	public static final String WEBIM_DEFAULT_GROUP_AVATAR="/aerfa/myresource/images/default_group.jpg";
	
	/**********json数据常量**********/
	/** 网页即时通讯webim初始化信息id信息标签 **/
	public static final String WEBIM_INIT_DATA_ID="id";
	/** 网页即时通讯webim初始化信息json个人信息标签 **/
	public static final String WEBIM_INIT_USER_DATA_MINE="mine";
	/** 网页即时通讯webim初始化信息json好友信息标签 **/
	public static final String WEBIM_INIT_USER_DATA_FRIEND="friend";
	/** 网页即时通讯webim初始化信息json普通群组信息标签 **/
	public static final String WEBIM_INIT_USER_DATA_GROUP="group";

	/** 网页即时通讯webim初始化群信息群名标签 **/
	public static final String WEBIM_INIT_GROUP_DATA_GROUPNAME="groupname";
	/** 网页即时通讯webim初始化群信息群头像标签 **/
	public static final String WEBIM_INIT_GROUP_DATA_AVATAR="avatar";
	/** 网页即时通讯webim初始化群信息拥有者标签 **/
	public static final String WEBIM_INIT_GROUP_DATA_OWNER="owner";
	/** 网页即时通讯webim初始化群信息总人数标签 **/
	public static final String WEBIM_INIT_GROUP_DATA_MEMBERS="members";
	/** 网页即时通讯webim初始化群信息人员详情标签  **/
	public static final String WEBIM_INIT_GROUP_DATA_LIST="list";

	/** webim的消息内容标签id **/
	public static final String WEBIM_JSON_LABEL_ID="id";
	/** webim的消息内容标签type **/
	public static final String WEBIM_JSON_LABEL_TYPE="type";

	/** 网页即时通讯webim好友或群组搜索返回总数量标签**/
	public static final String WEBIM_SEARCH_PAGE_COUNT="count";
	/** 网页即时通讯webim好友或群组搜索返回每页数量标签**/
	public static final String WEBIM_SEARCH_PAGE_LIMIT="limit";
	/** 网页即时通讯webim好友或群组搜索返回每页数量 **/
	public static final Long WEBIM_SEARCH_PAGE_LIMIT_NUMBER=12L;
	
	
	/**********普通定义数据常量**********/
	/** 默认好友群名称:我的好友 **/
	public static final String WEBIM_DEFAULT_FRIEND_GROUP_NAME="我的好友";
	/** 默认心情签名:编辑签名 **/
	public static final String WEBIM_DEFAULT_SIGNATURE="无签名";
	
	/** 群类型:好友群 **/
	public static final Long WEBIM_GROUP_TYPE_FRIEND=1L;
	/** 群类型:普通群 **/
	public static final Long WEBIM_GROUP_TYPE_GROUP=2L;
	
	/** 好友状态0刚添加 **/
	public static final Long WEBIM_FRIEND_RELATION_STATUS_WAIT=0L;
	/** 好友状态1已通过 **/
	public static final Long WEBIM_FRIEND_RELATION_STATUS_CONFIRM=1L;
	/** 好友状态2黑名单 **/
	public static final Long WEBIM_FRIEND_RELATION_STATUS_BLACK=2L;
	
	/** 提示信息：申请添加好友 **/
	public static final String WEBIM_TIPS_INFO_TYPE_FRIEND="friend";
	/** 提示信息：申请加入普通群组 **/
	public static final String WEBIM_TIPS_INFO_TYPE_GROUP="group";
	
	/** 在线状态:在线 **/
	public static final String WEBIM_STATUS_ONLINE="online";
	/** 在线状态:隐身 **/
	public static final String WEBIM_STATUS_HIDE="hide";
	
	/** 提示信息:待处理 **/
	public static final Long WEBIM_TIPS_STATUS_UNHANDLE=0L;
	/** 提示信息:已处理 **/
	public static final Long WEBIM_TIPS_STATUS_HANDLE=1L;

	/**********数据库定义数据常量**********/
	/** 智能小法:personId **/
	public static final Long WEBIM_AIXF_PERSON_ID=10L;
	
	/**********提示信息数据常量**********/
	/** 提示信息:申请已发送等待确认，填入名称参数 **/
	public static final String WEBIM_APPLY_TIPS_CONFIRM="你添加{}的申请已发送,请等待确认";
	/** 提示信息:申请信息重复，填入名称参数 **/
	public static final String WEBIM_APPLY_TIPS_REPEAT="你添加{}的申请已存在,请勿重复申请";

	/** WebSocket消息类型:webim的friend **/
	public static final String WEBSOCKET_MESSAGE_TYPE_FRIEND="friend";
	/** WebSocket消息类型:webim的group **/
	public static final String WEBSOCKET_MESSAGE_TYPE_GROUP="group";
	/** WebSocket消息类型:webim的all **/
	public static final String WEBSOCKET_MESSAGE_TYPE_ALL="all";
	/** WebSocket消息类型:智能问答的auto **/
	public static final String WEBSOCKET_MESSAGE_TYPE_AUTO="auto";
	/** WebSocket消息类型:人工客服的manual **/
	public static final String WEBSOCKET_MESSAGE_TYPE_MANUAL="manual";
	/** WebSocket产品类型认证标记:01是webim网页聊天 **/
	public static final String WEBSOCKET_TOKEN_TYPE_WEBIM="01";
	/** WebSocket产品类型认证标记:02是auto智能回复 **/
	public static final String WEBSOCKET_TOKEN_TYPE_AUTO="02";
	/** WebSocket产品类型认证标记:03是manual人工客服 **/
	public static final String WEBSOCKET_TOKEN_TYPE_MANUAL="03";

	/** WebSocket产品类型认证字符:webim网页聊天 **/
	public static final String WEBSOCKET_TOKEN_NAME_WEBIM="websocketWebimToken";
	public static final String WEBSOCKET_TOKEN_VALUE_WEBIM="40284381702e745c01702e745c620001";
	/** WebSocket产品类型认证字符:auto智能回复 **/
	public static final String WEBSOCKET_TOKEN_NAME_AUTO="websocketAutoToken";
	public static final String WEBSOCKET_TOKEN_VALUE_AUTO="40284381702e745c01702e745c620002";
	/** WebSocket产品类型认证字符:manual人工客服 **/
	public static final String WEBSOCKET_TOKEN_NAME_MANUAL="websocketManualToken";
	public static final String WEBSOCKET_TOKEN_VALUE_MANUAL="40284381702e745c01702e745c620003";
}
