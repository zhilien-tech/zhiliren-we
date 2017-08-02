/**
 * GenWebXml.java
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
 * 根据模板，生成项目的入口文件
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月1日 	 
 */
public class GenMainSetup {

	public static void genCode(boolean force, VelocityHandler handler) throws IOException {

		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		String javaOutput = LoadConfigWeb.JAVA_OUTPUT;
		String basePath4Pkg = PropProxyConfig.basePath4Pkg;
		String Output = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + javaOutput + "/" + basePath4Pkg;

		VelocityContext vCtx = GetVelocityContext.getVContext();

		String webTpl = TplPathConfig.webMainTpl;
		File file = new File(Output, "/" + "MainModule.java");
		handler.writeToFile(vCtx, webTpl, file, force);

		String setupTpl = TplPathConfig.weSetupTpl;
		File setupFile = new File(Output, "/" + "WeSetup.java");
		handler.writeToFile(vCtx, setupTpl, setupFile, force);

	}
}
