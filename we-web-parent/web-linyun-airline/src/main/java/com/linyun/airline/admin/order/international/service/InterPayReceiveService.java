/**
 * InterPayReceiveService.java
 * com.linyun.airline.admin.order.international.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.international.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.order.international.form.InterPaymentSqlForm;
import com.linyun.airline.admin.order.international.form.InterReceiptSqlForm;
import com.linyun.airline.entities.TReceiveEntity;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月18日 	 
 */
@IocBean
public class InterPayReceiveService extends BaseService<TReceiveEntity> {
	/**
	 * 国际收款列表
	 * <p>
	 * TODO国际收款列表
	 * @param sqlForm
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object listShouFuKuanData(InterReceiptSqlForm sqlForm, HttpServletRequest request) {
		Map<String, Object> listData = this.listPage4Datatables(sqlForm);
		return listData;
	}

	/**
	 * 国际付款列表数据
	 * <p>
	 * TODO国际付款列表数据
	 * @param sqlForm
	 * @param request
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object listFuKuanData(InterPaymentSqlForm sqlForm, HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;

	}

}
