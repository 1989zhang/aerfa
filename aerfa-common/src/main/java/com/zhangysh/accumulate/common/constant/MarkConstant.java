package com.zhangysh.accumulate.common.constant;

/*****
 * 系统类标记标志常量，用来做判断等用处
 * @author zhangysh
 * @date 2019年4月1日
 *****/
public class MarkConstant {
	
	/**********判断标记常量**********/
	//ResultVo的三个属性对象常量
	public static final String MARK_RESULT_VO_CODE="code";
	public static final String MARK_RESULT_VO_MSG="msg";
	public static final String MARK_RESULT_VO_DATA="data";
	//ResultVo返回成功常量标志
	public static final Integer MARK_RESULT_VO_SUCESS=0;
	
	/**********其他标记**********/
	/**字符常量：英文$号**/
	public static final String MARK_SPLIT_EN_DOLLAR ="$";
	/**字符常量：左括号英文{**/
	public static final String MARK_SPLIT_EN_BRACE_LEFT ="{";
	/**字符常量：右括号英文}**/
	public static final String MARK_SPLIT_EN_BRACE_RIGHT ="}";
	/**分割字符标记常量：英文逗号,**/
	public static final String MARK_SPLIT_EN_COMMA =",";
	/**分割字符标记常量：英文冒号:**/
	public static final String MARK_SPLIT_EN_COLON =":";
	/**英文中杠连接字符(同减号)：-**/
	public static final String MARK_SPLIT_EN_HYPHEN ="-";
	/**英文波浪号连接字符,用于参数分离**/
	public static final String MARK_SPLIT_EN_TILDE ="~";
	/**联系地址分割字符常量：英文斜杠号/**/
	public static final String MARK_SPLIT_EN_VIRGULE ="/";
	/** 请求参数变量前缀标记 ***/
	public static final String MARK_REQUEST_PARAM_PREFIX ="request~";
	
	/**********redis状态标记参数常量**********/
	/**由Redis分配器分配的内存总量，包含了redis进程内部的开销和数据占用的内存**/
	public static final String MARK_REDIS_STATUS_USED_MEMORY ="used_memory";
	/**由Redis中key的数量**/
	public static final String MARK_REDIS_STATUS_KEYS ="keys";
	/**Redis中连接的客户端数量**/
	public static final String MARK_REDIS_STATUS_CONNECTED_CLIENTS ="connected_clients";
	/**Redis执行的命令总数**/
	public static final String MARK_REDIS_STATUS_TOTAL_COMMANDS_PROCESSED ="total_commands_processed";
	/**Redis缓存命中率计算(命中/命中+未命中):命中率低说明过期的key太多**/
	public static final String MARK_REDIS_STATUS_KEYSPACE_HITS ="keyspace_hits";
	public static final String MARK_REDIS_STATUS_KEYSPACE_MISSES ="keyspace_misses";
	public static final String MARK_REDIS_STATUS_HIT_RATE="hit_rate";
	/**Redis内存使用的最大值，表示used_memory峰值**/
	public static final String MARK_REDIS_STATUS_USED_MEMORY_PEAK ="used_memory_peak";
	/**redis占用的系统CPU时间总和s秒**/
	public static final String MARK_REDIS_STATUS_USED_CPU_SYS="used_cpu_sys";
	/**redis每秒执行多少命令或叫查询**/
	public static final String MARK_REDIS_STATUS_QPS="qps";
}
