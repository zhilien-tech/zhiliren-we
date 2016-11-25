package com.linyun.airline.entities;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Data
@Table("t_customer_invoice")
public class TCustomerInvoiceEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column
	@Id(auto = true)
	@Comment("主键")
	private long id;

	@Column
	@Comment("客户信息id")
	private long infoId;

	@Column
	@Comment("发票项id")
	private long invoiceId;

	@Column
	@Comment("发票项名称")
	private String invioceName;

}