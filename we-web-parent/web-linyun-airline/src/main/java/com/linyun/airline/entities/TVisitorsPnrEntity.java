package com.linyun.airline.entities;

import org.nutz.dao.entity.annotation.*;
import lombok.Data;

import java.io.Serializable;


@Data
@Table("t_visitors_pnr")
public class TVisitorsPnrEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	private Integer id;
	
	@Column
    @Comment("PNR信息id")
	private Integer pNRid;
	
	@Column
    @Comment("游客名单id")
	private Integer visitorslistid;
	

}