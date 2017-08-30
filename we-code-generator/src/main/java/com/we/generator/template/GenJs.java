/**
 * GenJs.java
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
import com.we.generator.fileDesc.web.ModuleDesc;
import com.we.generator.util.CopyFile;

/**
 * 根据模板，生成对应的JS文件
 * <p>
 *
 * @author   朱晓川
 * @Date	 2017年8月1日 	 
 */
public class GenJs {

	//JavaScript
	public static void genJsCode(boolean force, VelocityHandler handler, ModuleDesc md) throws ClassNotFoundException,
			IOException {

		String jsOutPut = LoadConfigWeb.JS_OUTPUT;
		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		jsOutPut = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + jsOutPut;
		String pageFilePath = md.getAtUrl();

		VelocityContext jspCtx = GetVelocityContext.getVContext(md);

		String listJsTpl = TplPathConfig.listJsTpl;
		File listJS = new File(jsOutPut, pageFilePath + "/" + "listTable.js");
		handler.writeToFile(jspCtx, listJsTpl, listJS, force);

		//拷贝外部引入文件
		copyFiles(webOutput);

	}

	//拷贝外部文件到生成项目中
	private static void copyFiles(String webOutput) {
		//拷贝js
		String filePath = LoadConfigWeb.REFERENCES_PATH;
		String toFilePath = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + LoadConfigWeb.REFERENCES_OUTPUT;
		CopyFile.copyFile(filePath, toFilePath);

		//拷贝db配置信息
		String dbFilePath = LoadConfigWeb.DB_CONFIG_PATH;
		String toDBPath = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + LoadConfigWeb.DB_CONFIG_OUTPUT;
		CopyFile.copyFile(dbFilePath, toDBPath);

		//拷贝静态样式
		String staticFilePath = LoadConfigWeb.STATIC_HTML_PATH;
		String toStaticPath = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + LoadConfigWeb.REFERENCES_OUTPUT;
		CopyFile.copyFile(staticFilePath, toStaticPath);

		//拷贝page分页
		String pageFtlPath = LoadConfigWeb.FTL_PAGE_PATH;
		String toFtlPath = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + LoadConfigWeb.PUBLIC_PAGE_OUTPUT;
		CopyFile.copyFile(pageFtlPath, toFtlPath);
	}

}
