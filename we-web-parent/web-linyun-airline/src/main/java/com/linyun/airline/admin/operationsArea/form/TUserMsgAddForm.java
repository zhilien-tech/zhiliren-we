package com.linyun.airline.admin.operationsArea.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TUserMsgAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**消息Id*/
	private Integer msgId;
		
	/**用户Id*/
	private Integer userId;
		
	/**用户类型*/
	private String userType;
		
	/**是否阅读*/
	private Integer isRead;
		
	/**阅读时间*/
	private Date readTime;
		
	/**来源Id*/
	private Integer fromId;
		
	/**消息来源*/
	private Integer msgSource;
		
	/**发送时间*/
	private Date sendTime;
		
}