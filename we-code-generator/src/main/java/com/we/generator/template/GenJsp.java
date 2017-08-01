/**
 * GenJsp.java
 * com.we.generator.template
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.we.generator.template;

import java.io.File;
import java.io.IOException;

import org.apache.velocity.VelocityContext;
import org.nutz.ioc.impl.PropertiesProxy;

import com.we.generator.config.GetVelocityContext;
import com.we.generator.config.LoadConfigWeb;
import com.we.generator.fileDesc.web.ActionDesc;
import com.we.generator.fileDesc.web.ModuleDesc;

/**
 * 根据模板，生成对应的jsp页面
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月1日 	 
 */
public class GenJsp {

	//JSP页
	public static void genJspCode(boolean force, VelocityHandler handler, ModuleDesc md, PropertiesProxy propConfig)
			throws ClassNotFoundException, IOException {

		String pageFilePath = md.getAtUrl();

		String jspOutPut = LoadConfigWeb.JSP_OUTPUT;
		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String basePkg = propConfig.get("base_package");
		String templatePackage = propConfig.get("template_package");
		jspOutPut = webOutput + "/" + basePkg.replace(".", "-") + "/" + jspOutPut;

		VelocityContext jspCtx = GetVelocityContext.getVContext(md);

		String listTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/list.vm";
		File listPage = new File(jspOutPut, pageFilePath + "/" + "list.jsp");
		handler.writeToFile(jspCtx, listTpl, listPage, force);

		String updateTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/update.vm";
		File updatePage = new File(jspOutPut, pageFilePath + "/" + "update.jsp");
		handler.writeToFile(jspCtx, updateTpl, updatePage, force);

		String addTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/add.vm";
		File addPage = new File(jspOutPut, pageFilePath + "/" + "add.jsp");
		handler.writeToFile(jspCtx, addTpl, addPage, force);

		for (ActionDesc ad : md.getActionList()) {
			File commonPage = new File(jspOutPut, pageFilePath + "/" + ad.getActionName() + ".jsp");
			String commonTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common.vm";
			handler.writeToFile(jspCtx, commonTpl, commonPage, force);
		}

	}
}
