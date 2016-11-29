/**
 * 
 * 
 * 
 * 
 * 授权:
 * 遵循OAuth 2.0开放授权
 * POST /v2/auth/token
 * account:brandorsabre
 * passwd:zxcv1234
 * 
 */
package com.xiaoka.test;

import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.linyun.airline.common.sabre.SabreTokenFactory;
import com.linyun.airline.common.sabre.bean.SabreAccessToken;
import com.uxuexi.core.common.util.Util;

/**
 * 1,构造客户端ID
 * 形如:V1:mq2kolgs7tunkpe0:DEVCENTER:EXT
 * 2,Base64编码(Client_ID),Base64编码你的密码password 
 *   用冒号拼接，得到形如encodedId:encodedPasswd的值，对此值再次进行Base64编码得到用于获取
 *   access_token的凭据
 * 3，获取access_token
 * 
 * 4,使用access_token调用查询航班的API
 * 
 * 国际航空3字母代码
 * SYX(三亚)
 * BKK(曼谷)
 * 例:GET https://api.sabre.com/v1/shop/flights/cheapest/fares/DFW
 * @author   朱晓川
 * @Date	 2016年10月26日 	 
 */
public class SabreAPITest {

	//打log用
	private static Log log = Logs.getLog(SabreAPITest.class);

	@Test
	public static void getAccessTokenTest() {
		int nullCnt = 0;
		for (int i = 0; i < 20; i++) {
			//从缓存中得到数据，由于我们没有设置过缓存，所以需要通过CacheLoader加载缓存数据
			SabreAccessToken accessToken = SabreTokenFactory.getAccessToken();
			String token = accessToken.getAccess_token();
			log.info(token);

			if (Util.isEmpty(token)) {
				nullCnt++;
			}
			Assert.assertEquals(nullCnt, 0);
		}
	}
}
