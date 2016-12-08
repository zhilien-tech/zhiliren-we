package com.linyun.airline.admin.authority.job.form;

import lombok.Data;

/**
* @ClassName: DeptJobForm
* @author 崔建斌 
* @date 2016年11月18日 下午2:04:15
 */
@Data
public class DeptJobForm {

	/**部门名称*/
	private String deptName;

	/**职位名称*/
	private String jobName;

	/**功能id串*/
	private String functionIds;

}
