/**
 * TxCheckListener.java
 * org.zxc.transaction
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq.tx;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.TransactionCheckListener;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * MQ事务状态监听器
 * <p>
 * 用于检查本地事务状态，对于未决事务，MQ服务器回查客户端；
 * 当RocketMQ发现`Prepared消息`时，会根据这个Listener实现的策略来判断事务是否执行成功，用以决定是否发送消息。
 * @author   朱晓川
 * @Date	 2017年10月19日 	 
 */
public class TransactionCheckListenerImpl implements TransactionCheckListener {

	private static final Logger logger = LoggerFactory.getLogger(TransactionProducer.class);

	private AtomicInteger transactionIndex = new AtomicInteger(0);

	@Override
	public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
		logger.info("server checking TrMsg " + msg.toString());

		int value = transactionIndex.getAndIncrement();
		if ((value % 6) == 0) {
			throw new RuntimeException("Could not find db");
		} else if ((value % 5) == 0) {
			return LocalTransactionState.ROLLBACK_MESSAGE;
		} else if ((value % 4) == 0) {
			return LocalTransactionState.COMMIT_MESSAGE;
		}

		return LocalTransactionState.UNKNOW;

	}

}
