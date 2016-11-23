package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.joda.time.DateTime;

import com.uxuexi.core.web.form.AddForm;

/**
* @ClassName: TJobAddForm
* @author 崔建斌 
* @date 2016年11月18日 下午2:04:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TJobAddForm extends AddForm {

	/**职位名称*/
	private String name;

	/**部门id*/
	private long deptId;

	/**创建时间*/
	private DateTime createTime;

	/**备注*/
	private String remark;

}
