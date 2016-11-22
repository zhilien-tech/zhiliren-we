package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SUserRoleMapUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**用户id*/
	private Long userId;
		
	/**角色id*/
	private Long roleId;
		
}