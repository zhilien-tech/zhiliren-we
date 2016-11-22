package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.joda.time.DateTime;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TFunctionUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**上级功能id*/
	private long parentId;

	/**功能名称*/
	private String name;

	/**访问地止*/
	private String url;

	/**功能等级，是指在功能树中所处的层级*/
	private long level;

	/**创建时间*/
	private DateTime createTime;

	/**更新时间*/
	private DateTime updateTime;

	/**备注*/
	private String remark;

	/**序号*/
	private long sort;

}