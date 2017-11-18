/**
 * 
 */
package com.jrsoft.common;

import java.util.List;

/**
 * 用于前端Easy UI DataGrid显示数据使用
 *
 * @author Chris Mao(Zibing) <chris.mao.zb@163.com>
 *
 * @version 1.0
 *
 */
public class EasyDataGrid<T> {
	
	private Long total;
	
	private List<T> rows;

	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}

	/**
	 * @return the rows
	 */
	public List<T> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DataGrid [total=" + total + ", rows=" + rows + "]";
	}

}
