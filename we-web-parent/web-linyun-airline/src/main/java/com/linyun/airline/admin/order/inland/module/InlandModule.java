/**
 * InlandModule.java
 * com.linyun.airline.admin.order.inland.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.module;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.order.inland.form.InlandListSearchForm;
import com.linyun.airline.admin.order.inland.service.InlandService;

/**
 * TODO内陆跨海module
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年2月13日 	 
 */
@IocBean
@At("admin/inland")
public class InlandModule {

	@Inject
	private InlandService inlandService;

	/**
	 * 跳转到内陆跨海列表
	 */
	@At
	@Ok("jsp")
	public Object list(HttpServletRequest request) {
		return null;
	}

	/**
	 * 查询内陆跨海列表数据
	 */
	@At
	public Object listData(@Param("..") InlandListSearchForm form) {
		return inlandService.listData(form);
	}

	/**
	 * 跳转到内陆跨海添加页面
	 */
	@At
	@Ok("jsp")
	public Object addOrder(HttpServletRequest request) {
		return null;
	}

	/**
	 * 添加内陆跨海订单
	 */
	@At
	@POST
	public Object addOrderInfo(@Param("data") String data) {
		return inlandService.addOrderInfo(data);
	}

	/**
	 * 跳转到查询订单详情页面
	 */
	@At
	@Ok("jsp")
	public Object queryDetail(@Param("id") Integer id) {
		return inlandService.queryDetail(id);
	}

	/**
	 * 保存查询订单详情
	 */
	@At
	@POST
	public Object saveOrderInfo(@Param("data") String data) {
		return inlandService.saveOrderInfo(data);
	}
}
