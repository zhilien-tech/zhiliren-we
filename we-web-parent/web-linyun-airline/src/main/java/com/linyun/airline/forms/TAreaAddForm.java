package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.AddForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TAreaAddForm extends AddForm implements Serializable {
	private static final long serialVersionUID = 1L;

	/**区域名称*/
	private String areaName;

	/**字典信息id*/
	private String infoId;

	/**备注*/
	private String remark;

}