package com.zhangysh.accumulate.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 字符串工具类
 * @author zhangysh
 * @date 2018年9月7日
 */
public class StringUtil
{

    
    /** 空字符串 */
    private static final String NULLSTR = "";
    /** 下划线 */
    private static final char SEPARATOR = '_';
    /** 字符判断 */
	private static final String EMPTY_JSON = "{}";
	private static final char C_BACKSLASH = '\\';
	private static final char C_DELIM_START = '{';
	
    /**
     * 获取参数不为空值
     * 
     * @param value defaultValue 要判断的value
     * @return value 返回值
     */
    public static <T> T nvl(T value, T defaultValue)
    {
        return value != null ? value : defaultValue;
    }

    /**
     * * 判断一个Collection是否为空， 包含List，Set，Queue
     * 
     * @param coll 要判断的Collection
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Collection<?> coll)
    {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * * 判断一个Collection是否非空，包含List，Set，Queue
     * 
     * @param coll 要判断的Collection
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Collection<?> coll)
    {
        return !isEmpty(coll);
    }

    /**
     * * 判断一个对象数组是否为空
     * 
     * @param objects 要判断的对象数组
     ** @return true：为空 false：非空
     */
    public static boolean isEmpty(Object[] objects)
    {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * * 判断一个对象数组是否非空
     * 
     * @param objects 要判断的对象数组
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Object[] objects)
    {
        return !isEmpty(objects);
    }

    /**
     * * 判断一个Map是否为空
     * 
     * @param map 要判断的Map
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Map<?, ?> map)
    {
        return isNull(map) || map.isEmpty();
    }

    /**
     * * 判断一个Map是否为空
     * 
     * @param map 要判断的Map
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Map<?, ?> map)
    {
        return !isEmpty(map);
    }

    /**
     * * 判断一个字符串是否为空串
     * 
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(String str)
    {
        return isNull(str) || str.length()==0;
    }

    /**
     * * 判断一个字符串是否为非空串
     * 
     * @param str String
     * @return true：非空串 false：空串
     */
    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }

    /**
     * * 判断一个对象是否为空
     * 
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object)
    {
        return object == null;
    }

    /**
     * * 判断一个对象是否非空
     * 
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object)
    {
        return !isNull(object);
    }

    /**
     * * 判断一个对象是否是数组类型（Java基本型别的数组）
     * 
     * @param object 对象
     * @return true：是数组 false：不是数组
     */
    public static boolean isArray(Object object)
    {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * 去空格
     */
    public static String trim(String str)
    {
        return (str == null ? "" : str.trim());
    }

    /**
     * 截取字符某字符开头的字符串，例如：aefsys_data_dic截取aefsys,
     * @param str 总字符串
     * @param indexStr 截取标记
     * @return 截取结果
     **/
    public static String substring(final String str,String indexStr) {
    	if (str == null || !str.contains(indexStr) ){
            return NULLSTR;
        }
    	return str.substring(0,str.indexOf(indexStr));
    }
    
    /**
     * 截取字符串
     * 
     * @param str 字符串
     * @param start 开始
     * @return 结果
     */
    public static String substring(final String str, int start)
    {
        if (str == null)
        {
            return NULLSTR;
        }

        if (start < 0)
        {
            start = str.length() + start;
        }

        if (start < 0)
        {
            start = 0;
        }
        if (start > str.length())
        {
            return NULLSTR;
        }

        return str.substring(start);
    }

    /**
     * 截取字符串
     * 
     * @param str 字符串
     * @param start 开始
     * @param end 结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end)
    {
        if (str == null)
        {
            return NULLSTR;
        }

        if (end < 0)
        {
            end = str.length() + end;
        }
        if (start < 0)
        {
            start = str.length() + start;
        }

        if (end > str.length())
        {
            end = str.length();
        }

        if (start > end)
        {
            return NULLSTR;
        }

        if (start < 0)
        {
            start = 0;
        }
        if (end < 0)
        {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 将驼峰式命名字符串转下划线方式命名。如果转换为空，则返回空字符串。 例如：HelloWorld->hello_word
     * 
     * @param s 转换前的驼峰式命名的字符串
     * @return 转换后的下划线命名的字符串
     */
    public static String convertToUnderLineCase(String s)
    {
        if (s == null || s.isEmpty())
        {
        	// 没必要转换
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1))
            {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c))
            {
                if (!upperCase || !nextUpperCase)
                {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            }
            else
            {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }
    
    /**
     * 将下划线方式命名的字符串转换为驼峰式。如果转换为空，则返回空字符串。 例如：Hello_word->HelloWorld
     * 
     * @param name 转换前的下划线方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name)
    {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty())
        {
            // 没必要转换
            return "";
        }
        else if (!name.contains("_"))
        {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels)
        {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty())
            {
                continue;
            }
            // 首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }
    
    /**
     * 驼峰首字符小写，用于参数变量:例如：HelloWorld->helloWorld
     * @param str 驼峰命名的字符串
     * @return 转换后的驼峰首字母小写的字符串
     */
    public static String convertToFirstLowerCase (String str){
        int strLen;
        if (str == null || (strLen = str.length()) == 0){
            return str;
        }
        return new StringBuilder(strLen).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
    }
    /**
     * 是否包含字符串
     * 
     * @param str 验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs)
    {
        if (str != null && strs != null)
        {
            for (String s : strs)
            {
                if (str.equalsIgnoreCase(trim(s)))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     * 
     * @param strPattern 文本模板，被替换的部分用 {} 表示
     * @param params 参数值
     * @return 格式化后的文本
     */
    public static String format(final String strPattern, final Object... argArray)
    {
        if (isEmpty(strPattern) || isEmpty(argArray) )
        {
            return strPattern;
        }
        final int strPatternLength = strPattern.length();

        // 初始化定义好的长度以获得更好的性能
        StringBuilder sbuf = new StringBuilder(strPatternLength + 50);

        int handledPosition = 0;
        int delimIndex;// 占位符所在位置
        for (int argIndex = 0; argIndex < argArray.length; argIndex++)
        {
            delimIndex = strPattern.indexOf(EMPTY_JSON, handledPosition);
            if (delimIndex == -1)
            {
                if (handledPosition == 0)
                {
                    return strPattern;
                }
                else
                { // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                    sbuf.append(strPattern, handledPosition, strPatternLength);
                    return sbuf.toString();
                }
            }
            else
            {
                if (delimIndex > 0 && strPattern.charAt(delimIndex - 1) == C_BACKSLASH)
                {
                    if (delimIndex > 1 && strPattern.charAt(delimIndex - 2) == C_BACKSLASH)
                    {
                        // 转义符之前还有一个转义符，占位符依旧有效
                        sbuf.append(strPattern, handledPosition, delimIndex - 1);
                        sbuf.append(ConvertUtil.utf8Str(argArray[argIndex]));
                        handledPosition = delimIndex + 2;
                    }
                    else
                    {
                        // 占位符被转义
                        argIndex--;
                        sbuf.append(strPattern, handledPosition, delimIndex - 1);
                        sbuf.append(C_DELIM_START);
                        handledPosition = delimIndex + 1;
                    }
                }
                else
                {
                    // 正常占位符
                    sbuf.append(strPattern, handledPosition, delimIndex);
                    sbuf.append(ConvertUtil.utf8Str(argArray[argIndex]));
                    handledPosition = delimIndex + 2;
                }
            }
        }
        // 加入最后一个占位符后所有的字符
        sbuf.append(strPattern, handledPosition, strPattern.length());

        return sbuf.toString();
    }
    
    /***
     * 把以某种字符分割的字符串转化为字符串list
     * @param arrStr 要分割的串
     * @param splitStr 分割标记串
     ****/
    public static List<String> getStrListBySplit(String arrStr,String splitStr){
    	List<String> retStrList=new ArrayList<String>();
    	if(isNotEmpty(arrStr)&&isNotEmpty(splitStr)) {
    		String[] str=arrStr.split(splitStr);
    		for( int i=0;i<str.length;i++ ) {
    			retStrList.add(str[i]);
    		}
    	}
    	return retStrList;
    }
}