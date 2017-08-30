package org.zxc.shiro.chapter3.permission;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * 权限解析器，从realm调用
 * addStringPermission("+user2+10")
 * addObjectPermission(new WildcardPermission("user1:*"));
 * 得到的授权信息中解析出Permission对象。
 * <p>
 * @author   朱晓川
 * @Date	 2017年8月29日
 */
public class BitAndWildPermissionResolver implements PermissionResolver {

	/**
	 * 如果权限字符串以加号开头，则解析得到BitPermission对象，否则得到WildcardPermission对象
	 */
	@Override
	public Permission resolvePermission(String permissionString) {
		if (permissionString.startsWith("+")) {
			return new BitPermission(permissionString);
		}
		return new WildcardPermission(permissionString);
	}
}
