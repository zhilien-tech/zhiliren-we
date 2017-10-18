package com.xiaoka.template.test;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.uxuexi.core.redis.IRedisDao;
import com.we.business.sms.SMSService;
import com.we.business.sms.enums.SmsType;
import com.we.business.sms.impl.HuaxinSMSServiceImpl;

/**
 * 外部系统直接实例化短信服务进行使用，注意classpath中需要sms_config.properties
 * <p>
 * @author   朱晓川
 * @Date	 2017年10月18日
 */
public class SmsTestWithNew {

	private Ioc ioc;

	@BeforeClass
	public void init() {
		ioc = new NutIoc(new JsonLoader("redis/"));
	}

	@Test
	public void testSendSms() throws Exception {
		IRedisDao redisDao = ioc.get(IRedisDao.class, "redisDao");
		SMSService smsService = new HuaxinSMSServiceImpl(redisDao);
		smsService.sendCaptcha(SmsType.REGISTER, "18911451383");
	}

}
