/**
 * GenService.java
 * com.we.generator.template
 */

package com.we.generator.template;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import com.google.common.base.Joiner;
import com.uxuexi.core.common.util.EnumUtil;
import com.we.generator.config.GetExcelInfo;
import com.we.generator.config.GetVelocityContext;
import com.we.generator.config.LoadConfigWeb;
import com.we.generator.config.PropProxyConfig;
import com.we.generator.config.TplPathConfig;
import com.we.generator.fileDesc.enums.LogicEnum;
import com.we.generator.fileDesc.web.ServiceDesc;
import com.we.generator.load.ExcelLoader;
import com.we.generator.util.Utils;

/**
 * 根据模板，生成对应的service代码
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月1日 	 
 */
public class GenService {

	public static void genService() throws IOException {

		boolean forceCover = false; //是否覆盖已经存在的文件 
		String basePkg = PropProxyConfig.basePkg;
		forceCover = PropProxyConfig.forceCover;
		String serviceTpl = TplPathConfig.serviceTpl;

		//读取excel功能模块信息
		InputStream ins = GetExcelInfo.getExcelIns();
		Map<Integer, String[]> map = ExcelLoader.loadExcel(ins);

		for (int i = 1; i <= map.size(); i++) {
			String[] rowArr = map.get(i); //每一行  
			genServiceCode(forceCover, basePkg, serviceTpl, rowArr);
		}
	}

	//service
	public static void genServiceCode(boolean force, String basePkg, String serviceTpl, String[] rowArr)
			throws IOException {

		//entity
		String logic = rowArr[0];//逻辑划分
		String moduleCode = rowArr[2];//模块code
		String entityClassName = rowArr[6];//默认实体
		String entityPkgName = Joiner.on(".").join(basePkg, LoadConfigWeb.ENTITY_PKG_NAME);
		String fullEntityClassName = Joiner.on(".").join(entityPkgName, entityClassName);

		//form
		String formClassName = entityClassName.split("Entity")[0] + "Form";
		String formPkgName = Joiner.on(".").join(basePkg, LoadConfigWeb.FORM_PKG_NAME);
		String fullFormClassName = Joiner.on(".").join(formPkgName, formClassName);

		double dl = Double.valueOf(logic);
		int intL = (int) dl;
		LogicEnum le = EnumUtil.get(LogicEnum.class, intL);
		String logicPkg = le.value();
		String sdPkgName = basePkg + "." + logicPkg + "." + moduleCode + "." + LoadConfigWeb.SERVICE_PKG_NAME;
		String serviceClassName = Utils.upperFirst(moduleCode) + "ViewService";

		ServiceDesc sd = new ServiceDesc();
		sd.setPackageName(sdPkgName.toLowerCase());
		sd.setServiceClassName(serviceClassName);
		sd.setEntityClassName(entityClassName);
		sd.setFullEntityClassName(fullEntityClassName);
		sd.setFullFormClassName(fullFormClassName);

		VelocityContext context = GetVelocityContext.getVContext();
		context.put("service", sd);
		context.put("formName", formClassName);

		//service
		String javaOutput = LoadConfigWeb.JAVA_OUTPUT;
		javaOutput = LoadConfigWeb.WEB_OUTPUT + "/" + PropProxyConfig.basePkgRep + "/" + javaOutput;
		String serviceFilePath = Utils.getPath4Pkg(sdPkgName);
		File file = new File(javaOutput, serviceFilePath + "/" + serviceClassName + ".java");

		VelocityHandler generator = new VelocityHandler();
		generator.writeToFile(context, serviceTpl, file, force);
	}
}
