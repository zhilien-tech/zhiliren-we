/**
 * PlanMakeService.java
 * com.linyun.airline.admin.customneeds.service
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.customneeds.service;

import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.entities.TPlanInfoEntity;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2016年12月6日 	 
 */
@IocBean
public class PlanMakeService extends BaseService<TPlanInfoEntity> {

	/**
	 * 获取旅行社名称下拉
	 * <p>
	 * 获取旅行社名称下拉
	 *
	 * @return 返回旅行社名称的下拉列表
	 */
	public Object getTravelNameSelect() {

		return null;

	}

}
