package org.zxc.shiro.chapter6.service;

import org.zxc.shiro.chapter6.dao.PermissionDao;
import org.zxc.shiro.chapter6.dao.PermissionDaoImpl;
import org.zxc.shiro.chapter6.entity.Permission;

/**
 */
public class PermissionServiceImpl implements PermissionService {

	private PermissionDao permissionDao = new PermissionDaoImpl();

	public Permission createPermission(Permission permission) {
		return permissionDao.createPermission(permission);
	}

	public void deletePermission(Long permissionId) {
		permissionDao.deletePermission(permissionId);
	}
}
