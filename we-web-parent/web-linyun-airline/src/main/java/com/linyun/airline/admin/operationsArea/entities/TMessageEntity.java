package com.linyun.airline.admin.operationsArea.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_message")
public class TMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("标题")
	private String msgTitle;

	@Column
	@Comment("内容")
	private String msgContent;

	@Column
	@Comment("消息类型")
	private Long msgType;

	@Column
	@Comment("生成日期")
	private Date generateTime;

	@Column
	@Comment("优先级")
	private Long priorityLevel;

}