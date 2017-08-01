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
import com.we.generator.core.VelocityHandler;

/**
 * 根据模板，生成对应的Web.xml文件
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月1日 	 
 */
public class GenWebXml {

	//web.xml
	public static void genXmlFile(boolean force, VelocityHandler handler, PropertiesProxy propConfig)
			throws IOException {

		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String basePkg = propConfig.get("base_package");
		String templatePackage = propConfig.get("template_package");
		String Output = webOutput + "/" + basePkg.replace(".", "-") + "/" + LoadConfigWeb.JSP_OUTPUT;

		String webTpl = LoadConfigWeb.TEMPLATE_PATH + templatePackage + "/xml/web.vm";

		VelocityContext vCtx = GetVelocityContext.getVContext();

		File file = new File(Output, "/" + "web.xml");
		handler.writeToFile(vCtx, webTpl, file, force);

	}
}
