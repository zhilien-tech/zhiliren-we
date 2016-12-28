/**
 * FlightInfoSqlForm.java
 * com.linyun.airline.admin.flightinfo.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.flightinfo.form;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.entities.TFlightInfoEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2016年12月28日 	 
 */
public class FlightInfoSqlForm extends DataTablesParamForm {

	public Cnd cnd() {
		Cnd cnd = Cnd.limit();
		return cnd;
	}

	@Override
	public Sql sql(SqlManager sqlManager) {
		String sqlString = EntityUtil.entityCndSql(TFlightInfoEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;

	}

}
