package org.zxc.rocketmq.tx.remote;

import java.io.Serializable;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 事务状态表
 * 
 * @author   朱晓川
 * @Date	 2017年10月30日 	 
 */
@Data
@Table("tx_status")
public class TxStatusEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Column
	@Id(auto = true)
	@Comment("主键")
	private Long id;

	@Column
	@Comment("事务id")
	private String txId;

	@Column
	@Comment("事务状态")
	private int status;

}
