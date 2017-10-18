/**
 * GenService.java
 * com.we.generator.template
 */

package com.we.generator.template;

import java.io.File;
import java.io.IOException;

import org.apache.velocity.VelocityContext;

import com.uxuexi.core.common.util.Util;
import com.we.generator.config.GetVelocityContext;
import com.we.generator.config.LoadConfigWeb;
import com.we.generator.config.PropProxyConfig;
import com.we.generator.config.TplPathConfig;

/**
 * 根据模板，生成对应的websocket代码
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年9月28日 	 
 */
public class GenWebSocket {

	public static void genWebSocket() throws IOException {

		String templatePackage = PropProxyConfig.templatePackage;
		if (Util.eq("newVisaBootstrap", templatePackage)) {
			boolean force = false; //是否覆盖已经存在的文件
			force = PropProxyConfig.forceCover;
			VelocityHandler handler = new VelocityHandler();
			String webOutput = LoadConfigWeb.WEB_OUTPUT;
			String javaOutput = LoadConfigWeb.JAVA_OUTPUT;
			String jspOutput = LoadConfigWeb.JS_OUTPUT;
			String basePath4Pkg = PropProxyConfig.basePath4Pkg;
			String outPut = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + javaOutput + "/" + basePath4Pkg
					+ "/websocket";
			String pageOutPut = webOutput + "/" + PropProxyConfig.basePkgRep + "/" + jspOutput + "/websocket";

			//java代码
			getWebSocketCode(force, templatePackage, outPut, handler);

			//Demo页面
			getDemoPage(force, pageOutPut, handler);

		}

	}

	//webSocket.java
	public static void getWebSocketCode(boolean force, String templatePackage, String outPut, VelocityHandler handler)
			throws IOException {

		VelocityContext vCtx = GetVelocityContext.getVContext();

		String demoWSHandlerTpl = TplPathConfig.demoWSHandlerTpl;
		File demoWSHandlerFile = new File(outPut, "/" + "DemoWSHandler.java");
		handler.writeToFile(vCtx, demoWSHandlerTpl, demoWSHandlerFile, force);

		String handshakeInterceptorTpl = TplPathConfig.handshakeInterceptorTpl;
		File handshakeInterceptorFile = new File(outPut, "/" + "HandshakeInterceptor.java");
		handler.writeToFile(vCtx, handshakeInterceptorTpl, handshakeInterceptorFile, force);

		String webSocketConfigTpl = TplPathConfig.webSocketConfigTpl;
		File webSocketConfigFile = new File(outPut, "/" + "WebSocketConfig.java");
		handler.writeToFile(vCtx, webSocketConfigTpl, webSocketConfigFile, force);

	}

	//webSocketDemo页面
	public static void getDemoPage(boolean force, String outPut, VelocityHandler handler) throws IOException {
		VelocityContext vCtx = GetVelocityContext.getVContext();
		String demoWSHandlerTpl = TplPathConfig.webSocketDemoPageTpl;
		File demoWSHandlerFile = new File(outPut, "/" + "demo.html");
		handler.writeToFile(vCtx, demoWSHandlerTpl, demoWSHandlerFile, force);
	}

}
