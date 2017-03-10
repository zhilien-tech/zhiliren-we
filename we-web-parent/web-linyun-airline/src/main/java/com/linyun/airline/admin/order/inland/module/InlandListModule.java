/**
 * InlandListModule.java
 * com.linyun.airline.admin.order.inland.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.order.inland.service.InlandListService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月6日 	 
 */
@IocBean
@At("admin/inland")
public class InlandListModule {

	@Inject
	private InlandListService inlandListService;

	/**
	 * 格式化订单状态
	 */
	@At
	@POST
	public Object formatOrderStatus(@Param("status") Integer status) {
		return inlandListService.formatOrderStatus(status);
	}
}
