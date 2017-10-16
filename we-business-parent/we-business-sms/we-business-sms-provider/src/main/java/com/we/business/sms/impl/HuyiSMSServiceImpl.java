package com.we.business.sms.impl;

import static com.we.business.sms.SMSConstants.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.util.DateUtil;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

import com.uxuexi.core.common.exception.impl.BusinessException;
import com.uxuexi.core.common.util.BeanUtil;
import com.uxuexi.core.common.util.ConvertUtil;
import com.uxuexi.core.common.util.ExceptionUtil;
import com.uxuexi.core.common.util.RandomUtil;
import com.uxuexi.core.common.util.StringUtil;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.redis.IRedisDao;
import com.uxuexi.core.redis.support.RedisKeyPrefix;
import com.uxuexi.core.redis.util.RedisUniUtil;
import com.we.business.sms.SMSService;
import com.we.business.sms.bean.SmsCallResult;
import com.we.business.sms.enums.SmsType;
import com.we.business.sms.util.SmsUtil;

/**
 * 使用互亿无线提供的发送短信服务接口，结合redis数据库，提供此服务类
 * <p>
 * 当需要新增加短信类型时，首先登录互亿无线添加并报送短信模版，然后在此项目添加{@link SmsType}枚举类，最后把短信模版添加到/src/main/java/template.properties配置文件
 */
@Component(value = "huyiSMSService")
public class HuyiSMSServiceImpl implements SMSService {

	/**
	 * 账号key  
	 */
	public static final String ACCOUNT = "account";
	/**
	 * 密码key
	 */
	public static final String PASSWORD = "password";

	/**
	 * 互亿无线账号名
	 */
	private static final String AC = "";
	/**
	 * 互亿无线密码
	 */
	private static final String PW = "";

	/**接口请求地址*/
	private static String URL = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";

	/**短信提交成功代码*/
	private static final String SUCCESS = "2";

	private static final Logger logger = LoggerFactory.getLogger(HuyiSMSServiceImpl.class);

	@Autowired
	private IRedisDao redisDao;

	/**
	 * 互亿无线提供的发送短信服务接口
	 * @author   lq
	 * @param tel
	 * @param content
	 * @throws Exception 
	*/
	@Override
	public Map<String, Object> send(final String tel, final String content) throws Exception {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(URL);
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");

		//密码可以使用明文密码或使用32位MD5加密
		NameValuePair[] data = { new NameValuePair(ACCOUNT, AC), new NameValuePair(PASSWORD, PW),
				new NameValuePair("mobile", tel), new NameValuePair("content", content), };
		method.setRequestBody(data);
		client.executeMethod(method);
		String xml = method.getResponseBodyAsString();
		Map<String, Object> map = toMap(xml);
		return map;
	}

	/**
	 * 对某一个手机发送某一种类型的验证码
	 * <p>
	 * 默认参数如下：
	 * <li>
	 * 验证码长度5位数，有效期5分钟，每个用户每个业务每日最多获取5条
	 * <li>
	 * 重新获取验证码的时间限制为1分钟
	 * @param SmsType SMS类型
	 * @param tel 手机号
	 * @return SMSCODE验证码 
	 * @throws Exception 
	 * @throws BusinessException 60秒后才能再次发送激活码，每个用户每日发送激活码次数超过限制5
	 * @see #sendSmsCode(SmsType, String, int, int, int, int)
	*/
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

	/**
	 * 对某一个手机发送某一种类型的验证码
	 *
	 * @param smsType   验证码类型
	 * @param tel       电话号码
	 * @param maxCount
	 * @param length    数字验证码位数
	 * @param vaidTime  有效期(秒)
	 * @param sendGap  发送间隔(秒) 
	 * @return SmsCode 短信验证码
	 * @throws Exception 
	*/
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
		SmsCallResult result = BeanUtil.map2Object(map, SmsCallResult.class);
		if (SUCCESS.equals(result.getCode())) {
			logger.info("短信发送成功...");
		} else {
			throw new BusinessException(StringUtil.format("{0}", result.getMsg()));
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

	/**
	 * 根据类型查询曾经给某手机发送的验证码
	 * @param smsType  验证码类型
	 * @param tel      手机号
	 * @return SmsCode 短信验证码
	*/
	@Override
	public String getCaptcha(final SmsType smsType, final String tel) {
		ExceptionUtil.checkEmptyBEx(smsType, "验证码类型不允许为空");
		ExceptionUtil.checkEmptyBEx(tel, "手机号不允许为空");
		return redisDao.get(RedisUniUtil.key(RedisKeyPrefix.UNI, SMSCODE, smsType.key(), tel));
	}

	/**
	 * 使某一个手机收到的某一种类型的验证码失效
	 *
	 * @param smsType
	 * @param tel
	 * @return long 失效的数量
	*/
	@Override
	public long disableCaptcha(final SmsType smsType, final String tel) {
		ExceptionUtil.checkEmptyBEx(smsType, "验证码类型不允许为空");
		ExceptionUtil.checkEmptyBEx(tel, "手机号不允许为空");
		return redisDao.del(RedisUniUtil.key(RedisKeyPrefix.UNI, SMSCODE, smsType.key(), tel));
	}

	public void setShardRedis(IRedisDao shardRedis) {
		this.redisDao = shardRedis;
	}

	/**将互忆无线返回的值转为map*/
	private Map<String, Object> toMap(String xml) throws JDOMException, IOException {
		StringReader reader = new StringReader(xml);
		InputSource source = new InputSource(reader);
		SAXBuilder builder = new SAXBuilder();
		Document xmlDoc = builder.build(source);
		Element root = xmlDoc.getRootElement();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Element> children = root.getChildren();
		if (!Util.isEmpty(children)) {
			for (Element child : children) {
				map.put(child.getName(), child.getText());
			}
		}
		return map;
	}

}
