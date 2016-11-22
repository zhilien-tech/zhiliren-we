package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("s_user")
public class SUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Long id;
	
	@Column
    @Comment("登录名")
	private String username;
	
	@Column
    @Comment("登录密码")
	private String password;
	
	@Column
    @Comment("用户状态:0-未激活，1-激活，2-冻结")
	private Integer status;
	
	@Column
    @Comment("用户类型:0-前台用户，1-后台用户")
	private Integer userType;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	

}