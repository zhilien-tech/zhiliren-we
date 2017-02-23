package com.linyun.airline.admin.drawback.grabmail.entity;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_grab_mail")
public class TGrabMailEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("主题")
	private String theme;
	
	@Column
    @Comment("发件人")
	private String sender;
	
	@Column
    @Comment("收件人")
	private String addressee;
	
	@Column
    @Comment("发送时间")
	private Date sendTime;
	
	@Column
    @Comment("抓取时间")
	private Date grabTime;
	
	@Column
    @Comment("抓取状态(1-已抓;2-未抓;)")
	private Integer grabStatus;
	

}