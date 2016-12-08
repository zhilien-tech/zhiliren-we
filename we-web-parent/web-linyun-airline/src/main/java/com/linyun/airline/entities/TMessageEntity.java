package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_message")
public class TMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("标题")
	private String msgTitle;
	
	@Column
    @Comment("内容")
	private String msgContent;
	
	@Column
    @Comment("消息类型")
	private Integer msgType;
	
	@Column
    @Comment("生成日期")
	private Date generateTime;
	
	@Column
    @Comment("优先级")
	private Integer priorityLevel;
	

}