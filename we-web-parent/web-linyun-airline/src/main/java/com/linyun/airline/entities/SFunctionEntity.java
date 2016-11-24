package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("s_function")
public class SFunctionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Long id;
	
	@Column
    @Comment("父功能id")
	private Long parentId;
	
	@Column
    @Comment("功能名称")
	private String name;
	
	@Column
    @Comment("访问地址")
	private String url;
	
	@Column
    @Comment("功能等级")
	private Integer level;
	
	@Column
    @Comment("备注")
	private String remark;
	
	@Column
    @Comment("创建时间")
	private Date createTime;
	
	@Column
    @Comment("修改时间")
	private Date updateTime;
	
	@Column
    @Comment("序号")
	private Integer sort;
	

}