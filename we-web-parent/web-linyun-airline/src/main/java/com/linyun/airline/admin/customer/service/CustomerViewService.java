package com.linyun.airline.admin.customer.service;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.linyun.airline.entities.TCustomerInfoEntity;
import com.uxuexi.core.web.base.service.BaseService;

@IocBean
public class CustomerViewService extends BaseService<TCustomerInfoEntity> {
	private static final Log log = Logs.get();

	/*public List<TCustomerLineEntity> findDictInfoByName(String name) throws Exception {
		String sqlString = EntityUtil.entityCndSql(TCustomerLineEntity.class);
		Sql sql = Sqls.create(sqlString);
		Cnd cnd = Cnd.NEW();
		cnd.and("lineName", "like", name + "%");
		
		sql.setCondition(cnd);
		sql.setCallback(Sqls.callback.records());
		nutDao.execute(sql);
		@SuppressWarnings("unchecked")
		List<TCustomerLineEntity> list = (List<TCustomerLineEntity>) sql.getResult();
		return list;
	}*/

}