package com.zlr.feedback.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class JjuserAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**姓名*/
	private String name;
		
	/**年龄*/
	private Integer age;
		
}