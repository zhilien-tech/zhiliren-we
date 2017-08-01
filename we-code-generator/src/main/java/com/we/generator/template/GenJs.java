/**
 * GenJs.java
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
import com.we.generator.core.ModuleDesc;
import com.we.generator.core.VelocityHandler;
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
	public static void genJsCode(boolean force, VelocityHandler handler, ModuleDesc md, PropertiesProxy propConfig)
			throws ClassNotFoundException, IOException {

		String templatePackage = propConfig.get("template_package");

		String jsOutPut = LoadConfigWeb.JS_OUTPUT;
		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String basePkg = propConfig.get("base_package");
		jsOutPut = webOutput + "/" + basePkg.replace(".", "-") + "/" + jsOutPut;
		String pageFilePath = md.getAtUrl();

		VelocityContext jspCtx = GetVelocityContext.getVContext(md);

		String listJsTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/js/listJS.vm";
		File listJS = new File(jsOutPut, pageFilePath + "/" + "listTable.js");
		handler.writeToFile(jspCtx, listJsTpl, listJS, force);

		//拷贝外部引入文件
		copyFiles(webOutput, basePkg);

	}

	//拷贝外部文件到生成项目中
	private static void copyFiles(String webOutput, String basePkg) {
		//拷贝js
		String filePath = LoadConfigWeb.REFERENCES_PATH;
		String toFilePath = webOutput + "/" + basePkg.replace(".", "-") + "/" + LoadConfigWeb.REFERENCES_OUTPUT;
		CopyFile.copyFile(filePath, toFilePath);

		//拷贝db配置信息
		String dbFilePath = LoadConfigWeb.DB_CONFIG_PATH;
		String toDBPath = webOutput + "/" + basePkg.replace(".", "-") + "/" + LoadConfigWeb.DB_CONFIG_OUTPUT;
		CopyFile.copyFile(dbFilePath, toDBPath);

		//拷贝静态样式
		String staticFilePath = LoadConfigWeb.STATIC_HTML_PATH;
		String toStaticPath = webOutput + "/" + basePkg.replace(".", "-") + "/" + LoadConfigWeb.REFERENCES_OUTPUT;
		CopyFile.copyFile(staticFilePath, toStaticPath);

		//拷贝page分页
		String pageFtlPath = LoadConfigWeb.FTL_PAGE_PATH;
		String toFtlPath = webOutput + "/" + basePkg.replace(".", "-") + "/" + LoadConfigWeb.PUBLIC_PAGE_OUTPUT;
		CopyFile.copyFile(pageFtlPath, toFtlPath);
	}

}
