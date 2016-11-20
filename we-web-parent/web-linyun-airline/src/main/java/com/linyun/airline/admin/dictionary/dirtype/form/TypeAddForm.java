/**
 * TypeAddForm.java
 * com.xiaoka.template.admin.dictionary.dirtype.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirtype.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.NotEmpty;

import com.uxuexi.core.web.form.AddForm;

/**
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TypeAddForm extends AddForm {

	//字典类别编码
	private String typeCode;
	//字典类别名称
	@NotEmpty
	private String typeName;
	//描述
	private String description;
	//状态
	private int status;
}
