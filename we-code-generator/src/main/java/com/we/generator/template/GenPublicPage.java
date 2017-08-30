/**
 * GenPublicPage.java
 * com.we.generator.template
 */

package com.we.generator.template;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.velocity.VelocityContext;

import com.we.generator.config.GetVelocityContext;
import com.we.generator.config.LoadConfigWeb;
import com.we.generator.config.PropProxyConfig;
import com.we.generator.config.TplPathConfig;

/**
 * 根据模板，生成对应的 公共页代码
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月1日 	 
 */
public class GenPublicPage {

	//公共页
	public static void genPublicPage(boolean force, VelocityHandler handler, List<VelocityContext> vcLists)
			throws IOException {

		VelocityContext publicCtx = GetVelocityContext.getVContext();
		publicCtx.put("vcLists", vcLists);

		String jspOutPut = LoadConfigWeb.JSP_OUTPUT;
		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		jspOutPut = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + jspOutPut;

		String pubilcOutput = LoadConfigWeb.PUBLIC_PAGE_OUTPUT;
		pubilcOutput = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + pubilcOutput;

		//public页面
		String headerPageTpl = TplPathConfig.headerPageTpl;
		File headerFile = new File(pubilcOutput, "/public/" + "header.jsp");
		handler.writeToFile(publicCtx, headerPageTpl, headerFile, force);

		//左侧菜单栏
		String asidePageTpl = TplPathConfig.asidePageTpl;
		File asideFile = new File(pubilcOutput, "/public/" + "aside.jsp");
		handler.writeToFile(publicCtx, asidePageTpl, asideFile, force);

		//页脚
		String footerPageTpl = TplPathConfig.footerPageTpl;
		File footerFile = new File(pubilcOutput, "/public/" + "footer.jsp");
		handler.writeToFile(publicCtx, footerPageTpl, footerFile, force);

		//main页面
		String mainPageTpl = TplPathConfig.mainPageTpl;
		File mainFile = new File(pubilcOutput, "/" + "main.jsp");
		handler.writeToFile(publicCtx, mainPageTpl, mainFile, force);

		//login页面
		String loginPageTpl = TplPathConfig.loginPageTpl;
		File loginFile = new File(jspOutPut, "/" + "login.jsp");
		handler.writeToFile(publicCtx, loginPageTpl, loginFile, force);

		//404页面
		String error404PageTpl = TplPathConfig.error404PageTpl;
		File error404File = new File(jspOutPut, "/common/" + "404.jsp");
		handler.writeToFile(publicCtx, error404PageTpl, error404File, force);

		//500页面
		String error500PageTpl = TplPathConfig.error500PageTpl;
		File error500File = new File(jspOutPut, "/common/" + "500.jsp");
		handler.writeToFile(publicCtx, error500PageTpl, error500File, force);

		//tld页面
		String tldPageTpl = TplPathConfig.tldPageTpl;
		File tldFile = new File(jspOutPut, "/common/" + "tld.jsp");
		handler.writeToFile(publicCtx, tldPageTpl, tldFile, force);

		//we.tld标签配置文件
		String tldTpl = TplPathConfig.tldTpl;
		File tldF = new File(jspOutPut, "/common/" + "we.tld");
		handler.writeToFile(publicCtx, tldTpl, tldF, force);

	}

}
