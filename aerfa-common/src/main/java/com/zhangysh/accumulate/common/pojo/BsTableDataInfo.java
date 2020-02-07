package com.zhangysh.accumulate.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * bootstrap表格分页数据对象
 * @author zhangysh
 * @date 2018年8月30日
 */
public class BsTableDataInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 总记录数 */
    private long total;
    /** 列表数据 */
    private List<?> rows;

    /**
     * 表格数据对象
     */
    public BsTableDataInfo()
    {
    }

    /**
     * 构建分页分页数据对象
     * @param list 列表数据
     * @param total 总记录数
     */
    public BsTableDataInfo(List<?> list, int total)
    {
        this.rows = list;
        this.total = total;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public List<?> getRows()
    {
        return rows;
    }

    public void setRows(List<?> rows)
    {
        this.rows = rows;
    }

}
