package com.zhangysh.accumulate.pojo.iqa.transobj;

import com.zhangysh.accumulate.pojo.webim.transobj.AefwebimMessageDto;
import java.util.Map;

/**
 * 智能问答的消息传输对象，由于要和webim用同一个后台，所以继承至AefwebimMessageDto
 *
 * @author zhangysh
 * @date 2020年03月02日
 */
public class AefiqaMessageDto extends AefwebimMessageDto {

    private static final long serialVersionUID = 1L;

    /** 连接环境信息 **/
    private Map<String,Object> connectInfo;

    public Map<String, Object> getConnectInfo() {
        return connectInfo;
    }
    public void setConnectInfo(Map<String, Object> connectInfo) {
        this.connectInfo = connectInfo;
    }
}
