package com.linyun.airline.admin.drawback.grabmail.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TGrabMailAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**主题*/
	private String theme;
		
	/**发件人*/
	private String sender;
		
	/**收件人*/
	private String addressee;
		
	/**发送时间*/
	private Date sendTime;
		
	/**抓取时间*/
	private Date grabTime;
		
	/**抓取状态(1-已抓;2-未抓;)*/
	private Integer grabStatus;
		
}