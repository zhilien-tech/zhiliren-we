/**
 * AccessTokenCache.java
 * com.xiaoka.test.entity
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.common.sabre;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.linyun.airline.common.sabre.bean.SabreAccessToken;
import com.linyun.airline.common.util.Base64;
import com.linyun.airline.common.util.HttpClientUtil;
import com.uxuexi.core.common.util.Util;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   朱晓川
 * @Date	 2016年11月15日 	 
 */
public class SabreTokenFactory {

	protected static final Log log = Logs.getLog(SabreTokenFactory.class);

	private static final String TOKEN_KEY = "sabre_access_token";

	//token授权过期时间(秒)
	private static final int DEFAULT_EXPIREXIN = 604800;

	//LoadingCache在缓存项不存在时可以自动加载缓存 
	static LoadingCache<String, SabreAccessToken> cache

	//CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
	= CacheBuilder.newBuilder()

	//设置并发级别,并发级别是指可以同时写缓存的线程数 
			.concurrencyLevel(2)

			//设置写缓存后8秒钟过期
			.expireAfterWrite(DEFAULT_EXPIREXIN, TimeUnit.SECONDS)

			//设置缓存容器的初始容量为
			.initialCapacity(10)

			//设置缓存最大容量，超过之后就会按照LRU最近虽少使用算法来移除缓存项
			.maximumSize(10)

			//设置要统计缓存的命中率
			.recordStats()

			//设置缓存的移除通知
			.removalListener(new RemovalListener<String, SabreAccessToken>() {
				@Override
				public void onRemoval(RemovalNotification<String, SabreAccessToken> notification) {
					log.info(notification.getKey() + " was removed, cause is " + notification.getCause());
				}
			})

			//指定CacheLoader，当缓存不存在时通过CacheLoader的实现自动加载缓存
			.build(new CacheLoader<String, SabreAccessToken>() {
				@Override
				public SabreAccessToken load(String key) throws Exception {
					SabreAccessToken token = fetchToken();
					long now = System.currentTimeMillis();
					token.setLoadTimeMillis(now);
					return token;
				}
			});

	private SabreTokenFactory() {
	}

	public static SabreAccessToken getAccessToken() {
		SabreAccessToken token = null;
		try {
			token = cache.get(TOKEN_KEY);
			long now = System.currentTimeMillis();
			if (!Util.isEmpty(token)) {
				long loadTimeMillis = token.getLoadTimeMillis();
				int passed = (int) (now - loadTimeMillis) / 1000;

				log.debug("get token passed " + passed);
				if (passed >= token.getExpires_in()) {
					cache.invalidate(TOKEN_KEY);
					token = cache.get(TOKEN_KEY);
				}
			} else {
				//如果缓存中token为空的话，去sabre取,通常应该不会发生
				token = fetchToken();
				token.setLoadTimeMillis(now);
			}
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return token;
	}

	/**
	 * 获取access_token
	 * @throws UnsupportedEncodingException 
	 * @throws Exception 
	 */
	protected static SabreAccessToken fetchToken() throws UnsupportedEncodingException {
		log.debug("load access_token from sabre");
		String encodedCid = Base64.encode((SabreConfig.Client_ID).getBytes(SabreConfig.CHARSET));
		String encodedSecret = Base64.encode(SabreConfig.Secret.getBytes(SabreConfig.CHARSET));
		log.debug("encodedCid:" + encodedCid);
		log.debug("encodedSecret:" + encodedSecret);

		String authorization = Base64.encode((encodedCid + ":" + encodedSecret).getBytes(SabreConfig.CHARSET));
		String authUrl = SabreConfig.test_environment + SabreConfig.AUTH_URI;

		String respTxt = null;
		HttpPost httpPost = new HttpPost(authUrl);
		httpPost.addHeader("Authorization", "Basic " + authorization);//必须是第一个参数,而且Basic空格是必须的:(
		httpPost.addHeader("Content-Type", SabreConfig.contentType);

		//得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		StringEntity postEntity = new StringEntity("grant_type=client_credentials", SabreConfig.CHARSET);
		httpPost.setEntity(postEntity);

		log.debug("executing request " + httpPost.getRequestLine());
		respTxt = HttpClientUtil.httpsPost(httpPost);
		SabreAccessToken accessToken = Json.fromJson(SabreAccessToken.class, respTxt);
		return accessToken;
	}

	public static void main(String[] args) throws InterruptedException {

		for (int i = 0; i < 20; i++) {
			//从缓存中得到数据，由于我们没有设置过缓存，所以需要通过CacheLoader加载缓存数据
			SabreAccessToken accessToken = getAccessToken();
			log.info(accessToken.getAccess_token());
			//休眠1秒
			TimeUnit.SECONDS.sleep(1);
		}

		//最后打印缓存的命中率等 情况
		System.out.println("cache stats:");
		System.out.println(cache.stats().toString());
	}
}