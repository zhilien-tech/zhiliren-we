package com.xiaoka.template.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SRoleFunctionMapAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**角色id*/
	private Integer roleId;
		
	/**功能id*/
	private Integer functionId;
		
}