package com.zhangysh.accumulate.ui.iqa.service;

import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.iqa.transobj.AefiqaAskDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*****
 * 智能问答，回复相关方法
 * @author zhangysh
 * @date 2020年02月09日
 *****/
@FeignClient(name = "${feign.back.name}")
public interface IqaReplyService {

    /**
     * 根据问题和用户标示ID，获取问题的答案
     * @param askDto 请求的用户标识和问题
     * @return 获取到的答案
     ****/
    @RequestMapping(value = "/iqa/reply",method = RequestMethod.POST)
    public String getReply(@RequestBody AefiqaAskDto askDto);

    /**
     * 根据token即sessionId判断用户是否合法
     * @param iqaToken 用户session标识
     * @return 获取到判断结果
     ****/
    @RequestMapping(value = "/iqa/legal",method = RequestMethod.POST)
    public String getLegal(@RequestBody String iqaToken);

}
