package org.zxc.shiro.test.chapter3;

import java.util.Arrays;

import org.apache.shiro.authz.UnauthorizedException;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 测试基于角色的访问控制（即隐式角色）。
 * 缺点就是如果很多地方进行了角色判断，但是有一天不需要了那么就需要修改相应代码把所有相关的地方进行删除；
 * 这就是粗粒度造成的问题。
 * <p>
 * @author   朱晓川
 * @Date	 2017年8月2日
 */
public class RoleTest extends BaseTest {

	@Test
	public void testHasRole() {
		login("classpath:chapter3/shiro-role.ini", "zhang", "123");
		//判断拥有某个角色
		Assert.assertTrue(subject().hasRole("role1"));

		//判断是否拥有给出的所有角色
		Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1", "role2")));

		//逐个判断是否拥有给定的角色，会产生多个结果
		boolean[] result = subject().hasRoles(Arrays.asList("role1", "role2", "role3"));
		Assert.assertEquals(true, result[0]);
		Assert.assertEquals(true, result[1]);
		Assert.assertEquals(false, result[2]);
	}

	@Test(expectedExceptions = UnauthorizedException.class)
	public void testCheckRole() {
		login("classpath:chapter3/shiro-role.ini", "zhang", "123");
		//断言拥有某个角色
		subject().checkRole("role1");

		//断言拥有给出的全部角色
		subject().checkRoles("role1", "role3");
	}

}
