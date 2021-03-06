package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TDepartmentUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**公司id*/
	private long comId;

	/**部门名称*/
	private String deptName;

	/**备注*/
	private String remark;

}