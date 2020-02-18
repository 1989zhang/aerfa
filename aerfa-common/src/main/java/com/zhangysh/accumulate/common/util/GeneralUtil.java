package com.zhangysh.accumulate.common.util;

import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/**
 * 其他不太好分类的工具包
 *
 * @author zhangysh
 * @date 2020年02月18日
 */
public class GeneralUtil {

    /**
     * 使用TreeSet实现List去重(有序)
     * @param list 去重对象，
     * @return 去重结果集合
     * */
    public static List removeDuplicationByTreeSet(List list) {
        TreeSet set = new TreeSet(list);
        //把List集合所有元素清空
        list.clear();
        //把TreeSet对象添加至List集合
        list.addAll(set);
        return list;
    }

    /**
     * 使用HashSet实现List去重(无序)
     * @param list 去重对象，对象要实现compareTo
     * @去重结果集合
     * */
    public static List removeDuplicationByHashSet(List list) {
        HashSet set = new HashSet(list);
        //把List集合所有元素清空
        list.clear();
        //把HashSet对象添加至List集合
        list.addAll(set);
        return list;
    }
}
