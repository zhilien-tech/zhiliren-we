package org.zxc.shiro.chapter6.service;

import org.zxc.shiro.chapter6.entity.Permission;

/**
 */
public interface PermissionService {
	/**
	 * 添加一条权限
	 */
	public Permission createPermission(Permission permission);

	/**
	 * 按id删除一条权限
	 */
	public void deletePermission(Long permissionId);
}
