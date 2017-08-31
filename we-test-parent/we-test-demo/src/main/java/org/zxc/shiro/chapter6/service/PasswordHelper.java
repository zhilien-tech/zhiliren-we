package org.zxc.shiro.chapter6.service;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.zxc.shiro.chapter6.entity.User;

/**
 * 给用户的原始密码进行加密(MD5)，使用RandomNumberGenerator生成随机的salt
 */
public class PasswordHelper {

	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

	private String algorithmName = "md5";
	private final int hashIterations = 2;

	public void encryptPassword(User user) {

		user.setSalt(randomNumberGenerator.nextBytes().toHex());

		String newPassword = new SimpleHash(algorithmName, user.getPassword(), ByteSource.Util.bytes(user
				.getCredentialsSalt()), hashIterations).toHex();

		user.setPassword(newPassword);
	}
}
