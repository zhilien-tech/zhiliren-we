/**
 * TCompanyModForm.java
 * com.linyun.airline.admin.authority.companyfunction.form
 * Copyright (c) 2016, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.authority.companyfunction.form;

import javax.validation.constraints.Pattern;

import lombok.Data;

import org.joda.time.DateTime;

/**
 * TODO(这里用一句话描述这个类的作用)
 * @author   崔建斌
 * @Date	 2016年12月1日 	 
 */
@Data
public class TCompanyModForm {
	/**主键*/
	private long id;

	/**管理员账号id*/
	private long adminId;

	/**公司名称*/
	private String comName;

	/**公司类型*/
	private String comType;

	/**备注*/
	private String remark;

	/**联系人*/
	private String connect;

	/**联系人手机号*/
	private String mobile;

	/**邮箱*/
	private String email;

	/**座机*/
	private String phonenumber;

	/**地址*/
	private String address;

	/**营业执照*/
	private String license;

	/**操作人*/
	private long opid;

	/**创建时间*/
	private DateTime createtime;

	/**最后修改时间*/
	private DateTime lastupdatetime;

	/**删除标识*/
	private String deletestatus;

	/**职位关联的功能id列表,数字和逗号组成,可以为空*/
	@Pattern(regexp = "(\\d+,?)*")
	private String functionIds;
}
