/**
 * AuthorityBean.java
 * com.linyun.airline.admin.authority.authoritymanage.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.authority.authoritymanage.entity;

import lombok.Data;

/**
 * 权限管理bean
 * @author   崔建斌
 * @Date	 2016年12月7日 	 
 */
@Data
public class AuthorityBean implements Comparable<AuthorityBean> {
	//部门id
	private long deptId;
	//部门名称
	private String deptName;
	//职位id
	private long jobId;
	//职位名称
	private String name;
	//功能id
	private long funId;
	//模块名称(功能名称)
	private String moduleName;

	public void setStatus(String deptName, String name, String moduleName) {
		this.deptName = deptName;
		this.name = name;
		this.moduleName = moduleName;
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (deptId ^ (deptId >>> 32));
		result = prime * result + ((deptName == null) ? 0 : deptName.hashCode());
		result = prime * result + (int) (funId ^ (funId >>> 32));
		result = prime * result + (int) (jobId ^ (jobId >>> 32));
		result = prime * result + ((moduleName == null) ? 0 : moduleName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthorityBean other = (AuthorityBean) obj;
		if (deptId != other.deptId)
			return false;
		if (deptName == null) {
			if (other.deptName != null)
				return false;
		} else if (!deptName.equals(other.deptName))
			return false;
		if (funId != other.funId)
			return false;
		if (jobId != other.jobId)
			return false;
		if (moduleName == null) {
			if (other.moduleName != null)
				return false;
		} else if (!moduleName.equals(other.moduleName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**使用次数*/
	private int count;

	@Override
	public int compareTo(AuthorityBean o) {
		if (this.count > o.count) {
			return 1;
		} else if (this.count < o.count) {
			return -1;
		} else {
			return 0;
		}
	}
}
