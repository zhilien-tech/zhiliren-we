package com.linyun.airline.admin.dictionary.departurecity.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_departure_city")
public class TDepartureCityEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Comment("主键")
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("字典类别编码")
	private Integer typeCode;

	@Column
	@Comment("三字代码")
	private String dictCode;

	@Column
	@Comment("国家")
	private String countryName;

	@Column
	@Comment("州")
	private String stateName;

	@Column
	@Comment("英文名称")
	private String englishName;

	@Column
	@Comment("中文名称")
	private String chineseName;

	@Column
	@Comment("拼音")
	private String pinYin;

	@Column
	@Comment("描述")
	private String description;

	@Column
	@Comment("状态")
	private Integer status;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("修改时间")
	private Date updateTime;

}