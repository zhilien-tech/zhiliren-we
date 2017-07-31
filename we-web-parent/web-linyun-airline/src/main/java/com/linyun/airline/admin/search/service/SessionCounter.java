/**
 * SessionCounter.java
 * com.linyun.airline.admin.search.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.linyun.airline.admin.search.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.sabre.bean.BargainFinderSearch;
import com.linyun.airline.entities.TUserEntity;
import com.uxuexi.core.common.util.Util;

/**
 * TODO(session 监听事件)
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年7月6日 	 
 */
@IocBean
public class SessionCounter implements HttpSessionListener {

	/* Session创建事件  */
	@Override
	public void sessionCreated(HttpSessionEvent event) {

	}

	/* Session失效事件 */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		//当前用户id
		TUserEntity loginUser = (TUserEntity) session.getAttribute(LoginService.LOGINUSER);
		if (!Util.isEmpty(loginUser)) {
			long userId = loginUser.getId();
			Map<String, BargainFinderSearch> cache = SearchViewService.cache;
			Set<Entry<String, BargainFinderSearch>> entrySet = cache.entrySet();
			if (!Util.isEmpty(entrySet)) {
				List<String> cacheKey = new ArrayList<String>();
				for (Map.Entry<String, BargainFinderSearch> map : entrySet) {
					String key = map.getKey();
					BargainFinderSearch value = map.getValue();
					if (key.startsWith(userId + "-")) {
						cacheKey.add(key);
					}
				}
				for (String key : cacheKey) {
					cache.remove(key);
				}

			}

		}
	}
}
