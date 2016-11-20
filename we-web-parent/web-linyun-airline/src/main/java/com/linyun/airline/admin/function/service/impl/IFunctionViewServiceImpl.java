/**
 * IFunctionViewServiceImpl.java
 * com.linyun.airline.admin.function.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.function.service.impl;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.function.service.FunctionViewService;
import com.linyun.airline.entities.TFunctionEntity;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月18日 	 
 */
@IocBean(name = "functionViewService")
public class IFunctionViewServiceImpl extends BaseService<TFunctionEntity> implements FunctionViewService {

	@Override
	public TFunctionEntity findFuctionByRequestPath(String requestPath) {
		return dbDao.fetch(TFunctionEntity.class, Cnd.where("url", "LIKE", "%" + requestPath + "%"));
	}

}
