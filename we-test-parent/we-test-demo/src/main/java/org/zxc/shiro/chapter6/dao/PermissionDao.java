package org.zxc.shiro.chapter6.dao;

import org.zxc.shiro.chapter6.entity.Permission;

/**
 */
public interface PermissionDao {

	public Permission createPermission(Permission permission);

	public void deletePermission(Long permissionId);

}
