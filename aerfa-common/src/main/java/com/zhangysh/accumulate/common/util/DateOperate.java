package com.zhangysh.accumulate.common.util;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.zhangysh.accumulate.common.constant.UtilConstant;
/**
 * 获取系统当前时间和时间戳,以及时间相关各种类型转换.
 * @author zhangysh
 * @date 2018年9月10日
 * */
public class DateOperate {
	
    /**
	 * 获取当前系统时间字符串
	 * 格式为 yyyy-MM-dd
	 * @return String类型
	 **/
	public static String getCurrentStrDate() {
		 java.util.Date date = new  java.util.Date();
		 SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd");
		 return df.format(date);
	}
	
	/**
	 * 获取当前系统时间字符串，格式为 传入的格式
	 * @return String类型
	 **/
	public static String getCurrentStrDate(String pattern) {
		 java.util.Date date = new  java.util.Date();
		 if(StringUtil.isEmpty(pattern)) { pattern="yyyy-MM-dd"; }
		 SimpleDateFormat format = new SimpleDateFormat(pattern);
		 return format.format(date);
	}
		
	/**
	 * 获取当前系统时间戳字符串
	 * 格式为 yyyy-MM-dd HH:mm:ss
	 * @return String类型
	 * */
	public static String getCurrentStrTimeStamp() {
		Timestamp now =new Timestamp(System.currentTimeMillis());
		SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(now);
	}
		
	/**
	 * 获取当前util系统时间
	 * 格式为 ：Thu Dec 06 17:00:32 CST 2012
	 * @return Date类型
	 * */
	public static java.util.Date getCurrentUtilDate() {
		java.util.Date utildate=new java.util.Date(System.currentTimeMillis());
		return utildate;
	}
		
	/**
	 * 获取当前SQL系统时间
	 * 格式为 yyyy-MM-dd
	 * @return Date类型
	 * */
	public static java.sql.Date getCurrentSQLDate() {
		java.sql.Date sqldate=new java.sql.Date(System.currentTimeMillis());
		return sqldate;
	}
	
	/**
	 * 获取当前系统时间戳
	 * 格式为  yyyy-MM-dd HH:mm:ss.xxx
	 * @return Timestamp类型
	 * */
	public static Timestamp getCurrentSQLTimeStamp() {
		Timestamp now =new Timestamp(System.currentTimeMillis());
		return now;
	}
	
	/**
	 * 获取当前系统时间年份
	 * 格式为  2013
	 * @return int类型年份
	 * */
	public static int getCurrentTimeYear() {
		Calendar CD = Calendar.getInstance();
		return CD.get(Calendar.YEAR);
	}
	/**
	 * 获取当前系统时间月份
	 * 格式为  1
	 * @return int类型月份
	 * */
	public static int getCurrentTimeMonth() {
		Calendar CD = Calendar.getInstance();		
		return CD.get(Calendar.MONTH)+1 ;
	}
	
	/**
	 * 获取当前系统时间 日
	 * 格式为  12
	 * @return int类型 日
	 * */
	public static int getCurrentTimeDay() {
		Calendar CD = Calendar.getInstance();		
		return CD.get(Calendar.DATE);
	}
	/**
	 * 获取当前系统时间24小时制时
	 * 格式为  17
	 * @return int类型 时
	 * */
	public static int getCurrentTime24Hour() {
		Calendar CD = Calendar.getInstance();		
		return CD.get(Calendar.HOUR_OF_DAY);
	}
	/**
	 * 获取当前系统时间12小时制时
	 * 格式为  7
	 * @return int类型 时
	 * */
	public static int getCurrentTime12Hour() {
		Calendar CD = Calendar.getInstance();		
		return CD.get(Calendar.HOUR);
	}
	/**
	 * 获取当前系统时间分
	 * 格式为  30
	 * @return int类型 分
	 * */
	public static int getCurrentTimeMinute() {
		Calendar CD = Calendar.getInstance();		
		return CD.get(Calendar.MINUTE);
	}
	/**
	 * 获取当前系统时间秒
	 * 格式为  59
	 * @return int类型 秒
	 * */
	public static int getCurrentTimeSecond() {
		Calendar CD = Calendar.getInstance();		
		return CD.get(Calendar.SECOND);
	}
	/**
	 * 获取当前系统时间毫秒
	 * 格式为  875
	 * @return int类型 毫秒
	 * */
	public static int getCurrentTimeMilliSecond() {
		Calendar CD = Calendar.getInstance();		
		return CD.get(Calendar.MILLISECOND);
	}
	
	//////////////////////////////////////////////
	
	/**
	 * 获取以日期组成的：年月日层为文件夹的路径，路径以/分开，为了nginx显示不用File.separator
	 * @return 文件夹例如：2019/05/04/
	 ***/
	public static String getDateYmdFileHolder() {
		return  DateOperate.getCurrentStrDate().replaceAll("-", "/")+"/";
	}
	
	/**
	 * 获取以日期组成的：年月日时分秒字符串,并发特别低可作为uuid用
	 * @return 例如：20190620225304
	 ***/
	public static String getDateYmdHms() {
		return  DateOperate.getCurrentStrDate("yyyyMMddHHmmss");
	}
	
	//////////////////////////////////////////////
	
	/***
	 * 获取年月中的第一天
	 * @param year 所在年
	 * @param month 所在月
	 * @return 第一天日期例如：2019-06-01
	 ****/
	public static String getFirstDayOfMonth(int year, int month) {
		String firstDateStr=year+"-"+month+"-01";
		return firstDateStr;  
	}
	
	/**
	 * 获取年月中的最后一天
	 * @param year 所在年
	 * @param month 所在月
	 * @return 最后一天日期例如：2019-06-30
	 ***/
    public static String getLastDayOfMonth(int year, int month) {
    	int lastDay=getLastNumberDayOfMonth(year,month);
    	String monthStr = month < 10 ? ("0" + month) : ""+month;
    	String lastDateStr=year+"-"+monthStr+"-"+lastDay;
    	return StringdatetoStringYMD(lastDateStr, UtilConstant.NORMAL_MIDDLE_DATE, UtilConstant.NORMAL_MIDDLE_DATE);
	}
    
    /**
	 * 获取年月中的最后一天
	 * @param year 所在年
	 * @param month 所在月
	 * @return 最后一天例如：31
	 ***/
    public static int getLastNumberDayOfMonth(int year, int month) {   
    	int day = 1;
	    Calendar cal = Calendar.getInstance();     
	    cal.set(year, month-1, day);
	    //获取某月最大天数
	    int lastDay = cal.getActualMaximum(Calendar.DATE);
	    return lastDay;
	}
    
   /**
	 * 计算给定日期所在的周的周一的日期</br>
	 * 比如:2015-04-15所在周是(2015-04-13周一到2015-04-19周天)周一即为:2015-04-13
	 * @param strdate
	 *           传入时间字符串
	 * @param pattern
	 *           时间格式类型 ，例如:<li>yyyy-MM-dd</li>和传入的strdate保持一致
	 * @return str周一日期字符串格式类型和传入pattern相同
     * */
	 public static String getMondayDateByStrDate(String strdate,String pattern){
		try {
			 SimpleDateFormat sdf=new SimpleDateFormat(pattern);
			 java.util.Date date = sdf.parse(strdate);
			 Calendar cal = Calendar.getInstance();  
			 cal.setTime(date);
			 int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天 ,外国周天是第一天
			 if(1== dayWeek) {cal.add(Calendar.DAY_OF_MONTH, -1);}//判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了   
			 cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一 
			 int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
			 cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值 ,计算出周一   
			 return sdf.format(cal.getTime()); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 return null;
	 }
	 
    /**
	  * 计算给定日期所在的周的周天的日期</br>
	  * 比如:2015-04-15所在周是(2015-04-13周一到2015-04-19周天)周天即为:2015-04-19
	  * @param strdate
	  *           传入时间字符串
	  * @param pattern
	  *           时间格式类型 ，例如:<li>yyyy-MM-dd</li>和传入的strdate保持一致
	  * @return str周天日期字符串格式类型和传入pattern相同
      */
	 public static String getSundayDateByStrDate(String strdate,String pattern){
		try {
			 SimpleDateFormat sdf=new SimpleDateFormat(pattern);
			 java.util.Date date = sdf.parse(strdate);
			 Calendar cal = Calendar.getInstance();  
			 cal.setTime(date);
			 int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天 ,外国周天是第一天
			 if(1== dayWeek) {cal.add(Calendar.DAY_OF_MONTH, -1);}//判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了   
			 cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一 
			 int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
			 cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值,计算出周一   
			 cal.add(Calendar.DATE, 6);//计算出周天
			 return sdf.format(cal.getTime()); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 return null;
	 }

	/**
	 * 将字符串转String类型换成需要的String字符串类型
	 * @param str
	 *           要转换的字符串对象,格式: <li>2004-05-08 21:07:08</li> 
	 * @param strInPattern
	 *           要转换的字符串对象的格式,和字符串保持一致: <li>yyyy-MM-dd HH:mm:ss</li> 
	 * @param strOutPattern
	 *           要转出来的字符串格式: <li>yyyy年yy月dd日 hh时mm分ss秒</li> 
	 * @return String字符串
	 */
	public static String StringdatetoStringYMD(String str,String strInPattern,String strOutPattern) {
	    java.util.Date date=null;
        String strdate=null;
        SimpleDateFormat format1 = new SimpleDateFormat(strInPattern);
        SimpleDateFormat format2 = new SimpleDateFormat(strOutPattern);
	    try {
		    date=format1.parse(str);
		    strdate=format2.format(date);
		} catch (ParseException e) {
			System.out.println("StringdatetoStringYMD转换字符串出现异常:" + e.getMessage());
			e.printStackTrace();
		}
	    return strdate;
	}
	
	 /**
	  * 将字符串转换成UtilDate类型
	  * @param str
	  *           要转换的对象
	  * @param pattern
	  *           格式类型 ，和和要转换的对象对应
	  * @return Date时间日期Util
	  * <li>格式为 ：Thu Dec 06 17:00:32 CST 2012</li>
	  */
	  public static java.util.Date StringtoUtilDate(String str, String pattern) {
		 java.util.Date utildate = null;       
         if (!"".equals(str) && str != null) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                utildate = format.parse(str);
            } catch (Exception e) {
                System.out.println("StringtoUtilDate转换字符串出现异常:" + e.getMessage());
                e.printStackTrace();
            }
         }
         return utildate;
	  }
		
		/**
		 * 将字符串转换成sql Date类型
		 * @param str
		 *           要转换的对象
		 * @param pattern
		 *           格式类型 ，和要转换的对象对应,例如：
	     *            <li>2004-05-08 12:15:10 yyyy-MM-dd HH:mm:ss</li> 
		 *            <li>2004-05-08 12:15 yyyy-MM-dd HH:mm</li> 
		 *            <li>2004-05-08  yyyy-MM-dd</li> 
		 *            <li>2004-05 yyyy-MM</li>  
		 * @return Date 时间sql类型
		 */
		 public static java.sql.Date StringtoSQLDate(String str, String pattern) {
			java.sql.Date sqldate = null;       
	        if (!"".equals(str) && str != null) {
	            try {
	                SimpleDateFormat format = new SimpleDateFormat(pattern);
	                java.util.Date utildate = format.parse(str);
	                sqldate = new java.sql.Date(utildate.getTime());
	            } catch (Exception e) {
	                System.out.println("StringtoSQLDate转换字符串出现异常:" + e.getMessage());
	                e.printStackTrace();
	            }
	        }
	        return sqldate;
	    }
		 
		
		/**
		 * 将字符串转换成Timestamp类型
		 * @param str
		 *           要转换的对象
		 * @param pattern
		 *           格式类型 ，和要转换的对象对应,例如：
		 *            <li>2004-05-08 12:15:10 yyyy-MM-dd HH:mm:ss</li> 
		 *            <li>2004-05-08 12:15 yyyy-MM-dd HH:mm</li> 
		 *            <li>2004-05-08  yyyy-MM-dd</li> 
		 *            <li>2004-05 yyyy-MM</li>           
		 * @return Timestamp时间戳
		 */
		 public static Timestamp StringtoSQLTimestamp(String str, String pattern) {
	        Timestamp time = null;       
	        if (!"".equals(str) && str != null) {
	            try {
	                SimpleDateFormat format = new SimpleDateFormat(pattern);
	                java.util.Date date = format.parse(str);
	                time = new Timestamp(date.getTime());
	            } catch (Exception e) {
	                System.out.println("转换字符串出现异常:" + e.getMessage());
	                e.printStackTrace();
	            }
	        }
	        return time;
	    }
	
	/**
	 * long毫秒类型数字时间转util日期 
	 * @param timeData long类型的毫秒数
	 ****/
    public static java.util.Date longToUtilDate(Long timeData){
    	java.util.Date date = new java.util.Date(timeData);
    	return date;
    }
		
	/**
	 * 将TimeStamp类型转换成String类型
	 * @param timestamp
	 *           要转换的TimeStamp对象
	 * @param pattern
	 *           格式类型 ，例如:<li>yyyy-MM-dd</li>
	 *                      <li>yyyy-MM-dd HH:mm:ss</li>
     *                      <li>yyyy年MM月dd日 HH时mm分ss秒</li>
	 * @return str时间字符串
	 */
	
	public static String TimestamptoString(Timestamp timestamp,String pattern){
		String str=null;
		java.util.Date utildate=new java.util.Date(timestamp.getTime());
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		str=format.format(utildate);
		return str;
	}

	/**
	 * 将SQL日期类型转换成String类型
	 * @param sqldate
	 *           要转换的SQL对象
	 * @param pattern
	 *           格式类型 ，例如:<li>yyyy-MM-dd</li>
	 *                         <li>yyyy-MM-dd HH:mm:ss</li>
     *                         <li>yyyy年MM月dd日 HH时mm分ss秒</li>
	 * @return str时间字符串
	 */
	public static String SQLDatetoString(java.sql.Date sqldate, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String str=null;
		str=format.format(sqldate);
		return str;
	}
	
	
	/**
	 * 将Util日期类型转换成String类型
	 * @param utildate
	 *           要转换的Util对象
	 * @param pattern
	 *           格式类型 ，例如:<li>yyyy-MM-dd</li>
	 *                      <li>yyyy-MM-dd HH:mm:ss</li>
     *                      <li>yyyy年MM月dd日 HH时mm分ss秒</li>
	 * @return str时间字符串
	 */
	public static String UtilDatetoString(java.util.Date utildate, String pattern) {
		if(StringUtil.isNull(utildate)) { return ""; }
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String str=null;
		str=format.format(utildate);
		return str;
	}

	 /**
	  *给定的日期,求它的多少天后或多少天前的日期,时分秒不变,计算日(天)
	  * @param initdate
	  *           传入的初始日期
	  * @param daynumber
	  *           多少天前用负的int,多少天后直接用数字
	  * @return 计算后的java.util.Date类型日期
	  * */
	 public static java.util.Date DateAddOperation(java.util.Date initdate,int daynumber){
		 Calendar cal = Calendar.getInstance();  
	     cal.setTime(initdate);
	     cal.add(Calendar.DATE, daynumber);  
	     return cal.getTime();
	 }
	 
	 /***
	  * 检查日期字符串是否为周末即周六周天
	  * @param dateStr 日期字符串
	  * @param pattern 格式类型例如:<li>yyyy-MM-dd</li>
	  ****/
	 public static boolean checkWeekend(String dateStr,String pattern){
         boolean retTar=false;
		 try {
			DateFormat format1 = new SimpleDateFormat(pattern);
	        java.util.Date date = format1.parse(dateStr);
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
	        	retTar= true;
	        } else{
	        	retTar= false;
	        }
		 } catch (ParseException e) {
			e.printStackTrace();
		 }
         return retTar;     
	 }

	/***
	 * 测试方法
	 * @throws Exception 
	 ***/
	public static void main(String args[]) throws Exception {
		System.out.println(checkWeekend("2019-06-29","yyyy-MM-dd"));
	}
}
