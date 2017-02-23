package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_airline_info")
public class TAirlineInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("航空公司代码")
	private String aircom;

	@Column
	@Comment("出发城市")
	private String leavecity;

	@Column
	@Comment("到达城市")
	private String arrvicity;

	@Column
	@Comment("去程日期")
	private Date leavedate;

	@Column
	@Comment("回程日期")
	private Date backdate;

	@Column
	@Comment("原价(单价)")
	private Double formprice;

	@Column
	@Comment("售价(单价)")
	private Double price;

	@Column
	@Comment("结算货币代码")
	private String currencyCode;

	@Column
	@Comment("数量")
	private Integer passengercount;

	@Column
	@Comment("乘客类型")
	private Integer passengertype;

	@Column
	@Comment("机票类型")
	private Integer tickettype;

	@Column
	@Comment("旅程类型")
	private Integer traveltype;

	@Column
	@Comment("需求id")
	private Integer needid;

	@Column
	@Comment("计划id")
	private Integer planid;

	@Column
	@Comment("出发时间")
	private String leavetime;

	@Column
	@Comment("抵达时间")
	private String arrivetime;

	@Column
	@Comment("航班号")
	private String ailinenum;

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TAirlineInfoEntity other = (TAirlineInfoEntity) obj;
		if (ailinenum == null) {
			if (other.ailinenum != null)
				return false;
		} else if (!ailinenum.equals(other.ailinenum))
			return false;
		if (arrivetime == null) {
			if (other.arrivetime != null)
				return false;
		} else if (!arrivetime.equals(other.arrivetime))
			return false;
		if (arrvicity == null) {
			if (other.arrvicity != null)
				return false;
		} else if (!arrvicity.equals(other.arrvicity))
			return false;
		if (leavecity == null) {
			if (other.leavecity != null)
				return false;
		} else if (!leavecity.equals(other.leavecity))
			return false;
		if (leavedate == null) {
			if (other.leavedate != null)
				return false;
		} else if (!leavedate.equals(other.leavedate))
			return false;
		if (leavetime == null) {
			if (other.leavetime != null)
				return false;
		} else if (!leavetime.equals(other.leavetime))
			return false;
		if (planid == null) {
			if (other.planid != null)
				return false;
		} else if (!planid.equals(other.planid))
			return false;
		return true;
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ailinenum == null) ? 0 : ailinenum.hashCode());
		result = prime * result + ((arrivetime == null) ? 0 : arrivetime.hashCode());
		result = prime * result + ((arrvicity == null) ? 0 : arrvicity.hashCode());
		result = prime * result + ((leavecity == null) ? 0 : leavecity.hashCode());
		result = prime * result + ((leavedate == null) ? 0 : leavedate.hashCode());
		result = prime * result + ((leavetime == null) ? 0 : leavetime.hashCode());
		result = prime * result + ((planid == null) ? 0 : planid.hashCode());
		return result;
	}

}