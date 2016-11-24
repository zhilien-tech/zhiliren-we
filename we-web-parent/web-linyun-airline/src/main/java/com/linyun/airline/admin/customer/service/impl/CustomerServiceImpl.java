/**
 * CustomerServiceImpl.java
 * com.linyun.airline.admin.customer.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.customer.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.customer.service.CustomerService;
import com.linyun.airline.entities.TCustomerInfoEntity;
import com.linyun.airline.forms.TCustomerInfoUpdateForm;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2016年11月20日 	 
 */
@IocBean(name = "CustomerService")
public class CustomerServiceImpl implements CustomerService {

	@Inject
	private IDbDao dbDao;

	//根据id查找
	@Override
	public Map<String, Object> findCustomerById(long id) {
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("customerInfo", dbDao.fetch(TCustomerInfoEntity.class, id));
		return obj;
	}

	//更新
	@Override
	public boolean updateCustomer(TCustomerInfoUpdateForm form) {
		FormUtil.modify(dbDao, form, TCustomerInfoEntity.class);
		return true;
	}

	//删除
	@Override
	public boolean deleteCustomer(long id) {
		FormUtil.delete(dbDao, TCustomerInfoEntity.class, id);
		return true;
	}

	//批量删除
	@Override
	public boolean batchDeleteCustomer(long[] ids) {
		FormUtil.delete(dbDao, TCustomerInfoEntity.class, ids);
		return true;
	}

}
