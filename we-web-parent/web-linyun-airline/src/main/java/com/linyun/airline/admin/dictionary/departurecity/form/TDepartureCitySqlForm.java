package com.linyun.airline.admin.dictionary.departurecity.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.admin.dictionary.departurecity.entity.TDepartureCityEntity;
import com.uxuexi.core.db.util.EntityUtil;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TDepartureCitySqlForm extends DataTablesParamForm {
	/**主键*/
	private Integer id;

	/**字典类别编码*/
	private String typeCode;

	/**三字代码*/
	private String dictCode;

	/**国家*/
	private String countryName;

	/**州*/
	private String stateName;

	/**英文名称*/
	private String englishName;

	/**中文名称*/
	private String chineseName;

	/**拼音*/
	private String pinYin;

	/**描述*/
	private String description;

	/**状态*/
	private Integer status;

	/**创建时间*/
	private Date createTime;

	/**修改时间*/
	private Date updateTime;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		String sqlString = EntityUtil.entityCndSql(TDepartureCityEntity.class);
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		//TODO 添加自定义查询条件（可选）

		return cnd;
	}
}