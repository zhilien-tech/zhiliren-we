/**
 * TCompanyUserForm.java
 * com.linyun.airline.forms
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.forms;

import lombok.Data;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.db.dao.IDbDao;
import com.uxuexi.core.web.form.ISqlForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   刘旭利
 * @Date	 2016年11月21日 	 
 */
@Data
public class TCompanyUserSqlForm implements ISqlForm {

	private Long id;

	private String userName;

	@Override
	public Sql createPagerSql(IDbDao paramIDbDao, SqlManager paramSqlManager) {

		String sqlString = paramSqlManager.get("company_user_info_list");
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;

	}

	@Override
	public Sql createCountSql(IDbDao paramIDbDao, SqlManager paramSqlManager) {

		// TODO Auto-generated method stub
		return null;

	}

	private Cnd cnd() {
		Cnd cnd = Cnd.limit();
		//TODO 添加自定义查询条件（可选）
		if (!Util.isEmpty(id)) {
			cnd.and("tcj.comId", "=", id);
		}
		if (!Util.isEmpty(userName)) {
			cnd.and("tu.userName", "like", "%" + userName + "%").or("tu.telephone", "like", "%" + userName + "%");
		}
		return cnd;
	}

}
