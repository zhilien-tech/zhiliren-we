/**
 * TxCheckListener.java
 * org.zxc.transaction
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq.tx.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.TransactionCheckListener;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.uxuexi.core.common.util.Util;

/**
 * MQ事务状态监听器
 * <p>
 * 用于检查本地事务状态，对于未决事务，MQ服务器回查客户端；
 * 当RocketMQ发现`Prepared消息`时，会根据这个Listener实现的策略来判断事务是否执行成功，用以决定是否发送消息。
 * @author   朱晓川
 * @Date	 2017年10月19日 	 
 */
public class LocalTxCheckListenerImpl implements TransactionCheckListener {

	private static final Logger logger = LoggerFactory.getLogger(LocalTxMsgProducer.class);

	@Override
	public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
		String msgStr = new String(msg.getBody());
		logger.info("-mq server checking TrMsg: " + new String(msg.getBody()));
		String[] data = msgStr.split(":");
		String txId = data[0];

		Boolean txStat = TxStatusHolder.getTxStat(txId);
		logger.info("-txId: " + txId + "-->status:" + txStat);
		if (Util.isEmpty(txStat) || !txStat) {
			return LocalTransactionState.ROLLBACK_MESSAGE;
		} else {
			return LocalTransactionState.COMMIT_MESSAGE;
		}
	}

}
