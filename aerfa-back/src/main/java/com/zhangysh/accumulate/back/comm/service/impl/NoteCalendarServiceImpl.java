package com.zhangysh.accumulate.back.comm.service.impl;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.zhangysh.accumulate.pojo.comm.dataobj.AefcommNoteCalendar;
import com.zhangysh.accumulate.back.comm.dao.NoteCalendarDao;
import com.zhangysh.accumulate.back.comm.service.INoteCalendarService;
import com.zhangysh.accumulate.common.pojo.BsTableDataInfo;
import com.zhangysh.accumulate.common.pojo.BsTablePageInfo;

import com.zhangysh.accumulate.common.util.ConvertUtil;
import com.zhangysh.accumulate.common.util.DateOperate;
/**
 * 日历记事本 服务层实现
 * 
 * @author zhangysh
 * @date 2019年10月12日
 */
@Service
public class NoteCalendarServiceImpl implements INoteCalendarService {

	@Autowired
	private NoteCalendarDao noteCalendarDao;

    @Override
	public AefcommNoteCalendar getNoteCalendarById(Long id){
	    return noteCalendarDao.getNoteCalendarById(id);
	}
	
	@Override
	public BsTableDataInfo listPageNoteCalendar(BsTablePageInfo pageInfo,AefcommNoteCalendar noteCalendar){
	    //pagehelper方法调用
		Page<AefcommNoteCalendar> page =PageHelper.startPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getOrderBy());
		noteCalendarDao.listNoteCalendar(noteCalendar);
		BsTableDataInfo tableInfo=new BsTableDataInfo();
		tableInfo.setTotal(page.getTotal());
		tableInfo.setRows(page.getResult());
	    return tableInfo; 
	}

	@Override
	public List<AefcommNoteCalendar> listBypksNoteCalendar(String ids){
		return noteCalendarDao.listBypksNoteCalendar(ConvertUtil.toStrArray(ids));
	}

	@Override
	public List<AefcommNoteCalendar> listNoteCalendar(AefcommNoteCalendar noteCalendar){
		return noteCalendarDao.listNoteCalendar(noteCalendar);
	}

	@Override
	public List<AefcommNoteCalendar> listNoteCalendarAround(AefcommNoteCalendar noteCalendar){
		return noteCalendarDao.listNoteCalendarAround(noteCalendar);
	}
	
	@Override
	public int insertNoteCalendar(AefcommNoteCalendar noteCalendar){
	    return noteCalendarDao.insertNoteCalendar(noteCalendar);
	}
	
	@Override
	public int updateNoteCalendar(AefcommNoteCalendar noteCalendar){
	    return noteCalendarDao.updateNoteCalendar(noteCalendar);
	}
	
	@Override
	public int deleteNoteCalendarById(Long id){
		return noteCalendarDao.deleteNoteCalendarById(id);
	}
	
	@Override
	public int deleteNoteCalendarByIds(String ids){
		return noteCalendarDao.deleteNoteCalendarByIds(ConvertUtil.toStrArray(ids));
	}
	
}
