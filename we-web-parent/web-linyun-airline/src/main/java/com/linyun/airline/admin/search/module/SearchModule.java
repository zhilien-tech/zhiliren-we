package com.linyun.airline.admin.search.module;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.search.service.SearchViewService;

@IocBean
@At("/admin/search")
public class SearchModule {

	private static final Log log = Logs.get();

	@Inject
	private SearchViewService searchViewService;

	/**
	 * 跳转到查询页面
	 */
	@At
	@GET
	@Ok("jsp")
	public Object searchTicket() {
		return null;
	}

	/**
	 * 获取客户姓名下拉
	 */
	@At
	@POST
	public Object getLinkNameSelect(@Param("linkname") String linkname) {
		return searchViewService.getLinkNameSelect(linkname);
	}

	/**
	 * 获取電話下拉
	 */
	@At
	@POST
	public Object getPhoneNumSelect(@Param("phonenum") String phonenum) {
		return searchViewService.getPhoneNumSelect(phonenum);
	}

	/**
	 * 根据Id查询客户信息
	 */
	@At
	@POST
	public Object getCustomerById(@Param("id") Long id) {
		return searchViewService.getCustomerById(id);
	}

	//出发城市查询
	@At
	@POST
	public Object getCitys(@Param("id") final long id) throws Exception {
		return searchViewService.getCitys(id);
	}
}