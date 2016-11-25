package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

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
	@Comment("字典信息状态,1-启用，2--删除")
	private long status;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("全拼")
	private String quanPin;

	@Column
	@Comment("简拼")
	private String jianpin;
}