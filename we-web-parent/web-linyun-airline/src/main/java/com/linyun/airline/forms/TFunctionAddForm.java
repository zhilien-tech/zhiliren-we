package com.linyun.airline.forms;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.joda.time.DateTime;

import com.uxuexi.core.web.form.AddForm;

/**
* @ClassName: TFunctionAddForm
* @author 崔建斌 
* @date 2016年11月18日 上午11:24:31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TFunctionAddForm extends AddForm {

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
	private DateTime upDateTimeTime;

	/**备注*/
	private String remark;

	/**序号*/
	private long sort;

}