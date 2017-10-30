/**
 * TxExecuter.java
 * org.zxc.transaction
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq.tx.local;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zxc.rocketmq.tx.local.service.LocalWealthService;

import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;

/**
 * MQ本地事务处理器,也是本地业务执行的入口
 * 会根据事务消息的发送状态决定是否提交/回滚本地事务
 * @author   朱晓川
 * @Date	 2017年10月19日 	 
 */
@IocBean(name = "localTransactionExecuter")
public class LocalTransactionExecuterImpl implements LocalTransactionExecuter {

	private static final Logger logger = LoggerFactory.getLogger(LocalTransactionExecuterImpl.class);

	@Inject
	private LocalWealthService localWealthService;

	@Override
	public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
		String msgStr = new String(msg.getBody());
		logger.info("local message: " + new String(msg.getBody()));
		String[] data = msgStr.split(":");
		String txId = data[0];
		String account = data[1];
		double amount = Double.valueOf(data[2]);

		try {
			localWealthService.transferLisi(account, amount);
			TxStatusHolder.setTxStat(txId, true);
			return LocalTransactionState.COMMIT_MESSAGE;
		} catch (Exception e) {
			e.printStackTrace();
			TxStatusHolder.setTxStat(txId, false);
			return LocalTransactionState.ROLLBACK_MESSAGE;
		}
	}

}
