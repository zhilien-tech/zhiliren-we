package com.we.business.sms.impl;

import static com.we.business.sms.SMSConstants.*;

import java.util.Date;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.DateUtil;
import org.nutz.json.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uxuexi.core.common.exception.impl.BusinessException;
import com.uxuexi.core.common.util.BeanUtil;
import com.uxuexi.core.common.util.ConvertUtil;
import com.uxuexi.core.common.util.ExceptionUtil;
import com.uxuexi.core.common.util.RandomUtil;
import com.uxuexi.core.common.util.StringUtil;
import com.uxuexi.core.redis.IRedisDao;
import com.uxuexi.core.redis.support.RedisKeyPrefix;
import com.uxuexi.core.redis.util.RedisUniUtil;
import com.we.business.sms.SMSService;
import com.we.business.sms.bean.HuaxinSmsCallResult;
import com.we.business.sms.enums.SmsType;
import com.we.business.sms.util.MD5;
import com.we.business.sms.util.SmsUtil;

/**
 * 使用华信提供的发送短信服务接口，结合redis数据库，提供此服务类
 * <p>
 * 注意:短信发送接口只能选择一个，不要混合使用,而且华信的验证码短信内容最后必须带上签名，
 * 形如：【签名】
 */
@Component(value = "huaxinSMSService")
public class HuaxinSMSServiceImpl implements SMSService {

	/**
	 * 账号
	 */
	private static final String ACCOUNT = "AC00719";

	/**接口秘钥*/
	private static final String API_KEY = "AC0071955";

	/**接口请求地址*/
	private static String URL = "https://dx.ipyy.net/smsJson.aspx?action=send";

	/**短信提交成功代码*/
	private static final String SUCCESS = "Success";

	private static final Logger logger = LoggerFactory.getLogger(HuaxinSMSServiceImpl.class);

	private IRedisDao redisDao;

	@Autowired
	public HuaxinSMSServiceImpl(IRedisDao redisDao) {
		this.redisDao = redisDao;
	}

	@Override
	public Map<String, Object> send(final String tel, final String content) throws Exception {
		HttpClient client = new HttpClient();

		PostMethod post = new PostMethod(URL);
		post.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		NameValuePair userid = new NameValuePair("userid", "");
		NameValuePair account = new NameValuePair("account", ACCOUNT);
		NameValuePair password = new NameValuePair("password", MD5.GetMD5Code(API_KEY));
		NameValuePair mobile = new NameValuePair("mobile", tel);//手机号
		NameValuePair contentVp = new NameValuePair("content", content);
		NameValuePair sendTime = new NameValuePair("sendTime", "");//定时发送，留空
		NameValuePair extno = new NameValuePair("extno", ""); //扩展，留空
		post.setRequestBody(new NameValuePair[] { userid, account, password, mobile, contentVp, sendTime, extno });
		int status = client.executeMethod(post);
		logger.info("sms request http status=" + status);

		//返回结果
		String result = post.getResponseBodyAsString();
		logger.info("sms result=" + result);
		Map<String, Object> map = Json.fromJsonAsMap(Object.class, result);
		return map;
	}

	@Override
	public String sendCaptcha(final SmsType SmsType, final String tel) throws Exception {
		ExceptionUtil.checkEmptyBEx(SmsType, "验证码类型不允许为空");
		ExceptionUtil.checkEmptyBEx(tel, "手机号不允许为空");
		int maxCount = 5;
		int length = 4;
		int vaidTime = 5 * 60; //验证码有效期 
		int sendGap = 60; //发送间隔 
		return sendCaptcha(SmsType, tel, maxCount, length, vaidTime, sendGap);
	}

	@Override
	public String sendCaptcha(final SmsType smsType, final String tel, final int maxCount, final int length,
			final int vaidTime, final int sendGap) throws Exception {
		ExceptionUtil.checkEmptyBEx(smsType, "验证码类型不允许为空");
		ExceptionUtil.checkEmptyBEx(tel, "手机号不允许为空");
		ExceptionUtil.checkEmptyBEx(maxCount, "最大发送条数不允许为空");
		ExceptionUtil.checkEmptyBEx(length, "验证码长度不允许为空");
		ExceptionUtil.checkEmptyBEx(vaidTime, "验证码有效时长不允许为空");
		ExceptionUtil.checkEmptyBEx(sendGap, "验证码限制重新获取时长不允许为空");
		String SMSCODEkey = RedisUniUtil.key(RedisKeyPrefix.UNI, SMSCODE, smsType.key(), tel);
		String REGETSMSkey = RedisUniUtil.key(RedisKeyPrefix.UNI, REGETSMS, smsType.key(), tel);
		String date = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
		String SMSCountkey = RedisUniUtil.key(RedisKeyPrefix.EXP_COUNT, SMSCOUNT, smsType.key(), tel, date);

		if (redisDao.exists(REGETSMSkey)) {
			long ttl = redisDao.ttl(REGETSMSkey);
			throw new BusinessException(StringUtil.format("{0}秒后才能再次发送验证码", ttl));
		}

		int SMSCount = ConvertUtil.obj2int(redisDao.get(SMSCountkey));
		if (SMSCount > maxCount) {
			throw new BusinessException(StringUtil.format("每个用户每日发送验证码次数超过限制:{0}", maxCount));
		}
		String smscode = RandomUtil.randIntString(length);

		//发送短信 
		String content = SmsUtil.getSmsContent(smsType, smscode);
		Map<String, Object> map = send(tel, content);
		HuaxinSmsCallResult result = BeanUtil.map2Object(map, HuaxinSmsCallResult.class);
		if (SUCCESS.equals(result.getReturnstatus())) {
			logger.info("短信发送成功...");
		} else {
			throw new BusinessException(StringUtil.format("{0}", result.getMessage()));
		}

		redisDao.set(SMSCODEkey, smscode);
		redisDao.expire(SMSCODEkey, vaidTime);
		redisDao.set(REGETSMSkey, String.valueOf(true));
		redisDao.expire(REGETSMSkey, sendGap);
		boolean first = !redisDao.exists(SMSCountkey);
		if (first) {
			redisDao.set(SMSCountkey, String.valueOf(1));
			redisDao.expire(SMSCountkey, 24 * 3600);
		} else {
			redisDao.incr(SMSCountkey);
		}
		return smscode;
	}

	@Override
	public String getCaptcha(final SmsType smsType, final String tel) {
		ExceptionUtil.checkEmptyBEx(smsType, "验证码类型不允许为空");
		ExceptionUtil.checkEmptyBEx(tel, "手机号不允许为空");
		return redisDao.get(RedisUniUtil.key(RedisKeyPrefix.UNI, SMSCODE, smsType.key(), tel));
	}

	@Override
	public long disableCaptcha(final SmsType smsType, final String tel) {
		ExceptionUtil.checkEmptyBEx(smsType, "验证码类型不允许为空");
		ExceptionUtil.checkEmptyBEx(tel, "手机号不允许为空");
		return redisDao.del(RedisUniUtil.key(RedisKeyPrefix.UNI, SMSCODE, smsType.key(), tel));
	}
}
