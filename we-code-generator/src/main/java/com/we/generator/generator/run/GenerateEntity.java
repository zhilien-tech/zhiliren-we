/**
 * GenerateEntity.java
 * com.we.generator
*/

package com.we.generator.generator.run;

import com.we.generator.config.SetWebDirectory;
import com.we.generator.generator.Generator;

/**
 * 
 * 创建web项目文件夹、生成模块对应的实体类
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年7月28日 	 
 */
public class GenerateEntity {
	public static void main(String[] args) throws Exception {
		Generator generator = new Generator();
		//创建web项目的目录结构
		SetWebDirectory.makeFiles();
		//生成实体
		generator.generateEntity();
	}
}
