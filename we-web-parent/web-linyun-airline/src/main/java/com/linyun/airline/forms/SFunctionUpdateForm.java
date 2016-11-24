package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SFunctionUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**父功能id*/
	private Long parentId;
		
	/**功能名称*/
	private String name;
		
	/**访问地址*/
	private String url;
		
	/**功能等级*/
	private Integer level;
		
	/**备注*/
	private String remark;
		
	/**创建时间*/
	private Date createTime;
		
	/**修改时间*/
	private Date updateTime;
		
	/**序号*/
	private Integer sort;
		
}