/**
 * DirinfoEntity.java
 * com.xiaoka.template.admin.dictionary.dirinfo.entity
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.dictionary.dirinfo.entity;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年11月3日 	 
 */
@Table("dict_info")
@Data
@Comment
public class DirinfoEntity {

	@Column
	@Id(auto = true)
	@Comment("主键")
	private int id;

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
	@Comment("状态")
	//0-冻结，1-启用，2--删除
	private int status;
}
