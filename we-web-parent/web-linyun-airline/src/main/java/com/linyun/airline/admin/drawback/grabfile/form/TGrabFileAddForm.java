package com.linyun.airline.admin.drawback.grabfile.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.uxuexi.core.web.form.AddForm;
import java.util.Date;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class TGrabFileAddForm extends AddForm implements Serializable{
	private static final long serialVersionUID = 1L;
		
	/**抓取邮件id*/
	private Integer mailId;
		
	/**上级id*/
	private Integer parentId;
		
	/**文件名称*/
	private String fileName;
		
	/**文件地址*/
	private String url;
		
	/**文件大小*/
	private String fileSize;
		
	/**文件类型(1-文件夹;2-文件;)*/
	private Integer type;
		
	/**状态(1-已删除;2-已启用;)*/
	private Integer status;
		
	/**创建时间*/
	private Date createTime;
		
	/**修改时间*/
	private Date updateTime;
		
	/**层级*/
	private Integer naval;
		
	/**完整路径*/
	private String fullPath;
		
}