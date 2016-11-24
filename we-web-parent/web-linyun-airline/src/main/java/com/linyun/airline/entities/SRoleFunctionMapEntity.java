package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("s_role_function_map")
public class SRoleFunctionMapEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Long id;
	
	@Column
    @Comment("角色id")
	private Long roleId;
	
	@Column
    @Comment("功能id")
	private Long functionId;
	

}