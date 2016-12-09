package com.linyun.airline.admin.user.service;

import java.util.List;
import java.util.Map;

import org.nutz.ioc.loader.annotation.IocBean;

import com.linyun.airline.admin.authority.function.entity.TFunctionEntity;
import com.linyun.airline.entities.TUserEntity;
import com.linyun.airline.forms.TUserModForm;

@IocBean
public interface UserViewService {

	/**
	 * 修改用户信息
	 * @param form 
	 * @return
	 */
	public boolean update(TUserModForm form);

	/**
	 * 查询用户信息(包含用户的角色)
	 * list - 角色列表
	 * role - 用户实体
	 * @param id  用户id
	 */
	Map<String, Object> findUser(long userId);

	/**根据用户名或手机号和密码查询用户*/
	TUserEntity findUser(final String loginName, final String passwd);

	List<TFunctionEntity> findUserFunctions(long userId);
}