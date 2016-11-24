package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("s_role")
public class SRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Long id;
	
	@Column
    @Comment("角色名称")
	private String name;
	
	@Column
    @Comment("备注")
	private String remark;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	

}