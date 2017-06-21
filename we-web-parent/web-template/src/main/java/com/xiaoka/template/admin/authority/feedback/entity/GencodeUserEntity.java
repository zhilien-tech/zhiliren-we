package com.xiaoka.template.admin.authority.feedback.entity;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("gencode_user")
public class GencodeUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("")
	private String name;
	
	@Column
    @Comment("")
	private Integer age;
	
	@Column
    @Comment("")
	private String address;
	

}