/**
 * GenPomXml.java
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

/**
 * 根据模板，生成Maven项目对应的 pom.xml文件
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月1日 	 
 */
public class GenPomXml {

	//pom.xml
	public static void genXmlFile(boolean force, VelocityHandler handler) throws IOException {

		String pomTpl = TplPathConfig.pomTpl;
		String pomOutput = LoadConfigWeb.WEB_OUTPUT + "/" + PropProxyConfig.basePkgRep;
		VelocityContext pomCtx = GetVelocityContext.getVContext();
		File file = new File(pomOutput, "/" + "pom.xml");
		handler.writeToFile(pomCtx, pomTpl, file, force);
	}
}
