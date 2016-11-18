package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.joda.time.DateTime;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TJobUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**部门id*/
	private long deptId;

	/**职位名称*/
	private String name;

	/**创建时间*/
	private DateTime createTime;

	/**备注*/
	private String remark;

}