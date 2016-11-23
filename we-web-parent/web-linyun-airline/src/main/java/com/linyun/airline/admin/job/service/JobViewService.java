package com.linyun.airline.admin.job.service;

import java.util.Map;

import com.linyun.airline.forms.TJobModForm;


public interface JobViewService {

	/**
	 * 修改职位信息
	 * @param form 
	 * @return
	 */
	public boolean update(TJobModForm form) ;
	
	/**
	 * 查询角色信息(包含功能)
	 * list - 功能列表
	 * role - 角色实体
	 * @param id  角色id
	 */
	Map<String,Object> findJob(long id) ;
}