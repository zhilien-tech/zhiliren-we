package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SUserAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**登录名*/
	private String username;
		
	/**登录密码*/
	private String password;
		
	/**用户状态:0-未激活，1-激活，2-冻结*/
	private Integer status;
		
	/**用户类型:0-前台用户，1-后台用户*/
	private Integer userType;
		
	/**创建时间*/
	private Date createTime;
		
}