package com.xiaoka.template.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.we.business.sms.SMSService;
import com.we.business.sms.util.CustomizedPropertyConfigurer;

/**
 * 使用spring加载applicationContext的方式进行测试，也意味着如果要以dubbo服务的方式启动，
 * 则需要配置本项目的sms_config.properties以及redis.xml两个文件
 * <p>
 * 注意:
 * 1，AbstractTransactionalJUnit4SpringContextTests默认会回滚,
 * 2，如果要测试事务是否生效，就不要使用AbstractTransactionalJUnit4SpringContextTests完成
 * @author 朱晓川
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" })
public class SMSTest {

	@Resource(name = "huaxinSMSService")
	private SMSService smsService;

	@Before
	public void before() {
	}

	@Test
	public void testProperties() {
		String sms_loginProp = CustomizedPropertyConfigurer.getContextProperty("sms_login");
		System.out.println("sms_login:" + CustomizedPropertyConfigurer.getContextProperty("sms_login"));
		Assert.assertEquals(sms_loginProp, "123456");
	}

	@Test
	public void testSendCaptcha() {
		try {
			//			smsService.sendCaptcha(SmsType.REGISTER, "18911451383");
			String content = "亲爱的小魔女，您申请的签证需要补充资料，请点击链接登录填写签证资料http://www.f-visa.com/。【优悦签】";
			smsService.send("18911451383", content);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
