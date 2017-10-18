package org.zxc.shiro.chapter3.permission;

import java.util.Arrays;
import java.util.Collection;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * 角色权限解析器。处理Realm中addRole("role2")添加的权限
 * <p>
 * @author   朱晓川
 * @Date	 2017年8月29日
 */
public class MyRolePermissionResolver implements RolePermissionResolver {
	/**
	 * 如果角色字符串等于role1，则拥有menu:*权限
	 */
	@Override
	public Collection<Permission> resolvePermissionsInRole(String roleString) {
		if ("role1".equals(roleString)) {
			return Arrays.asList((Permission) new WildcardPermission("menu:*"));
		}
		return null;
	}
}
