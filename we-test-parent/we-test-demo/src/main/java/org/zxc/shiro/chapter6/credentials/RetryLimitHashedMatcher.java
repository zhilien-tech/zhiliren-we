package org.zxc.shiro.chapter6.credentials;

import java.util.concurrent.atomic.AtomicInteger;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * 认证器
 */
public class RetryLimitHashedMatcher extends HashedCredentialsMatcher {

	private Ehcache passwordRetryCache;

	/**
	 * 创建 RetryLimitHashedCredentialsMatcher对象.
	 * <p>
	 * 注意:坑啊，这是构造方法，如果这个方法报错会导致无法创建此类实例，但是报错的时候提示的却是:
	 * Unable to instantiate class ... Please ensure you've specified the fully qualified class name correctly.
	 *  其实是ehcache.xml路径不正确
	 */
	public RetryLimitHashedMatcher() {
		CacheManager cacheManager = CacheManager.newInstance(CacheManager.class.getClassLoader().getResource(
				"chapter6/ehcache.xml"));
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
	}

	/**
	 * 用户认证最大认证次数的实现:
	 * 使用ehcache保存认证失败的用户(以用户名作为标记)，当重试次数大于5的时候抛出ExcessiveAttemptsException异常
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		/*
		 * token是用户提交的，info是从realm查询到的
		 */
		String username = (String) token.getPrincipal();
		//retry count + 1
		Element element = passwordRetryCache.get(username);
		if (element == null) {
			element = new Element(username, new AtomicInteger(0));
			passwordRetryCache.put(element);
		}
		AtomicInteger retryCount = (AtomicInteger) element.getObjectValue();
		if (retryCount.incrementAndGet() > 5) {
			//if retry count > 5 throw
			throw new ExcessiveAttemptsException();
		}

		boolean matches = super.doCredentialsMatch(token, info);
		if (matches) {
			//清除认证失败的用户
			passwordRetryCache.remove(username);
		}
		return matches;
	}
}
