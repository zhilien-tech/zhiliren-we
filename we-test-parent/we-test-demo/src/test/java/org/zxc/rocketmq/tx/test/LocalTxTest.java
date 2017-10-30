/**
 * LocalTxTest.java
 * org.zxc.rocketmq.tx.test
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq.tx.test;

import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.zxc.rocketmq.tx.local.LocalTxMsgProducer;
import org.zxc.rocketmq.tx.local.service.LocalWealthService;

import com.alibaba.rocketmq.client.exception.MQClientException;

/**
 * 本地事务测试（异常回滚，正常提交）
 * <p>
 *
 * @author   朱晓川
 * @Date	 2017年10月30日 	 
 */
public class LocalTxTest {

	private NutDao nutDao;
	private Ioc ioc;

	private LocalWealthService localWealthService;

	private LocalTxMsgProducer localTxMsgProducer;

	@BeforeClass
	public void init() {
		try {
			ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader", "rocketmq/tx/dao-local.js",
					"*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "org.zxc.rocketmq.tx.local"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		nutDao = ioc.get(NutDao.class, "nutDao");
		localWealthService = ioc.get(LocalWealthService.class, "localWealthService");
		localTxMsgProducer = ioc.get(LocalTxMsgProducer.class, "localTxMsgProducer");
	}

	@Test
	public void testNotEmpty() {
		Assert.assertNotNull(nutDao);
		Assert.assertNotNull(localWealthService);
		Assert.assertNotNull(localTxMsgProducer);
	}

	@Test
	public void sendTxMsg() throws MQClientException, InterruptedException {
		localTxMsgProducer.sendTxMessage();
	}

}
