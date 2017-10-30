/**
 * RemoteWealthService.java
 * org.zxc.rocketmq.tx.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq.tx.remote;

import org.nutz.dao.Cnd;
import org.nutz.dao.Sqls;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.uxuexi.core.common.util.Util;

/**
 * 远程业务服务
 * 
 * @author   朱晓川
 * @Date	 2017年10月30日 	 
 */
@IocBean
public class RemoteWealthService {

	@Inject
	private NutDao nutDao;

	//给李四转账
	@Aop("txDb")
	public void transferLisi(String fromAccount, String txId, double amount) {
		TxStatusEntity txStat = nutDao.fetch(TxStatusEntity.class, Cnd.where("txId", "=", txId));
		//如果该事务还从来没有执行过
		if (Util.isEmpty(txStat)) {
			txStat = new TxStatusEntity();
			txStat.setTxId(txId);
			txStat.setStatus(0);
			nutDao.insert(txStat);
		}

		//确保事务只执行一次
		if (1 != txStat.getStatus()) {
			Sql addSql = Sqls.create("UPDATE t_wealth SET amount=amount+@amount WHERE account = @account");
			Sql reduceSql = Sqls.create("UPDATE t_wealth SET amount=amount-@amount WHERE account = @account");

			addSql.params().set("amount", amount);
			addSql.params().set("account", "lisi");

			reduceSql.params().set("amount", amount);
			reduceSql.params().set("account", fromAccount);
			nutDao.execute(reduceSql);
			nutDao.execute(addSql);

			txStat.setStatus(1);
			nutDao.update(txStat);
		}
	}

}
