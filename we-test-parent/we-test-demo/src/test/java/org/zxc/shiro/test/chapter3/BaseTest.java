package org.zxc.shiro.test.chapter3;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.testng.annotations.AfterMethod;

public abstract class BaseTest {

	@AfterMethod
	public void tearDown() throws Exception {
		//退出时请解除绑定Subject到线程 否则对下次测试造成影响
		ThreadContext.unbindSubject();
	}

	/**
	 * 通过IniSecurityManager对外部传入的用户名和密码进行认证，认证信息保存在SecurityUtils中(subject)
	 */
	protected void login(String configFile, String username, String password) {
		Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(configFile);

		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);

		subject.login(token);
	}

	public Subject subject() {
		return SecurityUtils.getSubject();
	}
}
