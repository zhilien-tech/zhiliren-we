package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class DictTypeAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**字典类别编码*/
	private String typeCode;

	/**字典类别名称*/
	private String typeName;

	/**描述*/
	private String description;

	/**状态,0-冻结，1-启用，2--删除*/
	private int status;

}