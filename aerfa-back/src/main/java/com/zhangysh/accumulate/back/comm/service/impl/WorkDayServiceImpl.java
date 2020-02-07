package com.zhangysh.accumulate.back.comm.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommWorkDay;
import com.zhangysh.accumulate.pojo.comm.viewobj.LayDateRenderVo;
import com.zhangysh.accumulate.pojo.sys.dataobj.AefsysPerson;
import com.zhangysh.accumulate.back.comm.dao.WorkDayDao;
import com.zhangysh.accumulate.back.comm.service.IWorkDayService;
import com.zhangysh.accumulate.back.sys.base.BaseMybatisDao;
import com.zhangysh.accumulate.common.constant.MarkConstant;
import com.zhangysh.accumulate.common.constant.SysDefineConstant;
import com.zhangysh.accumulate.common.constant.UtilConstant;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
import com.zhangysh.accumulate.common.util.DateOperate;
/**
 * 工作日 服务层实现
 * 
 * @author zhangysh
 * @date 2019年06月29日
 */
@Service
public class WorkDayServiceImpl implements IWorkDayService {

	@Autowired
	private WorkDayDao workDayDao;
	@Autowired
	private BaseMybatisDao baseMybatisDao;
	
    @Override
	public AefcommWorkDay getWorkDayById(Long id){
	    return workDayDao.getWorkDayById(id);
	}
	
	@Override
	public BsTableDataInfo listPageWorkDay(BsTablePageInfo pageInfo,AefcommWorkDay workDay){
	    //pagehelper方法调用
		Page<AefcommWorkDay> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		workDayDao.listWorkDay(workDay);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefcommWorkDay> listBypksWorkDay(String ids){
		return workDayDao.listBypksWorkDay(ConvertUtil.toStrArray(ids));
	}

	@Override
	public List<AefcommWorkDay> listWorkDay(AefcommWorkDay workDay){
		return workDayDao.listWorkDay(workDay);
	}

	@Override
	public int insertWorkDay(AefcommWorkDay workDay){
	    return workDayDao.insertWorkDay(workDay);
	}
	
	@Override
	public int updateWorkDay(AefcommWorkDay workDay){
		//当id为1的时候是定义的工作时间，要修改工作日相关属性
		if(SysDefineConstant.DB_WORK_DAY_TIME_DEFINE_ID.equals(workDay.getId())) {
			String startDate =workDay.getWorkDate().split(MarkConstant.MARK_SPLIT_EN_TILDE)[0];
			String endDate =workDay.getWorkDate().split(MarkConstant.MARK_SPLIT_EN_TILDE)[1];
			String updateWorkDayWorkTimeSql="update aefcomm_work_day set morning_duty_time='"+workDay.getMorningDutyTime()+"',morning_rush_time='"+workDay.getMorningRushTime()
			+"',afternoon_duty_time='"+workDay.getAfternoonDutyTime()+"',afternoon_rush_time='"+workDay.getAfternoonRushTime()+"',totle_work_time="+workDay.getTotleWorkTime() 
		    +" where work_date>='"+startDate+"' and work_date<='"+endDate+"' and id>1";
			baseMybatisDao.updateBySql(updateWorkDayWorkTimeSql);
		}
	    return workDayDao.updateWorkDay(workDay);
	}
	
	@Override
	public int deleteWorkDayById(Long id){
		return workDayDao.deleteWorkDayById(id);
	}
	
	@Override
	public int deleteWorkDayByIds(String ids){
		return workDayDao.deleteWorkDayByIds(ConvertUtil.toStrArray(ids));
	}

	@Override
	public List<LayDateRenderVo> getRenderDateByYear(AefsysPerson operPerson,int year) {
		List<LayDateRenderVo> RenderDateVoList=new ArrayList<LayDateRenderVo>();
		AefcommWorkDay searchWorkDay =new  AefcommWorkDay();
		searchWorkDay.setWorkDate(year+"");
		List<AefcommWorkDay> workDayList=listWorkDay(searchWorkDay);
		//存在初始化的数据就取判断是否上班标记
		if(workDayList!=null&&workDayList.size()>0) {
			for (int i = 1; i <= 12; i++) {
				int lastDay=DateOperate.getLastNumberDayOfMonth(year, i);
				String month = i < 10 ? ("0" + i) : ""+i;
				
				LayDateRenderVo layDateRenderVo=new LayDateRenderVo();
				Map<String,String> markMap=new HashMap<String,String>();
				for(int j=1;j<=lastDay;j++) {
					String day = j < 10 ? ("0" + j) : ""+j;
					//从查询出的日期列表中找出不上班的日期做标记
					String workDate=year+MarkConstant.MARK_SPLIT_EN_HYPHEN+month+MarkConstant.MARK_SPLIT_EN_HYPHEN+day;
					for(AefcommWorkDay worDay:workDayList){
						if( workDate.equals(worDay.getWorkDate()) && 
							SysDefineConstant.DB_WORK_DAY_STATUS_RUSH.equals(worDay.getWorkStatus())) {
							markMap.put(workDate, "");
						}
					}
				}
				layDateRenderVo.setYear(year+"");
				layDateRenderVo.setMonth(month);
				layDateRenderVo.setMonthMin(DateOperate.getFirstDayOfMonth(year, i));
				layDateRenderVo.setMonthMax(DateOperate.getLastDayOfMonth(year, i));
				layDateRenderVo.setMark(markMap);
				RenderDateVoList.add(layDateRenderVo);
			}
		}
		//为了保持数据完整性不存在就初始化日期数据
		else {
			List<AefcommWorkDay> batchSaveWorkDayList=new ArrayList<AefcommWorkDay>();
			AefcommWorkDay workTime=getWorkDayById(SysDefineConstant.DB_WORK_DAY_TIME_DEFINE_ID);
			for (int i = 1; i <= 12; i++) {
				int lastDay=DateOperate.getLastNumberDayOfMonth(year, i);
				String month = i < 10 ? ("0" + i) : ""+i;
				for(int j=1;j<=lastDay;j++) {
					String day = j < 10 ? ("0" + j) : ""+j;
					AefcommWorkDay workDay=new AefcommWorkDay();
					String workDate=year+MarkConstant.MARK_SPLIT_EN_HYPHEN+month+MarkConstant.MARK_SPLIT_EN_HYPHEN+day;
					workDay.setWorkDate(workDate);
					workDay.setMorningDutyTime(workTime.getMorningDutyTime());
					workDay.setMorningRushTime(workTime.getMorningRushTime());
					workDay.setAfternoonDutyTime(workTime.getAfternoonDutyTime());
					workDay.setAfternoonRushTime(workTime.getAfternoonRushTime());
					workDay.setTotleWorkTime(workTime.getTotleWorkTime());
					boolean weekendTar=DateOperate.checkWeekend(workDate, UtilConstant.NORMAL_MIDDLE_DATE);
					if(weekendTar) {
						workDay.setWorkStatus(SysDefineConstant.DB_WORK_DAY_STATUS_RUSH);
					}else {
						workDay.setWorkStatus(SysDefineConstant.DB_WORK_DAY_STATUS_DUTY);
					}
					workDay.setCreateBy(operPerson.getPersonName());
					workDay.setCreateTime(new Date());
					batchSaveWorkDayList.add(workDay);
				}
			}
			workDayDao.insertBatchWorkDay(batchSaveWorkDayList);
			//直接返回前台展示对象，因为是初始化的不查询数据库了
			for (int i = 1; i <= 12; i++) {
				int lastDay=DateOperate.getLastNumberDayOfMonth(year, i);
				String month = i < 10 ? ("0" + i) : ""+i;
				
				LayDateRenderVo layDateRenderVo=new LayDateRenderVo();
				Map<String,String> markMap=new HashMap<String,String>();
				for(int j=1;j<=lastDay;j++) {
					String day = j < 10 ? ("0" + j) : ""+j;
					String workDate=year+MarkConstant.MARK_SPLIT_EN_HYPHEN+month+MarkConstant.MARK_SPLIT_EN_HYPHEN+day;
					boolean weekendTar=DateOperate.checkWeekend(workDate, UtilConstant.NORMAL_MIDDLE_DATE);
					if(weekendTar) {
						markMap.put(workDate, "");
					}
				}
				layDateRenderVo.setYear(year+"");
				layDateRenderVo.setMonth(month);
				layDateRenderVo.setMonthMin(DateOperate.getFirstDayOfMonth(year, i));
				layDateRenderVo.setMonthMax(DateOperate.getLastDayOfMonth(year, i));
				layDateRenderVo.setMark(markMap);
				RenderDateVoList.add(layDateRenderVo);
			}
		}
		return RenderDateVoList;
	}
	
	@Override
	public String changeWorkDayStatus(AefsysPerson operPerson,String dateStr) {
		AefcommWorkDay searchWorkDay =new  AefcommWorkDay();
		searchWorkDay.setWorkDate(dateStr);
		List<AefcommWorkDay> workDayList=listWorkDay(searchWorkDay);
		AefcommWorkDay dbChangeWorkDay=workDayList.get(0);
		if(SysDefineConstant.DB_WORK_DAY_STATUS_DUTY.equals(dbChangeWorkDay.getWorkStatus())) {
			dbChangeWorkDay.setWorkStatus(SysDefineConstant.DB_WORK_DAY_STATUS_RUSH);
		}else {
			dbChangeWorkDay.setWorkStatus(SysDefineConstant.DB_WORK_DAY_STATUS_DUTY);
		}
		workDayDao.updateWorkDay(dbChangeWorkDay);
		if(SysDefineConstant.DB_WORK_DAY_STATUS_DUTY.equals(dbChangeWorkDay.getWorkStatus())) {
			return "已将"+dateStr+"设置为工作日";
		}else {
			return "已将"+dateStr+"设置为非工作日";
		}
	}
}
