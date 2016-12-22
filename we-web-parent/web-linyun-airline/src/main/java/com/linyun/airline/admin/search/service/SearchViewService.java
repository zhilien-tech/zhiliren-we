package com.linyun.airline.admin.search.service;

import java.util.List;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.linyun.airline.admin.customer.service.CustomerViewService;
import com.linyun.airline.admin.operationsArea.entities.TMessageEntity;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class SearchViewService extends BaseService<TMessageEntity> {
	private static final Log log = Logs.get();

	@Inject
	private CustomerViewService customerViewService;

	/**
	 * 
	 * TODO(获取客户姓名下拉列表)
	 *
	 * @param linkname
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getLinkNameSelect(String linkname) {
		List<TCustomerInfoEntity> customerInfos = customerViewService.getCustomerInfoByLinkName(linkname);
		return customerInfos;
	}

	/**
	 * 
	 * TODO(获取客户姓名下拉列表)
	 *
	 * @param linkname
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getPhoneNumSelect(String phonenum) {
		List<TCustomerInfoEntity> customerInfos = customerViewService.getCustomerInfoByPhone(phonenum);
		return customerInfos;
	}

	/**
	 * 
	 * TODO(获取id查询客户信息)
	 *
	 * @param linkname
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object getCustomerById(Long id) {
		return customerViewService.getCustomerById(id);
	}

	public Object getCitys(Long id) {
		return customerViewService.getOutCitys(id);
	}
}