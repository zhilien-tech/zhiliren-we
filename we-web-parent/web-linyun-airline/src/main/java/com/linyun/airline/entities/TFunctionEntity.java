package com.linyun.airline.entities;

import java.io.Serializable;

import lombok.Data;

import org.joda.time.DateTime;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_function")
public class TFunctionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Id(auto = true)
	@Comment("主键")
	private long id;

	@Column
	@Comment("上级功能id")
	private long parentId;

	@Column
	@Comment("功能名称")
	private String name;

	@Column
	@Comment("访问地止")
	private String url;

	@Column
	@Comment("功能等级，是指在功能树中所处的层级")
	private long level;

	@Column
	@Comment("创建时间")
	private DateTime createTime;

	@Column
	@Comment("更新时间")
	private DateTime updateTime;

	@Column
	@Comment("备注")
	private String remark;

	@Column
	@Comment("序号")
	private long sort;

	/**在树形节点中是否选中*/
	private String checked = "false";

}