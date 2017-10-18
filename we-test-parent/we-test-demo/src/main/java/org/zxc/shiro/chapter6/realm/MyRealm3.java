package org.zxc.shiro.chapter6.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import org.zxc.shiro.chapter6.entity.User;

/**
 */
public class MyRealm3 implements Realm {

	@Override
	public String getName() {
		return "c"; //realm name 为 “c”
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		User user = new User("zhang", "123");
		return new SimpleAuthenticationInfo(user, //身份 User类型
				"123", //凭据
				getName() //Realm Name
		);
	}
}
