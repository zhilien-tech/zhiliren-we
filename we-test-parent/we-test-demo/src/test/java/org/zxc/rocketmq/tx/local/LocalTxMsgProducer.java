/**
 * TransactionProducer.java
 * org.zxc.transaction
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq.tx.local;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.rocketmq.tx.common.MqConstants;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.TransactionCheckListener;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;

/**
 * 事务消息生产者
 * <p>
 * 事务消息的关键在于要保证本地事务与发送事务消息的原子性，
 * 也就是说，本地事务执行成功，则消息一定要发送成功，如果本地事务执行失败，则不发送事务消息
 * @author   朱晓川
 * @Date	 2017年10月19日 	 
 */
@IocBean
public class LocalTxMsgProducer {

	@Inject
	private LocalTransactionExecuterImpl localTransactionExecuter;

	private static final Logger logger = LoggerFactory.getLogger(LocalTxMsgProducer.class);

	public void sendTxMessage() throws MQClientException, InterruptedException {

		/****************************************1，设置MQ事务回查检查器********************************************/
		// 未决事务，MQ服务器回查客户端
		// 当RocketMQ发现`Prepared消息`时，会根据这个Listener实现的策略来判断事务是否执行成功，用以决定是否发送消息
		TransactionCheckListener transactionCheckListener = new LocalTxCheckListenerImpl();
		TransactionMQProducer producer = new TransactionMQProducer("transaction_message_producer_test_group");
		producer.setNamesrvAddr("192.168.1.27:9876");

		// 事务回查最小并发数
		producer.setCheckThreadPoolMinSize(2);
		// 事务回查最大并发数
		producer.setCheckThreadPoolMaxSize(2);
		// 队列数
		producer.setCheckRequestHoldMax(2000);
		producer.setTransactionCheckListener(transactionCheckListener);

		/**************************************2，启动producer发送事务消息******************************************/
		producer.start();

		/**
		 * 设置MQ事务处理器，根据事务消息的发送状态决定提交/回滚事务
		 */
		try {
			String txId = UUID.randomUUID().toString();

			String topic = MqConstants.TX_TOPIC;
			String tag = MqConstants.TX_TAG;
			String key = txId;

			String fromAccount = "zhangsan";
			double amount = 1000;
			byte[] data = (txId + ":" + fromAccount + ":" + amount).getBytes(RemotingHelper.DEFAULT_CHARSET);
			Message msg = new Message(topic, tag, key, data);
			SendResult sendResult = producer.sendMessageInTransaction(msg, localTransactionExecuter, null);
			logger.info(String.format("%s%n", sendResult));

			Thread.sleep(10);
		} catch (MQClientException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			producer.shutdown();
		}
	}

}
