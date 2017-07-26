package com.pengcheng.feedback.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("jjuser")
public class JjuserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("姓名")
	private String name;
	
	@Column
    @Comment("年龄")
	private Integer age;
	

}