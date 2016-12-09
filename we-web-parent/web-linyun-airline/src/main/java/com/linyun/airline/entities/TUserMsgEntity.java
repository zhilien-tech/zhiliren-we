package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;
import java.util.Date;

import java.io.Serializable;


@Data
@Table("t_user_msg")
public class TUserMsgEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("消息Id")
	private Integer msgId;
	
	@Column
    @Comment("用户Id")
	private Integer userId;
	
	@Column
    @Comment("用户类型")
	private String userType;
	
	@Column
    @Comment("是否阅读")
	private Integer isRead;
	
	@Column
    @Comment("阅读时间")
	private Date readTime;
	
	@Column
    @Comment("来源Id")
	private Integer fromId;
	
	@Column
    @Comment("消息来源")
	private Integer msgSource;
	
	@Column
    @Comment("发送时间")
	private Date sendTime;
	

}