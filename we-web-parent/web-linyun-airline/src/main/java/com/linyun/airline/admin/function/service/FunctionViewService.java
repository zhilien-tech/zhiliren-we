package com.linyun.airline.admin.function.service;

import com.linyun.airline.entities.TFunctionEntity;


public interface FunctionViewService {

	/**
	 * 根据请求路径查询功能
	 * @param requestPath
	 * @return
	 */
	public TFunctionEntity findFuctionByRequestPath(String requestPath);
}