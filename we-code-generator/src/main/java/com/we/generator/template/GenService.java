/**
 * GenService.java
 * com.we.generator.template
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.we.generator.template;

import java.io.File;
import java.io.IOException;

import org.apache.velocity.VelocityContext;

import com.google.common.base.Joiner;
import com.uxuexi.core.common.util.EnumUtil;
import com.we.generator.config.GetVelocityContext;
import com.we.generator.config.LoadConfigWeb;
import com.we.generator.core.ServiceDesc;
import com.we.generator.core.VelocityHandler;
import com.we.generator.core.enums.LogicEnum;
import com.we.generator.util.Utils;

/**
 * 根据模板，生成对应的service代码
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年8月1日 	 
 */
public class GenService {

	//service
	public static void genServiceCode(boolean force, String basePkg, String serviceTpl, String[] rowArr)
			throws IOException {

		String javaOutput = LoadConfigWeb.JAVA_OUTPUT;
		String webOutput = LoadConfigWeb.WEB_OUTPUT;
		javaOutput = webOutput + "/" + basePkg.replace(".", "-") + "/" + javaOutput;

		//逻辑划分
		String logic = rowArr[0];

		//模块code
		String moduleCode = rowArr[2];

		//默认实体
		String entityClassName = rowArr[6];
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
		String serviceFilePath = Utils.getPath4Pkg(sdPkgName);
		File file = new File(javaOutput, serviceFilePath + "/" + serviceClassName + ".java");

		VelocityHandler generator = new VelocityHandler();
		generator.writeToFile(context, serviceTpl, file, force);
	}
}
