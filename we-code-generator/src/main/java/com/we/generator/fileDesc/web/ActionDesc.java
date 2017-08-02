/**
 * ModuleDesc.java
 * com.we.generator.core
*/

package com.we.generator.fileDesc.web;

import lombok.Data;

/**
 * Module方法描述
 * @author   朱晓川
 * @Date	 2016年8月31日 	 
 */
@Data
public class ActionDesc {

	/**
	 * 方法名
	 */
	private String actionName;

	/**视图类型*/
	private String viewType;

}
