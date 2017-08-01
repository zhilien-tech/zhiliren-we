/**
 * GetVelocityContext.java
 * com.we.generator.config
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.we.generator.config;

import java.util.List;

import org.apache.velocity.VelocityContext;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.json.JsonLoader;

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
		Ioc ioc = new NutIoc(new JsonLoader(LoadConfigWeb.IOC_KVCFG_PATH));
		PropertiesProxy propConfig = ioc.get(PropertiesProxy.class, "propConfig");
		PropertiesProxy webPropConfig = ioc.get(PropertiesProxy.class, "webPropConfig");
		String basePkg = propConfig.get("base_package");
		String webName = basePkg.replace(".", "-");
		String company_name = webPropConfig.get("company_name");
		String system_name = webPropConfig.get("system_name");
		String pom_groupId = webPropConfig.get("pom_groupId");
		String pom_atrifactId = webPropConfig.get("pom_atrifactId");
		String pom_version = webPropConfig.get("pom_version");

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
