/**
 * GenWebXml.java
 * com.we.generator.template
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.we.generator.template;

import java.io.File;
import java.io.IOException;

import org.apache.velocity.VelocityContext;

import com.we.generator.config.GetVelocityContext;
import com.we.generator.config.LoadConfigWeb;
import com.we.generator.config.PropProxyConfig;
import com.we.generator.config.TplPathConfig;

/**
 * 根据模板，生成对应的Web.xml文件
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月1日 	 
 */
public class GenWebXml {

	//web.xml
	public static void genXmlFile(boolean force, VelocityHandler handler) throws IOException {
		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String webTpl = TplPathConfig.webXmlTpl;
		String Output = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + LoadConfigWeb.JSP_OUTPUT;
		VelocityContext vCtx = GetVelocityContext.getVContext();
		File file = new File(Output, "/" + "web.xml");
		handler.writeToFile(vCtx, webTpl, file, force);
	}
}
