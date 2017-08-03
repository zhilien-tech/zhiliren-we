/**
 * AuthenticatorTest.java
 * org.zxc.shiro
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.shiro.test.chapter2;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * @author   朱晓川
 * @Date	 2017年7月31日 	 
 */
public class AuthenticatorTest {
	private static final Log log = Logs.get();

	/**
	 * 测试必须全部通过才算通过
	 */
	@Test
	public void testAllSuccessfulStrategyWithSuccess() {
		login("classpath:chapter2/shiro-authenticator-all-success.ini");
		Subject subject = SecurityUtils.getSubject();

		//得到一个身份集合，其包含了Realm验证成功的身份信息
		PrincipalCollection principalCollection = subject.getPrincipals();
		Assert.assertEquals(2, principalCollection.asList().size());
		ThreadContext.unbindSubject();
	}

	@Test(expectedExceptions = UnknownAccountException.class)
	public void testAllSuccessfulStrategyWithFail() {
		login("classpath:chapter2/shiro-authenticator-all-fail.ini");
	}

	/**
	 * 至少通过一个
	 */
	@Test
	public void testAtLeastOneSuccessfulStrategyWithSuccess() {
		login("classpath:chapter2/shiro-authenticator-atLeastOne-success.ini");
		Subject subject = SecurityUtils.getSubject();

		//得到一个身份集合，其包含了Realm验证成功的身份信息
		PrincipalCollection principalCollection = subject.getPrincipals();
		Assert.assertEquals(2, principalCollection.asList().size());
	}

	/**
	 * 第一个必须通过
	 */
	@Test
	public void testFirstOneSuccessfulStrategyWithSuccess() {
		login("classpath:chapter2/shiro-authenticator-first-success.ini");
		Subject subject = SecurityUtils.getSubject();

		//得到一个身份集合，其包含了第一个Realm验证成功的身份信息
		PrincipalCollection principalCollection = subject.getPrincipals();
		Assert.assertEquals(1, principalCollection.asList().size());
	}

	/**
	 * 至少通过两个
	 */
	@Test
	public void testAtLeastTwoStrategyWithSuccess() {
		login("classpath:chapter2/shiro-authenticator-atLeastTwo-success.ini");
		Subject subject = SecurityUtils.getSubject();

		//得到一个身份集合，因为myRealm1和myRealm4返回的身份一样所以输出时只返回一个
		PrincipalCollection principalCollection = subject.getPrincipals();
		Assert.assertEquals(1, principalCollection.asList().size());
	}

	/**
	 * 只通过一个
	 */
	@Test
	public void testOnlyOneStrategyWithSuccess() {
		try {
			//身份认证
			login("classpath:chapter2/shiro-authenticator-onlyone-success.ini");
			log.info("===========认证成功==============");
		} catch (AuthenticationException e) {
			//认证失败  
			log.info("===========认证失败==============");
		}

		Subject subject = SecurityUtils.getSubject();

		//得到一个身份集合，因为myRealm1和myRealm4返回的身份一样所以输出时只返回一个
		PrincipalCollection principalCollection = subject.getPrincipals();
		Assert.assertEquals(1, principalCollection.asList().size());
	}

	private void login(String configFile) {
		//1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(configFile);

		//2、得到SecurityManager实例 并绑定给SecurityUtils
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		//3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("zhang", "123");

		subject.login(token);
	}

	/**
	 * testng使用@AfterMethod表示在每个测试方法之后执行
	 * junit使用@After
	 * <p>
	 * 注意:不是@AfterTest
	 */
	@AfterMethod
	public void tearDown() throws Exception {
		//注意：退出时请解除绑定Subject到线程 否则对下次测试造成影响
		ThreadContext.unbindSubject();
	}

}
