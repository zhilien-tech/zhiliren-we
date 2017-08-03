package com.zlr.feedback.entities;

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
    @Comment("姓名")
	private String name;
	
	@Column
    @Comment("年龄")
	private Integer age;
	
	@Column
    @Comment("地址")
	private String address;
	

}