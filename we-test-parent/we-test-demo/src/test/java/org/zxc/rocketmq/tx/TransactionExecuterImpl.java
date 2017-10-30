/**
 * TxExecuter.java
 * org.zxc.transaction
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq.tx;

import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;

/**
 * MQ事务处理器,根据事务消息的发送状态决定是否提交/回滚本地事务
 * @author   朱晓川
 * @Date	 2017年10月19日 	 
 */
public class TransactionExecuterImpl implements LocalTransactionExecuter {

	private AtomicInteger transactionIndex = new AtomicInteger(1);

	@Override
	public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
		//执行一次自增一次
		int value = transactionIndex.getAndIncrement();

		if (value == 0) {
			throw new RuntimeException("Could not find db");
		} else if ((value % 5) == 0) {
			//如果transactionIndex的值是5的倍数则表示回滚事务
			return LocalTransactionState.ROLLBACK_MESSAGE;
		} else if ((value % 4) == 0) {
			//4的倍数则表示提交事务
			return LocalTransactionState.COMMIT_MESSAGE;
		}
		return LocalTransactionState.UNKNOW;
	}

}
