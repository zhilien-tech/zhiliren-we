/**
 * LocalWealthService.java
 * org.zxc.rocketmq.tx.service
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq.tx.local.service;

import org.nutz.dao.Sqls;
import org.nutz.dao.impl.NutDao;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 * 本地业务服务
 * 
 * @author   朱晓川
 * @Date	 2017年10月30日 	 
 */
@IocBean
public class LocalWealthService {

	@Inject
	private NutDao nutDao;

	//给李四转账
	@Aop("txDb")
	public void transferLisi(String fromAccount, double amount) throws Exception {
		Sql addSql = Sqls.create("UPDATE t_wealth SET amount=amount+@amount WHERE account = @account");
		Sql reduceSql = Sqls.create("UPDATE t_wealth SET amount=amount-@amount WHERE account = @account");

		addSql.params().set("amount", amount);
		addSql.params().set("account", "lisi");

		reduceSql.params().set("amount", amount);
		reduceSql.params().set("account", fromAccount);
		nutDao.execute(reduceSql);
		nutDao.execute(addSql);

		//		throw new Exception();
	}

}
