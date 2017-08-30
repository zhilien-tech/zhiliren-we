/**
 * Generator.java
 * com.we.generator
 */

package com.we.generator.generator;

import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.we.generator.template.GenEntity;
import com.we.generator.template.GenModule;
import com.we.generator.template.GenService;

/**
 * 
 * 代码生成器入口
 * 
 * @author   彭辉
 * @Date	 2017年8月1日
 */
public class Generator {

	private static final Log log = Logs.get();

	//生成实体代码
	public void generateEntity() {
		try {
			GenEntity.genEntityCode();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	//生成Module等代码
	public void generatorModule() throws Exception {
		GenService.genService();
		GenModule.genModule();
		log.info("done!");
	}

}
