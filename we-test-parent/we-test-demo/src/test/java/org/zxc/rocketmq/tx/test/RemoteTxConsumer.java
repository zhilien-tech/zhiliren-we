/**
 * PullConsumer.java
 * org.zxc.rocketmq
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq.tx.test;

import java.util.List;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.rocketmq.tx.common.MqConstants;
import org.zxc.rocketmq.tx.remote.RemoteWealthService;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * 远程事务消费者
 * @author   朱晓川
 * @Date	 2017年10月20日 	 
 */
public class RemoteTxConsumer {
	static final Logger logger = LoggerFactory.getLogger(RemoteTxConsumer.class);

	public static void main(String[] args) throws ClassNotFoundException {
		Ioc ioc = new NutIoc(new ComboIocLoader("*org.nutz.ioc.loader.json.JsonLoader", "rocketmq/tx/dao-remote.js",
				"*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "org.zxc.rocketmq.tx.remote"));

		RemoteWealthService remoteWealthService = ioc.get(RemoteWealthService.class, "remoteWealthService");

		String namesrvAddr = "192.168.1.27:9876";
		//push consumer
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("transaction_message_consumer_test_group1");
		consumer.setNamesrvAddr(namesrvAddr);

		try {
			//注意，tag使用*表示全部tag，但是不可以使用诸如TAG-*这样的东西
			consumer.subscribe(MqConstants.TX_TOPIC, MqConstants.TX_TAG);

			//设置第一次启动的时候队列的什么位置开始消费
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

			//注册消费的监听   
			consumer.registerMessageListener(new MessageListenerConcurrently() {
				//在此监听中消费信息，并返回消费的状态信息  
				@Override
				public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
						ConsumeConcurrentlyContext context) {
					try {
						//收取消息,执行业务
						for (Message msg : msgs) {
							String msgStr = new String(msg.getBody());
							logger.info("remote receive message: " + new String(msg.getBody()));
							String keys = msg.getKeys();
							logger.info("keys: " + keys);

							String[] data = msgStr.split(":");
							String txId = data[0];
							String fromAccount = data[1];
							double amount = Double.valueOf(data[2]);
							remoteWealthService.transferLisi(fromAccount, txId, amount);
						}
					} catch (Exception e) {
						e.printStackTrace();
						return ConsumeConcurrentlyStatus.RECONSUME_LATER;
					}
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
			});

			consumer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
