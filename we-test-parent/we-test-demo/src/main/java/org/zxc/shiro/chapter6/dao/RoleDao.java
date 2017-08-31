package org.zxc.shiro.chapter6.dao;

import org.zxc.shiro.chapter6.entity.Role;

/**
 */
public interface RoleDao {

	public Role createRole(Role role);

	public void deleteRole(Long roleId);

	public void correlationPermissions(Long roleId, Long... permissionIds);

	public void uncorrelationPermissions(Long roleId, Long... permissionIds);

}
