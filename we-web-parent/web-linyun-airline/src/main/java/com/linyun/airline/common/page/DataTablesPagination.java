package com.linyun.airline.common.page;

import java.util.List;

import lombok.Data;

@Data
public class DataTablesPagination implements java.io.Serializable {

	private static final long serialVersionUID = -1585539529849523194L;

	/**每页记录数*/
	private int recordsTotal;

	/**当前页*/
	private int draw;

	/**总记录数*/
	private int recordsFiltered;

	/**
	 * 当前页的数据
	 */
	private List<?> data;

	public DataTablesPagination() {
	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 */
	public DataTablesPagination(int pageNo, int pageSize, int totalCount) {
		this.draw = pageNo;
		this.recordsTotal = pageSize;
		this.recordsFiltered = totalCount;

	}

	/**
	 * 构造器
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页几条数据
	 * @param totalCount
	 *            总共几条数据
	 * @param list
	 *            分页内容
	 */
	public DataTablesPagination(int pageNo, int pageSize, int totalCount, List<?> list) {
		this(pageNo, pageSize, totalCount);
		this.data = list;
	}

	/**
	 * 第一条数据位置
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (draw - 1) * recordsTotal;
	}
}
