package com.zhangysh.accumulate.ui.sys.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhangysh.accumulate.common.constant.CacheConstant;
import com.zhangysh.accumulate.pojo.sys.viewobj.AefsysResourceVo;

import java.util.ArrayList;
import java.util.List;

/**
 * 转化util
 *
 * @author zhangysh
 * @date 2020年02月22日
 */
public class TransformUtil {


    /**
     * 把后台session存的父子结构的资源转换为平行list资源
     * @param structResourceJsonStr
     ***/
    public static List<AefsysResourceVo> TransformResourceStructToList(String structResourceJsonStr){
        List<JSONObject> topResourceListJson= JSON.parseObject(structResourceJsonStr, List.class);
        //资源带父子结构所以重新组装成平行的
        List<AefsysResourceVo> resourceList=new ArrayList<AefsysResourceVo>();
        for(JSONObject topResourceJsonObject:topResourceListJson){
            AefsysResourceVo resourceVo=JSONObject.toJavaObject(topResourceJsonObject,AefsysResourceVo.class);
            resourceList.addAll(dealWithStructureResourceLine(resourceVo));
        }
        return resourceList;
    }

    /**
     * 处理结构化父子资源到list集合，平行处理
     */
    private static List<AefsysResourceVo> dealWithStructureResourceLine(AefsysResourceVo resourceVo){
        List<AefsysResourceVo> retResourceVoList=new ArrayList<AefsysResourceVo>();
        List<AefsysResourceVo> childrenResourceVo=resourceVo.getChildren();
        retResourceVoList.add(resourceVo);//添加当前这个
        if(childrenResourceVo.size()>0){//添加子
            for(int i=0 ; i<childrenResourceVo.size(); i++){
                retResourceVoList.addAll(dealWithStructureResourceLine(childrenResourceVo.get(i)));
            }
        }
        return retResourceVoList;
    }
}
