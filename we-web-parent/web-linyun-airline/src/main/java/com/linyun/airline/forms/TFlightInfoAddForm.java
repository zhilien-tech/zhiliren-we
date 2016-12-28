package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TFlightInfoAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**出发城市*/
	private String leavecity;
		
	/**返回城市*/
	private String backcity;
		
	/**航班号*/
	private String airlinenum;
		
	/**起飞时间*/
	private String leavetime;
		
	/**到达时间*/
	private String backtime;
		
	/**班期*/
	private String banqi;
		
	/**机型*/
	private String airtype;
		
	/**航空公司*/
	private Integer aircomid;
		
}