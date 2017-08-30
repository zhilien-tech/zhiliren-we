package org.zxc.shiro.test.chapter4;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 演示如何通过配置文件进行注入
 */
public class IniMainTest {

	@Test
	public void test() {

		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
				"classpath:chapter4/shiro-config-main.ini");

		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();

		//将SecurityManager设置到SecurityUtils 方便全局使用
		SecurityUtils.setSecurityManager(securityManager);

		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");
		subject.login(token);

		Assert.assertTrue(subject.isAuthenticated());

	}
}
