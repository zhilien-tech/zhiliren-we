/**
 * WealthEntity.java
 * org.zxc.rocketmq.tx.local
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.rocketmq.tx.entity;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 财富表实体
 * 
 * @author   朱晓川
 * @Date	 2017年10月30日 	 
 */
@Data
@Table("t_wealth")
public class WealthEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column
	@Id(auto = true)
	@Comment("主键")
	private Long id;

	@Column
	@Comment("账号")
	private String account;

	@Column
	@Comment("金额")
	private Double amount;

}
