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
package com.xiaoka.test.entity;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.nutz.json.Json;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.linyun.airline.common.sabre.bean.SabreAccessToken;
import com.linyun.airline.common.util.Base64;
import com.linyun.airline.common.util.HttpClientUtil;

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
public class TestAPI {

	//打log用
	private static Log log = Logs.getLog(TestAPI.class);

	/**
	 * API环境分为测试环境和生产环境，测试的时候使用测试环境
	 */
	private static final String test_environment = "https://api.test.sabre.com";

	private static final String prod_environment = "https://api.sabre.com";

	private static final String CHARSET = "UTF-8";

	private static final String AUTH_URI = "/v2/auth/token";

	private static final String Client_ID = "V1:mq2kolgs7tunkpe0:DEVCENTER:EXT";

	private static final String Secret = "satULB47";

	private static final String contentType = "application/x-www-form-urlencoded";

	private static final String access_token = "T1RLAQJiHYczD7oSJWFHsDp6SzuZmqTR3RAOeJOIzdJ7HcQHu5gnxB/VAADA9sg2UYKES7aYS3fBvS4ysjPxXcCdTSozKggrj11aQ11UXa4AFw5UvDTTlyAD2VRr6eHVqX2LNjyYDrE3T390KRPPluLnT1noU6Fiao4edDsEClsQ2l7StvFym62+FLZjoAOtjbVMDxXtfR3tCQWDB4iJUocwDCQ9pLHJCl9AvRJg3iMUlfFVK+vh8x7T3jJ/EYmdRoCQ6TvV/SJqvxnB3D+dSjCEa921HzZoLKq5w2dyoEKROU4rgybvc0U0qVAP";

	public static void main(String[] args) throws UnsupportedEncodingException {
		//		SabreAccessToken token = getAccessToken();
		//		log.info("token:" + token.getAccess_token());
		flightTo();
	}

	/**
	 * 获取access_token
	 * @throws UnsupportedEncodingException 
	 * @throws Exception 
	 */
	public static SabreAccessToken getAccessToken() throws UnsupportedEncodingException {
		String encodedCid = Base64.encode((Client_ID).getBytes(CHARSET));
		String encodedSecret = Base64.encode(Secret.getBytes(CHARSET));
		log.debug("encodedCid:" + encodedCid);
		log.debug("encodedSecret:" + encodedSecret);

		String authorization = Base64.encode((encodedCid + ":" + encodedSecret).getBytes(CHARSET));
		String authUrl = test_environment + AUTH_URI;

		String respTxt = null;
		HttpPost httpPost = new HttpPost(authUrl);
		httpPost.addHeader("Authorization", "Basic " + authorization);//必须是第一个参数,而且Basic空格是必须的:(
		httpPost.addHeader("Content-Type", contentType);

		//得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		StringEntity postEntity = new StringEntity("grant_type=client_credentials", CHARSET);
		httpPost.setEntity(postEntity);

		log.debug("executing request " + httpPost.getRequestLine());
		respTxt = HttpClientUtil.httpsPost(httpPost);
		SabreAccessToken accessToken = Json.fromJson(SabreAccessToken.class, respTxt);
		return accessToken;
	}

	/**
	 * 1输入目的地直接查询(调试通过)
	 * GET /v1/shop/flights/cheapest/fares/{destination}
	 * 
	 * 2复杂查询
	 * A     /v1/shop/flights
	 * B     /v1/shop/flights/tags/{tagid}
	 * A接口指定参数enabletagging=true的时候会返回tagid
	 * 
	 * BJS   北京
	 * SDY   悉尼
	 */
	public static String flightTo() {
		//		String searchUrl = test_environment + "/v1/shop/flights?origin=PEK&destination=SYD&departuredate=2016-11-30";
		String searchUrl = test_environment + "/v1/shop/flights/cheapest/fares/BJS";

		String result = null;
		HttpGet httpget = new HttpGet(searchUrl);
		httpget.addHeader("Authorization", "Bearer " + access_token);

		log.info("executing request " + httpget.getRequestLine());
		Header[] heads = httpget.getAllHeaders();
		for (Header h : heads) {
			log.info("header name[" + h.getName() + "] value[" + h.getValue() + "]");
		}
		result = HttpClientUtil.httpsGet(httpget);
		return result;
	}

}
