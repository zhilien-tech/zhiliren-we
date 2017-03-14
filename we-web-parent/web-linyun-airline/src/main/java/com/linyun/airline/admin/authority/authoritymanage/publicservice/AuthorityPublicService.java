/**
 * AuthorityPublicService.java
 * com.linyun.airline.admin.authority.authoritymanage.publicservice
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.linyun.airline.admin.authority.authoritymanage.publicservice;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Lists;
import com.linyun.airline.admin.login.service.LoginService;
import com.linyun.airline.common.enums.CompanyTypeEnum;
import com.linyun.airline.entities.TCompanyEntity;
import com.linyun.airline.entities.TCompanyFunctionMapEntity;
import com.uxuexi.core.common.util.Util;
import com.uxuexi.core.web.base.service.BaseService;

/**
 * 权限管理对外接口
 * @author   崔建斌
 * @Date	 2017年3月13日 	 
 */
@IocBean
public class AuthorityPublicService extends BaseService<TCompanyFunctionMapEntity> {

	//新增公司后给公司配置功能
	@Aop("txDb")
	public boolean companyFunction(final HttpSession session) {
		//通过session获取公司的id
		TCompanyEntity company = (TCompanyEntity) session.getAttribute(LoginService.USER_COMPANY_KEY);
		Long companyId = company.getId();//得到公司的id
		//根据公司id查询出是上游公司还是代理商
		TCompanyEntity fetchType = dbDao.fetch(TCompanyEntity.class, companyId);
		String comType = fetchType.getComType();//得到公司类型
		int parseComType = Integer.parseInt(comType);//类型转换
		//上游公司功能ID
		int[] function = { 43, 44, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 68,
				69, 75, 76, 81, 84, 85, 86, 87, 88 };
		List<TCompanyFunctionMapEntity> functionList = Lists.newArrayList();
		if (parseComType == CompanyTypeEnum.UPCOMPANY.intKey()) {
			for (int i = 0; i < function.length; i++) {
				//向公司功能关系表中添加数据
				TCompanyFunctionMapEntity one = new TCompanyFunctionMapEntity();
				one.setComId(companyId);
				one.setFunId(function[i]);
				functionList.add(one);
			}
		}
		List<TCompanyFunctionMapEntity> insert = dbDao.insert(functionList);
		return !Util.isEmpty(insert);
	}
}
