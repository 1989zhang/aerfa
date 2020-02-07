package com.zhangysh.accumulate.back.support.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.constant.UnitConstant;
import com.alibaba.fastjson.JSON;
import com.zhangysh.accumulate.back.manage.redis.IRedisService;
import com.zhangysh.accumulate.back.support.service.IRedisRelatedService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;
import com.zhangysh.accumulate.common.pojo.TokenModel;
import com.zhangysh.accumulate.common.util.CalculateUtil;
import com.zhangysh.accumulate.common.util.StringUtil;
import com.zhangysh.accumulate.pojo.base.BaseParamObj;
import com.zhangysh.accumulate.pojo.support.dataobj.RedisParam;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysOrg;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;

/****
 * 验证token信息相关的方法接口实现
 * 
 * @author zhangysh
 * @date 2018年9月12日
 */
@Service
public class RedisRelatedServiceImpl implements IRedisRelatedService {
	
	private static Logger logger = LoggerFactory.getLogger(RedisRelatedServiceImpl.class);

	@Autowired
	private IRedisService redisService;

	@Override
	public void setTokenInfo(String token,TokenModel tokenModel) {
		logger.info("setTokenInfo设置redis缓存token:{}", token);
		if (StringUtil.isNotEmpty(token)) {
			redisService.setRedis(CacheConstant.REDIS_AERFATOKEN_PREFIX, token, JSON.toJSONString(tokenModel), CacheConstant.REDIS_EXPIRES_SEVEN_DAYS);
		}
	}
	
	@Override
	public TokenModel getTokenModelByToken(String token) {
		logger.info("getTokenModelByToken方法获取redis缓存参数token:{}", token);
		if (StringUtil.isEmpty(token)) {
			return null;
		}
		String aerfatoken=redisService.getRedis(CacheConstant.REDIS_AERFATOKEN_PREFIX, token, String.class);
		return JSON.parseObject(aerfatoken, TokenModel.class);
	}
	
	@Override
	public Map<String, Object> getRedisSessionMapByToken(String token) {
		TokenModel tokenModel=getTokenModelByToken(token);
		return tokenModel==null?null:tokenModel.getSession();
	}
	
	@Override
	public AefsysPerson getRedisSessionPersonByToken(String token) {
		Map<String, Object> redisSessionMap=getRedisSessionMapByToken(token);
		AefsysPerson redisPerson=new AefsysPerson();
		if(StringUtil.isNotEmpty(redisSessionMap)) {
			String personObjectJson = redisSessionMap.get(CacheConstant.TOKENMODEL_SESSION_KEY_PERSON)+"";
			if(StringUtil.isNotEmpty(personObjectJson)) {
				redisPerson=JSON.parseObject(personObjectJson, AefsysPerson.class);
			}
		}
		return redisPerson;
	}
	
	@Override
	public AefsysOrg getRedisSessionOrgByToken(String token) {
		Map<String, Object> redisSessionMap=getRedisSessionMapByToken(token);
		AefsysOrg redisOrg=new AefsysOrg();
		if(StringUtil.isNotEmpty(redisSessionMap)) {
			String orgObjectJson = redisSessionMap.get(CacheConstant.TOKENMODEL_SESSION_KEY_ORG)+"";
			if(StringUtil.isNotEmpty(orgObjectJson)) {
				redisOrg=JSON.parseObject(orgObjectJson, AefsysOrg.class);
			}
		}
		return redisOrg;
	}
	
	@Override
	public BsTableDataInfo getPageRedisConfig(BsTablePageInfo pageInfo) {
		Integer pageNum=pageInfo.getPageNum();
		Integer pageSize=pageInfo.getPageSize();
		Set<Map.Entry<Object, Object>> configSet= redisService.getRedisInfo();
		List<BaseParamObj> parmList=new ArrayList<BaseParamObj>();
		BsTableDataInfo tableDataInfo=new BsTableDataInfo();
		for(Map.Entry<Object,Object> entry:configSet){
			BaseParamObj parmObj=new BaseParamObj();
			parmObj.setParmCode(entry.getKey()+"");
			parmObj.setParmValue(entry.getValue()+"");
		
			if((entry.getKey()+"").equals("redis_version")) {
				parmObj.setRemark("redis版本");
			}else if((entry.getKey()+"").equals("gcc_version")) {
				parmObj.setRemark("redis所用gcc版本");	
			}else if((entry.getKey()+"").equals("os")) {
				parmObj.setRemark("Redis服务器所在操作系统");	
			}else if((entry.getKey()+"").equals("arch_bits")) {
				parmObj.setRemark("redis架构位数(32或64位)");
			}else if((entry.getKey()+"").equals("tcp_port")) {
				parmObj.setRemark("redis服务器监听端口");
			}else if((entry.getKey()+"").equals("cluster_enabled")) {
				parmObj.setRemark("是否集群模式：1是，0否");
			}else if((entry.getKey()+"").equals("redis_mode")) {
				parmObj.setRemark("运行模式：standalone单机，cluster集群");
			}else if((entry.getKey()+"").equals("run_id")) {
				parmObj.setRemark("redis服务器随机标识符(用于sentinel和集群)");
			}else if((entry.getKey()+"").equals("aof_enabled")) {
				parmObj.setRemark("是否开启了aof：1开启，0关闭");
			}else if((entry.getKey()+"").equals("role")) {
				parmObj.setRemark("实例的角色，是master或slave");
			}else if((entry.getKey()+"").equals("process_id")) {
				parmObj.setRemark("redis服务器进程的pid");
			}else if((entry.getKey()+"").equals("aof_rewrite_scheduled")) {
				parmObj.setRemark("一个标志值，记录了在 RDB 文件创建完毕之后，是否需要执行预约的 AOF 重写操作");
			}else if((entry.getKey()+"").equals("aof_last_write_status")) {
				parmObj.setRemark("AOF上次写入状态");
			}else if((entry.getKey()+"").equals("mem_allocator")) {
				parmObj.setRemark("在编译时指定的Redis使用的内存分配器，可以是libc、jemalloc、tcmalloc");
			}else if((entry.getKey()+"").equals("uptime_in_seconds")) {
				parmObj.setRemark("自 Redis服务器启动以来，经过的秒数");
			}else if((entry.getKey()+"").equals("uptime_in_days")) {
				parmObj.setRemark("自 Redis服务器启动以来，经过的天数");
			}else if((entry.getKey()+"").equals("total_commands_processed")) {
				parmObj.setRemark("Redis服务处理命令的总数");
			}else if((entry.getKey()+"").equals("repl_backlog_size")) {
				parmObj.setRemark("一个环形缓存区，默认为1024*1024(即1Mb)");
			}else if((entry.getKey()+"").equals("config_file")) {
				parmObj.setRemark("redis配置文件路径");
			}else if((entry.getKey()+"").equals("connected_slaves")) {
				parmObj.setRemark("已连接从服务器数量");
			}else if((entry.getKey()+"").equals("expired_keys")) {
				parmObj.setRemark("运行以来过期的key数量");
			}else if((entry.getKey()+"").equals("connected_clients")) {
				parmObj.setRemark("连接的客户端数量");
			}else if((entry.getKey()+"").equals("executable")) {
				parmObj.setRemark("启动的可执行文件路径");
			}else if((entry.getKey()+"").equals("total_connections_received")) {
				parmObj.setRemark("运行以来连接过的客户端的总数量");
			}else if((entry.getKey()+"").equals("multiplexing_api")) {
				parmObj.setRemark("redis事务处理机制");
			}else if((entry.getKey()+"").equals("rejected_connections")) {
				parmObj.setRemark("因最大客户端连接限制而被拒绝连接的请求数量");
			}else if((entry.getKey()+"").equals("pubsub_channels")) {
				parmObj.setRemark("目前被订阅的频道数量");
			}
			
			if(parmObj.getRemark()!=null) {
				parmList.add(parmObj);
			}
		}
		Collections.sort(parmList, Comparator.comparing(BaseParamObj::getParmCode));
		List<BaseParamObj> subList=null;
		if( pageNum * pageSize >parmList.size()){
			subList= parmList.subList( (pageNum-1) * pageSize, parmList.size() );  
		}else {
			subList= parmList.subList( (pageNum-1) * pageSize, pageNum * pageSize );   
		}
		
		tableDataInfo.setRows(subList);
		tableDataInfo.setTotal(parmList.size());
		return tableDataInfo;
	}
	
	@Override
	public List<BaseParamObj> getRedisStates(){
		Set<Map.Entry<Object, Object>> configSet= redisService.getRedisInfo();
		List<BaseParamObj> parmList=new ArrayList<BaseParamObj>();
		for(Map.Entry<Object,Object> entry:configSet){
			BaseParamObj parmObj=new BaseParamObj();
			parmObj.setParmCode(entry.getKey()+"");
			parmObj.setParmValue(entry.getValue()+"");
		
			if((entry.getKey()+"").equals("db0")) {
				String value=entry.getValue()+"";//单独处理db0参数里面的keys(keys=136,expires=135,avg_ttl=451982084)
				parmObj.setParmCode("keys");
				parmObj.setParmValue(value.substring(5, value.indexOf(",")));
				parmObj.setRemark("存储key的数量");
			//由 Redis分配器分配的内存总量，包含了redis进程内部的开销和数据占用的内存，以字节（byte）为单位
			}else if((entry.getKey()+"").equals("used_memory")) {
				parmObj.setRemark("占用的内存总量，以字节为单位");
			}else if((entry.getKey()+"").equals("used_memory_peak")) {
				parmObj.setRemark("内存内存最高峰值，以字节为单位");	
			}else if((entry.getKey()+"").equals("connected_clients")) {
				parmObj.setRemark("已连接客户端的数量");	
			}else if((entry.getKey()+"").equals("total_commands_processed")) {
				parmObj.setRemark("redis处理的总命令数");	
			}else if((entry.getKey()+"").equals("keyspace_hits")) {
				parmObj.setRemark("命中次数");	
			}else if((entry.getKey()+"").equals("keyspace_misses")) {
				parmObj.setRemark("没命中次数");	
			}else if((entry.getKey()+"").equals("used_cpu_sys")) {
				parmObj.setRemark("redis进程占用的CPU使用量求和累计起来");
			}
			if(parmObj.getRemark()!=null) {
				parmList.add(parmObj);
			}
		}
		return parmList;
	}
	
	@Override
	public String getHandleRedisStates() {
		List<BaseParamObj> statesParmList=getRedisStates();
		List<RedisParam> redisParamList=new ArrayList<RedisParam>();
		//还未做运算，后面要做运算的
		Double keyspaceHits = 1D;Double keyspaceMisses = 0D;
		double mbParamSize=1048576d;//字节转化为M的计算参数1024*1024;
		for(BaseParamObj baseParam:statesParmList) {
			if(MarkConstant.MARK_REDIS_STATUS_USED_MEMORY.equals(baseParam.getParmCode())) {
				RedisParam redisParam=new RedisParam();
				redisParam.setParmCode(baseParam.getParmCode());
				redisParam.setParmValue(CalculateUtil.div(Double.parseDouble(baseParam.getParmValue()),mbParamSize,2)+"");
				redisParamList.add(redisParam);
			}else if(MarkConstant.MARK_REDIS_STATUS_USED_MEMORY_PEAK.equals(baseParam.getParmCode())) {
				RedisParam redisParam=new RedisParam();
				redisParam.setParmCode(baseParam.getParmCode());
				redisParam.setParmValue(CalculateUtil.div(Double.parseDouble(baseParam.getParmValue()),mbParamSize,2)+"");
				redisParamList.add(redisParam);
			}else if(MarkConstant.MARK_REDIS_STATUS_KEYS.equals(baseParam.getParmCode())) {
				RedisParam redisParam=new RedisParam();
				redisParam.setParmCode(baseParam.getParmCode());
				redisParam.setParmValue(baseParam.getParmValue());
				redisParamList.add(redisParam);
			}else if(MarkConstant.MARK_REDIS_STATUS_CONNECTED_CLIENTS.equals(baseParam.getParmCode())) {
				RedisParam redisParam=new RedisParam();
				redisParam.setParmCode(baseParam.getParmCode());
				redisParam.setParmValue(baseParam.getParmValue());
				redisParamList.add(redisParam);
			}else if(MarkConstant.MARK_REDIS_STATUS_TOTAL_COMMANDS_PROCESSED.equals(baseParam.getParmCode())) {
				RedisParam redisParam=new RedisParam();
				redisParam.setParmCode(baseParam.getParmCode());
				redisParam.setParmValue(baseParam.getParmValue());
				redisParamList.add(redisParam);
			}else if(MarkConstant.MARK_REDIS_STATUS_USED_CPU_SYS.equals(baseParam.getParmCode())) {
				RedisParam redisParam=new RedisParam();
				redisParam.setParmCode(baseParam.getParmCode());
				redisParam.setParmValue(baseParam.getParmValue());
				redisParamList.add(redisParam);
			}else if(MarkConstant.MARK_REDIS_STATUS_KEYSPACE_HITS.equals(baseParam.getParmCode())) {
				keyspaceHits=Double.parseDouble(baseParam.getParmValue());
			}else if(MarkConstant.MARK_REDIS_STATUS_KEYSPACE_MISSES.equals(baseParam.getParmCode())) {
				keyspaceMisses=Double.parseDouble(baseParam.getParmValue());
			}
		}
		//缓存命中率计算
		RedisParam redisParam=new RedisParam();
		redisParam.setParmCode(MarkConstant.MARK_REDIS_STATUS_HIT_RATE);
		redisParam.setParmValue((CalculateUtil.div(keyspaceHits, (keyspaceHits+keyspaceMisses), 2)*100)+"");
		redisParamList.add(redisParam);
		return JSON.toJSONString(redisParamList);
	}
	
	@Override
	public BsTableDataInfo getPageRedisStorage(BsTablePageInfo pageInfo,RedisParam redisParam) {
		Integer pageNum=pageInfo.getPageNum();
		Integer pageSize=pageInfo.getPageSize();
		List<String> keyList=redisService.getRedisContainKeys(redisParam.getParmCode());
		Collections.sort(keyList);
		List<String> subList=null;
		if( pageNum * pageSize >keyList.size()){
			subList= keyList.subList( (pageNum-1) * pageSize, keyList.size() );  
		}else {
			subList= keyList.subList( (pageNum-1) * pageSize, pageNum * pageSize );   
		}
		List<RedisParam> redisParamList=new ArrayList<RedisParam>();
		//根据 redis的key获取组装完整键key值value过期等对象
		for(String strKey:subList) {
			RedisParam retRedisParam=new RedisParam();
			retRedisParam.setParmCode(strKey);
			retRedisParam.setParmValue(redisService.getRedisByRealKey(strKey, String.class));
			retRedisParam.setType(CacheConstant.REDIS_DATA_TYPE_STRING);
			retRedisParam.setExpireTime(redisService.getRedisExpireTimeByRealKey(strKey));
			redisParamList.add(retRedisParam);
		}
		
		BsTableDataInfo tableDataInfo=new BsTableDataInfo();
		tableDataInfo.setRows(redisParamList);
		tableDataInfo.setTotal(keyList.size());
		return tableDataInfo;
	}
	
	@Override
	public boolean saveRedisStorage(RedisParam redisParam) {
		redisService.setRedis("",redisParam.getParmCode(),redisParam.getParmValue(),redisParam.getExpireTime(),UnitConstant.TIME_UNIT_SECONDS);
		return true;
	}
	
	@Override
	public RedisParam getRedisStorage(String redisKey) {
		RedisParam redisParam=new RedisParam();
		String redisValue=redisService.getRedisByRealKey(redisKey, String.class);
		redisParam.setParmCode(redisKey);
		redisParam.setParmValue(redisValue);
		redisParam.setType(CacheConstant.REDIS_DATA_TYPE_STRING);
		redisParam.setExpireTime(redisService.getRedisExpireTimeByRealKey(redisKey));
		return redisParam;
	}
	
	@Override
	public boolean deleteRedisStorageByKeys(String redisKeys) {
		if(StringUtil.isNotEmpty(redisKeys)) {
			String[] redisKeyArr=redisKeys.split(MarkConstant.MARK_SPLIT_EN_COMMA);
			for(int i=0;i<redisKeyArr.length;i++) {
				redisService.delRedisByRealKey(redisKeyArr[i]);
			}
		}
		return true;
	}
	
	@Override
	public boolean cleanUpRedis() {
		//总共清除以下redis类型key（数据字典,行政区划）
		redisService.cleanUpRedisPattern("sys:dictype");
		redisService.cleanUpRedisPattern("sys:division");
		return true;
	}
}
