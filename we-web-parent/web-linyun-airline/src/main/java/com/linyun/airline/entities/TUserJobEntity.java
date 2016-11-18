package com.linyun.airline.entities;

import lombok.Data;

import org.joda.time.DateTime;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 用户职位关系实体
 * @author   崔建斌
 * @Date	 2016-11-17	 
 */
@Data
@Table("t_user_job")
public class TUserJobEntity {
	@Column
	@Id(auto = true)
	@Comment("主键")
	private int id;

	@Column
	@Comment("用户id")
	private long userid;

	@Column
	@Comment("公司职位id")
	private long companyJobId;

	@Column
	@Comment("状态")
	private int status;

	@Column
	@Comment("入职时间")
	private DateTime hireDate;

	@Column
	@Comment("离职时间")
	private DateTime leaveDate;

	@Column
	@Comment("备注")
	private String remark;

}