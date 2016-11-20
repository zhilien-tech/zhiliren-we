/**
 * DirtypeEntity.java
 * com.xiaoka.template.admin.dictionary.dirtype.entity
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirtype.entity;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * TODO(字典类型管理实体类)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@Table("dict_type")
@Data
@Comment
public class DirtypeEntity {

	@Column
	@Id(auto = true)
	@Comment("主键")
	private int id;

	@Column
	@Comment("字典类别编码")
	private String typeCode;

	@Column
	@Comment("字典类别名称")
	private String typeName;

	@Column
	@Comment("描述")
	private String description;

	@Column
	@Comment("状态")
	//0-冻结，1-启用，2--删除
	private int status;

}
