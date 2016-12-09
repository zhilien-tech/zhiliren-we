package com.linyun.airline.admin.operationsArea.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.ModForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TMessageUpdateForm extends ModForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**标题*/
	private String msgTitle;
		
	/**内容*/
	private String msgContent;
		
	/**消息类型*/
	private Integer msgType;
		
	/**生成日期*/
	private Date generateTime;
		
	/**优先级*/
	private Integer priorityLevel;
		
}