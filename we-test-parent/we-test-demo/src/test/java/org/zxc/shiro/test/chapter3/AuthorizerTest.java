package org.zxc.shiro.test.chapter3;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthorizerTest extends BaseTest {

	@Test
	public void testIsPermitted() {
		login("classpath:chapter3/shiro-authorizer.ini", "zhang", "123");
		//1，WildcardPermission。

		//判断拥有权限：user:create
		Assert.assertTrue(subject().isPermitted("user1:update"));
		Assert.assertTrue(subject().isPermitted("user2:update"));

		//2，BitPermission，通过二进制位的方式表示权限。
		Assert.assertTrue(subject().isPermitted("+user1+2"));//新增权限
		Assert.assertTrue(subject().isPermitted("+user1+8"));//查看权限
		Assert.assertTrue(subject().isPermitted("+user2+10"));//新增及查看

		//断言通过+号的方式，也就是BitPermission，对user1没有删除权限
		Assert.assertFalse(subject().isPermitted("+user1+4"));//没有删除权限

		//3，角色权限。
		Assert.assertTrue(subject().isPermitted("menu:view"));//通过MyRolePermissionResolver解析得到的权限
	}

	@Test
	public void testIsPermitted2() {
		login("classpath:chapter3/shiro-jdbc-authorizer.ini", "zhang", "123");
		//判断拥有权限：user:create
		Assert.assertTrue(subject().isPermitted("user1:update"));
		Assert.assertTrue(subject().isPermitted("user2:update"));
		//通过二进制位的方式表示权限
		Assert.assertTrue(subject().isPermitted("+user1+2"));//新增权限
		Assert.assertTrue(subject().isPermitted("+user1+8"));//查看权限
		Assert.assertTrue(subject().isPermitted("+user2+10"));//新增及查看

		Assert.assertFalse(subject().isPermitted("+user1+4"));//没有删除权限

		Assert.assertTrue(subject().isPermitted("menu:view"));//通过MyRolePermissionResolver解析得到的权限
	}

}
