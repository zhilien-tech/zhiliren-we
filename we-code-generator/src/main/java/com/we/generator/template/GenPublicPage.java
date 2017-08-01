/**
 * GenPublicPage.java
 * com.we.generator.template
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.we.generator.template;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.nutz.ioc.impl.PropertiesProxy;

import com.we.generator.config.GetVelocityContext;
import com.we.generator.config.LoadConfigWeb;
import com.we.generator.core.VelocityHandler;

/**
 * 根据模板，生成对应的 公共页代码
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月1日 	 
 */
public class GenPublicPage {

	//公共页
	public static void genPublicPage(boolean force, VelocityHandler handler, PropertiesProxy propConfig,
			List<VelocityContext> vcLists) throws IOException {

		VelocityContext publicCtx = GetVelocityContext.getVContext();
		publicCtx.put("vcLists", vcLists);

		String jspOutPut = LoadConfigWeb.JSP_OUTPUT;
		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String basePkg = propConfig.get("base_package");
		String templatePackage = propConfig.get("template_package");
		jspOutPut = webOutput + "/" + basePkg.replace(".", "-") + "/" + jspOutPut;

		String pubilcOutput = LoadConfigWeb.PUBLIC_PAGE_OUTPUT;
		pubilcOutput = webOutput + "/" + basePkg.replace(".", "-") + "/" + pubilcOutput;

		//public页面
		String headerPageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/public/header.vm";
		File headerFile = new File(pubilcOutput, "/public/" + "header.jsp");
		handler.writeToFile(publicCtx, headerPageTpl, headerFile, force);

		//左侧菜单栏
		String asidePageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/public/aside.vm";
		File asideFile = new File(pubilcOutput, "/public/" + "aside.jsp");
		handler.writeToFile(publicCtx, asidePageTpl, asideFile, force);

		//页脚
		String footerPageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/public/footer.vm";
		File footerFile = new File(pubilcOutput, "/public/" + "footer.jsp");
		handler.writeToFile(publicCtx, footerPageTpl, footerFile, force);

		//main页面
		String mainPageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/main.vm";
		File mainFile = new File(pubilcOutput, "/" + "main.jsp");
		handler.writeToFile(publicCtx, mainPageTpl, mainFile, force);

		//login页面
		String loginPageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/login.vm";
		File loginFile = new File(jspOutPut, "/" + "login.jsp");
		handler.writeToFile(publicCtx, loginPageTpl, loginFile, force);

		//404页面
		String error404PageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common/404.vm";
		File error404File = new File(jspOutPut, "/common/" + "404.jsp");
		handler.writeToFile(publicCtx, error404PageTpl, error404File, force);

		//500页面
		String error500PageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common/500.vm";
		File error500File = new File(jspOutPut, "/common/" + "500.jsp");
		handler.writeToFile(publicCtx, error500PageTpl, error500File, force);

		//tld页面
		String tldPageTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common/tld.vm";
		File tldFile = new File(jspOutPut, "/common/" + "tld.jsp");
		handler.writeToFile(publicCtx, tldPageTpl, tldFile, force);

		//we.tld标签配置文件
		String tldTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/view/common/wetld.vm";
		File tldF = new File(jspOutPut, "/common/" + "we.tld");
		handler.writeToFile(publicCtx, tldTpl, tldF, force);

	}

}
