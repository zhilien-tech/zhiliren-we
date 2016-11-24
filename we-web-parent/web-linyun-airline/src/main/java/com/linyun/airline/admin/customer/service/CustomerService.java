/**
 * CustomerService.java
 * com.linyun.airline.admin.customer.service
 * Copyright (c) 2016, 北京科技有限公司版权所有.
 */

package com.linyun.airline.admin.customer.service;

import java.util.Map;

import com.linyun.airline.forms.TCustomerInfoUpdateForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2016年11月20日 	 
 */
public interface CustomerService {

	//根据id查询客户
	public Map<String, Object> findCustomerById(long id);

	//更新
	public boolean updateCustomer(TCustomerInfoUpdateForm form);

	//删除
	public boolean deleteCustomer(long id);

	//批量删除
	public boolean batchDeleteCustomer(long[] ids);

}
