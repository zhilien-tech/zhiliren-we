/**
 * ApplyApprovalModule.java
 * com.linyun.airline.admin.applyapproval.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.applyapproval.module;

import javax.servlet.http.HttpSession;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.applyapproval.service.ApplyApprovalService;
import com.linyun.airline.forms.ApplyApprovalForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月15日 	 
 */
@IocBean
@At("/admin/applyapproval")
@Filters
public class ApplyApprovalModule {
	@Inject
	private ApplyApprovalService applyApprovalService;

	/**
	 * 跳转到list页面
	 * @param sqlForm
	 * @param pager
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final Pager pager, HttpSession session) {
		/*Map<String, Object> map = Maps.newHashMap();
		List<Record> deplist = grabfileViewService.getFolderInfo(sqlManager);
		map.put("deplist", deplist);
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));*/
		/*airlinePolicyService.findConditionList()*/
		return applyApprovalService.listPage4Datatables(session);
	}

	/**
	 * 列表查询
	 */
	@At
	public Object listData(@Param("..") final ApplyApprovalForm sqlForm, final HttpSession session) {

		return null;
	}

	/**
	 * 跳转到list页面
	 * @param sqlForm
	 * @param pager
	 */
	@At
	@Ok("jsp")
	public Object dataList(@Param("..") final Pager pager) {
		/*Map<String, Object> map = Maps.newHashMap();
		List<Record> deplist = grabfileViewService.getFolderInfo(sqlManager);
		map.put("deplist", deplist);
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));*/
		/*airlinePolicyService.findConditionList()*/
		return null;
	}

	/**
	 * 跳转到list页面
	 * @param sqlForm
	 * @param pager
	 */
	@At
	@Ok("jsp")
	public Object detailList(@Param("..") final Pager pager) {
		/*Map<String, Object> map = Maps.newHashMap();
		List<Record> deplist = grabfileViewService.getFolderInfo(sqlManager);
		map.put("deplist", deplist);
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));*/
		/*airlinePolicyService.findConditionList()*/
		return null;
	}
}
