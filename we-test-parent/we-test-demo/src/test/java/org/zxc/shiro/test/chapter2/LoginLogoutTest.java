/**
 * ShiroHelloworldTest.java
 * org.zxc
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.shiro.test.chapter2;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Shiro LoginLogoutTest
 * <p>
 * 只是使用的配置文件不同，其他代码基本相同
 * @author   朱晓川
 * @Date	 2017年7月31日 	 
 */
public class LoginLogoutTest {

	/**ini文件users*/
	@Test
	public void testHelloworld() {
		/**
		 * 注意:1和2顺序不能颠倒，SecurityUtils必须先设置SecurityManager，然后才能通过它获取subject
		 */

		//1、从工厂获取 securityManager并且注入SecurityUtils(服务端)
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:chapter2/shiro.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		//2、获取Subject（客户端）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

		try {
			//3、登录，即身份验证  
			subject.login(token);
		} catch (AuthenticationException e) {
			//4、身份验证失败  
		}

		Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已认证通过

		//5、退出  
		subject.logout();
	}

	/**自定义realm*/
	@Test
	public void testCustomRealm() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:chapter2/shiro-realm.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录
		subject.logout();
	}

	/**
	 * 多个自定义realm,
	 * 权限校验的顺序按照属性文件的配置进行，只要有一个realm验证通过则为通过(默认的策略)
	 */
	@Test
	public void testCustomMultiRealm() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:chapter2/shiro-multi-realm.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录
		subject.logout();
	}

	/**jdbc-realm*/
	@Test
	public void testJDBCRealm() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:chapter2/shiro-jdbc-realm.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(true, subject.isAuthenticated()); //断言用户已经登录
		subject.logout();
	}

}
