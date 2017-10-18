package org.zxc.shiro.chapter6.service;

import org.zxc.shiro.chapter6.entity.Role;

/**
 */
public interface RoleService {
	/**
	 * 添加角色
	 */
	public Role createRole(Role role);

	/**
	 * 按id删除角色
	 */
	public void deleteRole(Long roleId);

	/**
	 * 添加角色-权限之间关系
	 * @param roleId
	 * @param permissionIds
	 */
	public void correlationPermissions(Long roleId, Long... permissionIds);

	/**
	 * 移除角色-权限之间关系
	 * @param roleId
	 * @param permissionIds
	 */
	public void uncorrelationPermissions(Long roleId, Long... permissionIds);

}
