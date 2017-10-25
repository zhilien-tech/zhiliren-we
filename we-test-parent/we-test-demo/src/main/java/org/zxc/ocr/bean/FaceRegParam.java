/**
 * FaceRegParam.java
 * org.zxc.ocr.bean
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.ocr.bean;

import lombok.Data;

/**
 * 阿里云人脸识别请求参数封装
 * <p>
 *
 * @author   朱晓川
 * @Date	 2017年10月25日 	 
 */
@Data
public class FaceRegParam {

	/**
	 * 0: 表示通过url识别；
	 * <P>
	 * 1: 表示通过图片content识别
	 */
	private int type = 1;

	/**
	 * 图片地址，当type为0的时候不能为空
	 */
	private String image_url;

	/**
	 * 图片内容，当type为1的时候不能为空
	 */
	private String content;

}
