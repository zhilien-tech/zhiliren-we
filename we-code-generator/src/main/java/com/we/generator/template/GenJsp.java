/**
 * GenJsp.java
 * com.we.generator.template
 */

package com.we.generator.template;

import java.io.File;
import java.io.IOException;

import org.apache.velocity.VelocityContext;

import com.we.generator.config.GetVelocityContext;
import com.we.generator.config.LoadConfigWeb;
import com.we.generator.config.PropProxyConfig;
import com.we.generator.config.TplPathConfig;
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
	public static void genJspCode(boolean force, VelocityHandler handler, ModuleDesc md) throws ClassNotFoundException,
			IOException {

		String pageFilePath = md.getAtUrl();

		String jspOutPut = LoadConfigWeb.JSP_OUTPUT;
		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		jspOutPut = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + jspOutPut;

		VelocityContext jspCtx = GetVelocityContext.getVContext(md);

		String listTpl = TplPathConfig.listTpl;
		File listPage = new File(jspOutPut, pageFilePath + "/" + "list.jsp");
		handler.writeToFile(jspCtx, listTpl, listPage, force);

		String updateTpl = TplPathConfig.updateTpl;
		File updatePage = new File(jspOutPut, pageFilePath + "/" + "update.jsp");
		handler.writeToFile(jspCtx, updateTpl, updatePage, force);

		String addTpl = TplPathConfig.addTpl;
		File addPage = new File(jspOutPut, pageFilePath + "/" + "add.jsp");
		handler.writeToFile(jspCtx, addTpl, addPage, force);

		for (ActionDesc ad : md.getActionList()) {
			File commonPage = new File(jspOutPut, pageFilePath + "/" + ad.getActionName() + ".jsp");
			String commonTpl = TplPathConfig.commonTpl;
			handler.writeToFile(jspCtx, commonTpl, commonPage, force);
		}

	}
}
