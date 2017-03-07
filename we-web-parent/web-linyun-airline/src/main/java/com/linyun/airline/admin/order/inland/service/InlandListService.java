/**
 * InlandListService.java
 * com.linyun.airline.admin.order.inland.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.order.inland.service;

import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.common.enums.OrderStatusEnum;
import com.linyun.airline.entities.TUpOrderEntity;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2017年3月6日 	 
 */
@IocBean
public class InlandListService extends BaseService<TUpOrderEntity> {

	/**
	 * 格式化订单状态
	 * <p>
	 * TODO格式化订单状态
	 *
	 * @param status
	 * @return TODO(这里描述每个参数,如果有返回值描述返回值,如果有异常描述异常)
	 */
	public Object formatOrderStatus(Integer status) {
		String result = "";
		for (OrderStatusEnum orderstatus : OrderStatusEnum.values()) {
			if (status == orderstatus.intKey()) {
				result = orderstatus.value();
				break;
			}
		}
		return result;
	}

}
