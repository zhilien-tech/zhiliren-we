package com.pengcheng.feedback.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class GencodeUserUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**姓名*/
	private String name;
		
	/**年龄*/
	private Integer age;
		
	/**地址*/
	private String address;
		
}