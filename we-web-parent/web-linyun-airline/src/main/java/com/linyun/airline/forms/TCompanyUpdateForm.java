package com.linyun.airline.forms;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.joda.time.DateTime;

import com.uxuexi.core.web.form.ModForm;

@Data
@EqualsAndHashCode(callSuper = true)
public class TCompanyUpdateForm extends ModForm implements Serializable {
	private static final long serialVersionUID = 1L;

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

}