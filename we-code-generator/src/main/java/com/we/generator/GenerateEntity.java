/**
 * GenerateEntity.java
 * com.we.generator
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package com.we.generator;

/**
 * 生成模块对应的实体类
 * <p>
 *
 * @author   彭辉
 * @Date	 2017年7月28日 	 
 */
public class GenerateEntity {
	public static void main(String[] args) throws Exception {
		Generator generator = new Generator();
		generator.generateEntity();
	}
}
