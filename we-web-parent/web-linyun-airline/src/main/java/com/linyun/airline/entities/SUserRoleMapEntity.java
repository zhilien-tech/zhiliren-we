package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("s_user_role_map")
public class SUserRoleMapEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Long id;
	
	@Column
    @Comment("用户id")
	private Long userId;
	
	@Column
    @Comment("角色id")
	private Long roleId;
	

}