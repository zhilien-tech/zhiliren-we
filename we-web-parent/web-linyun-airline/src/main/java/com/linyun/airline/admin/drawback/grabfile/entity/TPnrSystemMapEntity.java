package com.linyun.airline.admin.drawback.grabfile.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_pnr_system_map")
public class TPnrSystemMapEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	@Column
	@Comment("主键")
	private long id;

	@Column
	@Comment("订单id")
	private long orderId;

	@Column
	@Comment("pnrid")
	private long pnrId;

	@Column
	@Comment("财务信息id")
	private long financeId;
	@Column
	@Comment("邮寄抓取文件的id")
	private long grabFileId;
	@Column
	@Comment("收付款记录id")
	private long payReceiveRecordId;

	@Column
	@Comment("文件名称")
	private String fileName;

	@Column
	@Comment("航空公司编码")
	private String airCode;

	@Column
	@Comment("备注")
	private String remark;

	@Column
	@Comment("备用1")
	private String def3;
	@Column
	@Comment("备用2")
	private String def4;
	@Column
	@Comment("备用3")
	private String def5;

	@Column
	@Comment("关联状态")
	private long relationStatus;

	@Column
	@Comment("创建时间")
	private Date createTime;

	@Column
	@Comment("修改时间")
	private Date updateTime;

}