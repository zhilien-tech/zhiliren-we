package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SRoleUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**角色名称*/
	private String name;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private Date createTime;
		
}