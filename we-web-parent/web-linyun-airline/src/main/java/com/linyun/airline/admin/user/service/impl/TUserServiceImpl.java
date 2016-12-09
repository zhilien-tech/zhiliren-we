/**
 * TUserServiceImpl.java
 * com.linyun.airline.admin.user.service.impl
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.authority.function.entity.TFunctionEntity;
import com.linyun.airline.admin.authority.job.entity.TJobEntity;
import com.linyun.airline.admin.user.service.UserViewService;
import com.linyun.airline.common.enums.UserStatusEnum;
import com.linyun.airline.common.enums.UserTypeEnum;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.entities.TUserJobEntity;
import com.linyun.airline.forms.TUserModForm;
import com.uxuexi.core.common.util.EnumUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.util.DbSqlUtil;
import com.uxuexi.core.web.base.service.BaseService;
import com.uxuexi.core.web.util.FormUtil;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月18日 	 
 */
@IocBean(name = "tuserService")
public class TUserServiceImpl extends BaseService<TUserEntity> implements UserViewService {

	@Override
	public boolean update(TUserModForm form) {
		//修改用户实体
		FormUtil.modify(dbDao, form, TUserEntity.class);

		//更新用户的角色
		updateUserJobMap(form);
		return true;

	}

	@Override
	public Map<String, Object> findUser(long userId) {
		Map<String, Object> obj = new HashMap<String, Object>();

		//全部职位
		List<TJobEntity> allJob = dbDao.query(TJobEntity.class, null, null);

		//根据用户查询user_role
		List<TUserJobEntity> relation = dbDao.query(TUserJobEntity.class, Cnd.where("userId", "=", userId), null);

		//如果该用户拥有功能
		if (!Util.isEmpty(relation)) {
			//该职位的功能id集合
			List<Long> existsJobIds = new ArrayList<Long>();
			for (TUserJobEntity r : relation) {
				existsJobIds.add(r.getCompanyJobId());
			}

			for (TJobEntity job : allJob) {
				if (existsJobIds.contains(job.getId())) {
					job.setChecked(true);
				} else {
					job.setChecked(false);
				}
			}
		}
		obj.put("roleList", allJob); //所有职位
		obj.put("user", dbDao.fetch(TUserEntity.class, userId)); //用户实体
		obj.put("userTypeEnum", EnumUtil.enum2(UserTypeEnum.class));//用户类型
		obj.put("userStatusEnum", EnumUtil.enum2(UserStatusEnum.class)); //用户状态
		return obj;
	}

	/**更新用户的职位*/
	public void updateUserJobMap(TUserModForm userForm) {
		List<TUserJobEntity> mapList = userForm.getMap();

		Long userId = userForm.getId();
		//根据用户查询用户职位关系
		List<TUserJobEntity> before = dbDao.query(TUserJobEntity.class, Cnd.where("userId", "=", userId), null);

		if (!Util.isEmpty(mapList)) {
			for (TUserJobEntity map : mapList) {
				//职位id为空则不处理
				if (Util.isEmpty(map.getUserid())) {
					continue;
				}
				map.setUserid(userId);
			}
		}
		dbDao.updateRelations(before, mapList);
	}

	@Override
	public TUserEntity findUser(String loginName, String passwd) {
		TUserEntity user = dbDao.fetch(TUserEntity.class,
				Cnd.where("userName", "=", loginName).and("password", "=", passwd));

		if (Util.isEmpty(user)) {
			user = dbDao.fetch(TUserEntity.class, Cnd.where("telephone", "=", loginName).and("password", "=", passwd));
		}
		return user;
	}

	@Override
	public List<TFunctionEntity> findUserFunctions(long userId) {
		Sql sql = Sqls.create(sqlManager.get("user_function_all"));
		sql.params().set("userId", userId);
		return DbSqlUtil.query(dbDao, TFunctionEntity.class, sql, null);
	}

}
