package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TPlanInfoAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**旅行社名称*/
	private String travelname;
		
	/**航空公司名称*/
	private String airlinename;
		
	/**去程日期*/
	private String leavesdate;
		
	/**去程航班*/
	private String leaveairline;
		
	/**出发城市*/
	private String leavescity;
		
	/**回程日期*/
	private String backsdate;
		
	/**回程航班*/
	private String backairline;
		
	/**返回城市*/
	private String backscity;
		
	/**人数*/
	private Integer peoplecount;
		
	/**天数*/
	private Integer dayscount;
		
	/**联运城市*/
	private String unioncity;
		
	/**1、系列团，2、临时团；3关闭*/
	private Integer teamtype;
		
	/**订单号*/
	private String ordernumber;
		
	/**是否保存*/
	private Integer issave;
		
	/**操作人*/
	private Integer opid;
		
	/**创建时间*/
	private Date createtime;
		
	/**最后修改时间*/
	private Date laseupdatetime;
		
}