/**
 * GetVelocityContext.java
 * com.we.generator.config
 */

package com.we.generator.config;

import java.util.List;

import org.apache.velocity.VelocityContext;

import com.we.generator.fileDesc.web.ModuleDesc;
import com.we.generator.fileDesc.web.PageFieldDesc;

/**
 * 根据配置信息，获取引擎上下文内容
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月1日 	 
 */
public class GetVelocityContext {

	//获取引擎上下文
	public static VelocityContext getVContext() {
		String basePkg = PropProxyConfig.basePkg;
		String webName = PropProxyConfig.basePkgRep;
		String company_name = PropProxyConfig.company_name;
		String system_name = PropProxyConfig.system_name;
		String pom_groupId = PropProxyConfig.pom_groupId;
		String pom_atrifactId = PropProxyConfig.pom_atrifactId;
		String pom_version = PropProxyConfig.pom_version;

		VelocityContext context = new VelocityContext();
		context.put("basePkg", basePkg);
		context.put("webName", webName);
		context.put("company_name", company_name);
		context.put("system_name", system_name);
		context.put("groupId", pom_groupId);
		context.put("atrifactId", pom_atrifactId);
		context.put("version", pom_version);

		return context;
	}

	//获取引擎上下文
	public static VelocityContext getVContext(ModuleDesc md) throws ClassNotFoundException {

		//获取列表标题栏
		List<PageFieldDesc> fieldList = GetPageFields.getPageFields(md);

		VelocityContext context = new VelocityContext();
		context.put("module", md);
		context.put("atUrl", md.getAtUrl());
		context.put("moudleName", md.getModuleName());
		context.put("moudleCode", md.getModuleCode());
		context.put("fieldList", fieldList);

		return context;
	}
}
