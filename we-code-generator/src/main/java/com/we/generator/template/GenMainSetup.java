/**
 * GenWebXml.java
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
import com.we.generator.util.Utils;

/**
 * 根据模板，生成项目的入口文件
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月1日 	 
 */
public class GenMainSetup {

	public static void genCode(boolean force, VelocityHandler handler, PropertiesProxy propConfig) throws IOException {

		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String javaOutput = LoadConfigWeb.JAVA_OUTPUT;
		String basePkg = propConfig.get("base_package");
		String templatePackage = propConfig.get("template_package");
		String webName = basePkg.replace(".", "-");
		String Output = webOutput + "/" + webName + "/" + javaOutput + "/" + Utils.getPath4Pkg(basePkg);

		VelocityContext vCtx = GetVelocityContext.getVContext();

		String webTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/MainModule.vm";
		File file = new File(Output, "/" + "MainModule.java");
		handler.writeToFile(vCtx, webTpl, file, force);

		String setupTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/WeSetup.vm";
		File setupFile = new File(Output, "/" + "WeSetup.java");
		handler.writeToFile(vCtx, setupTpl, setupFile, force);

	}
}
