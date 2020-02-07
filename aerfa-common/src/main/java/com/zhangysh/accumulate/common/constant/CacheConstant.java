package com.zhangysh.accumulate.common.constant;

/**
 *缓存相关常量:redis缓存
 *@author 创建者：zhangysh
 *@date 2018年9月12日
 */
public class CacheConstant {
	/**********前台cookie存的各种对象*************/
	/**token的名称，因为不能带特殊名称，所以单独列了一个字符出来*/
	public static final String COOKIE_NAME_AERFATOKEN="aerfatoken";
	
	/**********redis存储对象前缀,一般为表名**********/
	/**redis中aerfatoken对应的key前缀，后缀为uuid去掉-*/
	public static final String REDIS_AERFATOKEN_PREFIX="sys:aerfatoken:";
	/**redis中数据字典类型对应的key前缀，后缀为类型codeType*/
	public static final String REDIS_DIC_TYPE_PREFIX="sys:dictype:";
	/**config_data配置信息存redis的前缀，因为存所有所以没后缀****/
	public static final String REDIS_CONFIG_DATA_ALL_PREFIX="sys:configdata:all";
	/** CityPicker专用行政区划展示字符存redis的key标识,且没后缀**/
	public static final String REDIS_DIVISION_CITY_PICKER_PREFIX="sys:division:citypicker";
	
	/**********redis存储过期时间**********/
	/**reids永久有效时间（小于0）*/
	public static final Long REDIS_NEVER_EXPIRES_HOUR=-1L;
	/**redis系统token信息过期（24*7小时）*/
	public static final Long REDIS_EXPIRES_SEVEN_DAYS=168L;
	/**redis系统不常用信息过期（24小时）*/
	public static final Long REDIS_EXPIRES_ONE_DAY=24L;
	
	/**********redis里token对应的TokenModel对象里session存储的map信息对应的key**********/
	/**TokenModel对象里session存储的个人信息*/
	public static final String TOKENMODEL_SESSION_KEY_PERSON="personinfo";
	/**TokenModel对象里session存储的部门信息*/
	public static final String TOKENMODEL_SESSION_KEY_ORG="orginfo";
	
	/**redis存储的数据类型****/
	/**redis数据类型string：默认存储json的固定数据类型******/
	public static final String REDIS_DATA_TYPE_STRING="string";
}
