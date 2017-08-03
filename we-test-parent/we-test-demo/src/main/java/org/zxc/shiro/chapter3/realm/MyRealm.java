package org.zxc.shiro.chapter3.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.zxc.shiro.chapter3.permission.BitPermission;

public class MyRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
		authInfo.addRole("role1");
		authInfo.addRole("role2");
		authInfo.addObjectPermission(new BitPermission("+user1+10"));
		authInfo.addObjectPermission(new WildcardPermission("user1:*"));
		authInfo.addStringPermission("+user2+10");
		authInfo.addStringPermission("user2:*");
		return authInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal(); //得到用户名
		String password = new String((char[]) token.getCredentials()); //得到密码
		if (!"zhang".equals(username)) {
			throw new UnknownAccountException(); //如果用户名错误
		}
		if (!"123".equals(password)) {
			throw new IncorrectCredentialsException(); //如果密码错误
		}
		//如果身份认证验证成功，返回一个AuthenticationInfo实现；
		return new SimpleAuthenticationInfo(username, password, getName());
	}
}
