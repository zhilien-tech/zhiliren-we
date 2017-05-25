package com.linyun.airline.admin.drawback.grabfile.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.Cnd;
import org.nutz.dao.SqlManager;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;

import com.linyun.airline.common.enums.OrderTypeEnum;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.form.DataTablesParamForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TGrabFileSqlForm extends DataTablesParamForm {
	/**主键*/
	private long id;

	/**抓取邮件id*/
	private long mailId;

	/**上级id*/
	private long parentId;

	/**客户团号计数*/
	private Integer customnum;

	/**文件名称*/
	private String fileName;

	/**文件地址*/
	private String url;

	/**文件大小*/
	private String fileSize;

	/**文件单位*/
	private String unit;

	/**文件类型(1-文件夹;2-文件;)*/
	private int type;

	/**状态(1-已删除;2-已启用;)*/
	private int status;

	/**创建时间*/
	private Date createTime;

	/**修改时间*/
	private Date updateTime;

	/**层级*/
	private long level;

	/**完整路径*/
	private String fullPath;

	/**序号*/
	private long sort;

	/**散团类型*/
	private String groupType;

	/**发送邮件时间*/
	private String sendTime;

	/**航空公司代码*/
	private String fileNameCode;

	/**发送邮件时间*/
	private String fileNameTime;

	/**判断是团还是散*/
	private int flag;

	@Override
	public Sql sql(SqlManager sqlManager) {
		/**
		 * 默认使用了当前form关联entity的单表查询sql,如果是多表复杂sql，
		 * 请使用sqlManager获取自定义的sql，并设置查询条件
		 */
		//String sqlString = EntityUtil.entityCndSql(TGrabFileEntity.class);
		String sqlString = sqlManager.get("grab_mail_and_file_list");

		if (level < 3) {

			sqlString += " AND ((gr.url NOT like '%.png' and gr.url NOT like '%.jpg' and gr.url NOT like '%.gif' and gr.url NOT like '%.doc' and gr.url NOT like '%.xls') or gr.url is null) ORDER BY gr.updateTime DESC";
		} else {

			sqlString += " AND ((gr.url NOT like '%.png' and gr.url NOT like '%.jpg' and gr.url NOT like '%.gif' and gr.url NOT like '%.doc' and gr.url NOT like '%.xls') or gr.url is null)";
		}
		Sql sql = Sqls.create(sqlString);
		sql.setCondition(cnd());
		return sql;
	}

	//TODO 添加自定义查询条件（可选）
	private Cnd cnd() {
		Cnd cnd = Cnd.NEW();
		if (!Util.isEmpty(parentId)) {
			cnd.and("gr.parentId", "=", parentId);
		}
		if (!Util.isEmpty(fileNameCode)) {
			cnd.and("gr.fileName", "LIKE", "%" + fileNameCode + "%").and("gr.fileName", "LIKE",
					"%" + fileNameTime + "%");
		}
		//cnd.and("gr.groupType", "=", GroupTypeEnum.GRABMAIL_FIT.intKey());//散客
		int a = flag;
		if (a == 0) {
			cnd.and("groupType", "=", OrderTypeEnum.FIT.intKey());
		} else if (a == 1) {
			cnd.and("groupType", "=", OrderTypeEnum.TEAM.intKey());

		}
		return cnd;
	}
}