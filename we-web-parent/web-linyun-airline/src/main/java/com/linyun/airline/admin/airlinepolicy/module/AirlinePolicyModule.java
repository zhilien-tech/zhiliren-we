/**
 * AirlinePolicyModule.java
 * com.linyun.airline.admin.airlinepolicy.module
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.airlinepolicy.module;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.linyun.airline.admin.airlinepolicy.service.AirlinePolicyService;
import com.linyun.airline.common.base.UploadService;
import com.linyun.airline.common.base.Uploader;
import com.linyun.airline.common.constants.CommonConstants;
import com.linyun.airline.common.enums.AirlinePolicyEnum;
import com.linyun.airline.forms.TAirlinePolicyAddForm;
import com.linyun.airline.forms.TAirlinePolicyForm;
import com.uxuexi.core.web.chain.support.JsonResult;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月8日 	 
 */
@IocBean
@At("/admin/airlinepolicy")
@Filters
public class AirlinePolicyModule {

	@Inject
	private UploadService qiniuUploadService;//文件上传
	@Inject
	private AirlinePolicyService airlinePolicyService;

	/**
	 * 跳转到list页面
	 * @param sqlForm
	 * @param pager
	 */
	@At
	@Ok("jsp")
	public Object list(@Param("..") final Pager pager) {
		/*Map<String, Object> map = Maps.newHashMap();
		List<Record> deplist = grabfileViewService.getFolderInfo(sqlManager);
		map.put("deplist", deplist);
		map.put("dataStatusEnum", EnumUtil.enum2(DataStatusEnum.class));*/
		/*airlinePolicyService.findConditionList()*/
		return airlinePolicyService.findConditionList();
	}

	/**
	 * 列表查询
	 */
	@At
	public Object listData(@Param("..") final TAirlinePolicyForm sqlForm, final HttpSession session) {
		return airlinePolicyService.listPage4Datatables(sqlForm);
	}

	/**
	 * 上传文件
	 */
	@At
	@Ok("json")
	public Object uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(CommonConstants.CHARACTER_ENCODING_PROJECT);//字符编码为utf-8
		response.setCharacterEncoding(CommonConstants.CHARACTER_ENCODING_PROJECT);
		Uploader uploader = new Uploader(request, qiniuUploadService);
		uploader.upload();
		String url = CommonConstants.IMAGES_SERVER_ADDR + uploader.getUrl();
		System.out.println(url);
		return url;
	}

	/**
	 * 保存上传文件
	 * @param addForm
	 */
	@At
	public Object saveUploadFile(@Param("..") TAirlinePolicyAddForm addForm) {
		addForm.setCreateTime(new Date());
		addForm.setUpdateTime(new Date());
		addForm.setStatus(AirlinePolicyEnum.ENABLE.intKey());
		addForm.setUrl(addForm.getUrl());
		addForm.setFileName(addForm.getFileName());
		addForm.setFileSize(addForm.getFileSize());
		return airlinePolicyService.add(addForm);
	}

	/**
	 * 删除记录
	 */
	@At
	public Object delete(@Param("id") final long id) {
		airlinePolicyService.deleteById(id);
		return JsonResult.success("删除成功");
	}
}
