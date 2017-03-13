/**
 * TAirlinePolicy.java
 * com.linyun.airline.entities
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   孙斌
 * @Date	 2017年3月11日 	 
 */
@Data
@Table("t_airlinepolicy")
public class TAirlinePolicyEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;

	@Column
	@Comment("文件名字")
	private String fileName;

	@Column
	@Comment("文件路径")
	private String url;

	@Column
	@Comment("文件大小")
	private String fileSize;

	@Column
	@Comment("创建日期")
	private Date createTime;

	@Column
	@Comment("更新日期")
	private Date updateTime;

	@Column
	@Comment("状态")
	private int status;

}
