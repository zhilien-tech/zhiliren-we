package org.zxc.shiro.test.chapter6;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.zxc.shiro.test.chapter6.BaseTest;

/**
 */
public class UserRealmTest extends BaseTest {

	@Test
	public void testLoginSuccess() {
		login("classpath:chapter6/shiro.ini", u1.getUsername(), password);
		Assert.assertTrue(subject().isAuthenticated());
	}
	//	@Test(expectedExceptions = UnknownAccountException.class)
	//	public void testLoginFailWithUnknownUsername() {
	//		login("classpath:chapter6/shiro.ini", u1.getUsername() + "1", password);
	//	}
	//
	//	@Test(expectedExceptions = IncorrectCredentialsException.class)
	//	public void testLoginFailWithErrorPassowrd() {
	//		login("classpath:chapter6/shiro.ini", u1.getUsername(), password + "1");
	//	}
	//
	//	@Test(expectedExceptions = LockedAccountException.class)
	//	public void testLoginFailWithLocked() {
	//		login("classpath:chapter6/shiro.ini", u4.getUsername(), password + "1");
	//	}
	//
	//	@Test(expectedExceptions = ExcessiveAttemptsException.class)
	//	public void testLoginFailWithLimitRetryCount() {
	//		for (int i = 1; i <= 5; i++) {
	//			try {
	//				login("classpath:chapter6/shiro.ini", u3.getUsername(), password + "1");
	//			} catch (Exception e) {/*ignore*/
	//			}
	//		}
	//		login("classpath:chapter6/shiro.ini", u3.getUsername(), password + "1");
	//
	//		//需要清空缓存，否则后续的执行就会遇到问题(或者使用一个全新账户测试)
	//	}
	//
	//	@Test
	//	public void testHasRole() {
	//		login("classpath:chapter6/shiro.ini", u1.getUsername(), password);
	//		Assert.assertTrue(subject().hasRole("admin"));
	//	}
	//
	//	@Test
	//	public void testNoRole() {
	//		login("classpath:chapter6/shiro.ini", u2.getUsername(), password);
	//		Assert.assertFalse(subject().hasRole("admin"));
	//	}
	//
	//	@Test
	//	public void testHasPermission() {
	//		login("classpath:chapter6/shiro.ini", u1.getUsername(), password);
	//		Assert.assertTrue(subject().isPermittedAll("user:create", "menu:create"));
	//	}
	//
	//	@Test
	//	public void testNoPermission() {
	//		login("classpath:chapter6/shiro.ini", u2.getUsername(), password);
	//		Assert.assertFalse(subject().isPermitted("user:create"));
	//	}

}
