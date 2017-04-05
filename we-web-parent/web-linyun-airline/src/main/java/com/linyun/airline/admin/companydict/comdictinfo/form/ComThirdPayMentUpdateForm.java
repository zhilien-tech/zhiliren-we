/**
 * ComThirdPayMentUpdateForm.java
 * com.linyun.airline.admin.companydict.comdictinfo.form
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.companydict.comdictinfo.form;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.uxuexi.core.web.form.ModForm;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2017年4月2日 	 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ComThirdPayMentUpdateForm extends ModForm {
	//用户id
	private Integer userId;
	//字典代码
	private String comDdictCode;
	//第三方公司名称
	private String thirdCompanyName;
	//银行卡账号
	private String bankCardNum;
	//状态,1-启用，2--删除
	private Integer status;
	//更新时间
	private Date updateTime;
	//备注
	private String remark;
	//预留字段1
	private String res1;
	//预留字段2
	private String res2;
	//预留字段3
	private String res3;
	//预留字段4
	private String res4;
	//预留字段5
	private String res5;
}
