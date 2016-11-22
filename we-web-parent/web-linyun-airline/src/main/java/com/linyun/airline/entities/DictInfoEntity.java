package com.linyun.airline.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("dict_info")
public class DictInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Id(auto = true)
	@Comment("主键")
	private long id;

	@Column
	@Comment("字典类别编码")
	private String typeCode;

	@Column
	@Comment("字典代码")
	private String dictCode;

	@Column
	@Comment("字典信息")
	private String dictName;

	@Column
	@Comment("描述")
	private String description;

	@Column
	@Comment("字典信息状态,0-冻结，1-启用，2--删除")
	private int status;

	@Column
	@Comment("删除标识:(0,表示未删除；1,表示已删除)")
	private int dr;

}