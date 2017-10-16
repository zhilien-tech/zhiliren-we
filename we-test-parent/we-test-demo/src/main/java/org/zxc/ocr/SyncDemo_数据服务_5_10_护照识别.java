package org.zxc.ocr;

/*
* Copyright 2017 Alibaba Group
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.zxc.ocr.bean.Input;
import org.zxc.ocr.bean.RecognizeData;
import org.zxc.ocr.util.HttpUtils;

import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.google.common.collect.Maps;

public class SyncDemo_数据服务_5_10_护照识别 {

	private SyncApiClient_数据服务_5_10_护照识别 syncClient = null;

	private static final String app_key = "24624389";
	private static final String app_secret = "3a28e8c97af2d2eadcf2720b279bdc9d";
	private static final String AppCode = "36c5ae22ed87410290bd90cb198e47a7";

	public SyncDemo_数据服务_5_10_护照识别() {
		this.syncClient = SyncApiClient_数据服务_5_10_护照识别.newBuilder().appKey(app_key).appSecret(app_secret).build();
	}

	public void 印刷文字识别_护照识别Demo(byte[] body) {
		ApiResponse response = syncClient.印刷文字识别_护照识别(body);
		printResponse(response);
	}

	private static void printResponse(ApiResponse response) {
		try {
			System.out.println("response code = " + response.getStatusCode());
			System.out.println("response content = " + new String(response.getBody(), "utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//		String filepath = "D:\\test\\tmp\\QQ图片20170915163253.jpg";
		String filepath = "D:\\test\\tmp\\comonImage.jpg";
		byte[] bytes = Files.readBytes(filepath);
		String imageDataB64 = Base64.encodeBase64String(bytes);
		appCodeCallCommonOCR(imageDataB64);
	}

	public static void aliPassportOcr(String imageDataB64) {
		Input input = new Input(imageDataB64, null);

		RecognizeData rd = new RecognizeData();
		rd.getInputs().add(input);

		String content = Json.toJson(rd);

		byte[] body = content.getBytes(Charset.forName("utf-8"));
		SyncDemo_数据服务_5_10_护照识别 service = new SyncDemo_数据服务_5_10_护照识别();
		service.印刷文字识别_护照识别Demo(body);
	}

	/**
	 * 杭州网尚科技有限公司提供的接口
	 * <p>识别类型:
	 * {
	        "typeid": "1",
	        "typename": "一代身份证"
	    },
	    {
	        "typeid": "2",
	        "typename": "二代身份证正面"
	    },
	    {
	        "typeid": "3",
	        "typename": "二代身份证证背面"
	    },
	    {
	        "typeid": "4",
	        "typename": "临时身份证"
	    },
	    {
	        "typeid": "5",
	        "typename": "驾照"
	    },
	    {
	        "typeid": "6",
	        "typename": "行驶证"
	    },
	    {
	        "typeid": "7",
	        "typename": "军官证"
	    },
	    {
	        "typeid": "9",
	        "typename": "中华人民共和国往来港澳通行证(机读证件 护照幅面)"
	    },
	    {
	        "typeid": "10",
	        "typename": "台湾居民往来大陆通行证(机读证件 护照幅面)"
	    },
	    {
	        "typeid": "11",
	        "typename": "大陆居民往来台湾通行证(机读证件 护照幅面)"
	    },
	    {
	        "typeid": "12",
	        "typename": "签证(机读证件 护照幅面)"
	    },
	    {
	        "typeid": "13",
	        "typename": "护照(机读证件 护照幅面)"
	    },
	    {
	        "typeid": "14",
	        "typename": "港澳居民来往内地通行证正面(机读证件 卡幅面)"
	    },
	    {
	        "typeid": "15",
	        "typename": "港澳居民来往内地通行证背面(机读证件 卡幅面)"
	    },
	    {
	        "typeid": "16",
	        "typename": "户口本"
	    },
	    {
	        "typeid": "1000",
	        "typename": "居住证"
	    },
	    {
	        "typeid": "1001",
	        "typename": "香港永久性居民身份证"
	    },
	    {
	        "typeid": "1002",
	        "typename": "登机牌(拍照设备目前不支持登机牌的识别)"
	    },
	    {
	        "typeid": "1003",
	        "typename": "边民证(A)(照片页)"
	    },
	    {
	        "typeid": "1004",
	        "typename": "边民证(B)(个人信息页)"
	    },
	    {
	        "typeid": "1005",
	        "typename": "澳门身份证"
	    },
	    {
	        "typeid": "1006",
	        "typename": "领取凭证(AVA6支持) "
	    },
	    {
	        "typeid": "1007",
	        "typename": "律师证(A) (信息页)"
	    },
	    {
	        "typeid": "1008",
	        "typename": "律师证(B) (照片页)"
	    },
	    {
	        "typeid": "1030",
	        "typename": "全民健康保险卡 "
	    },
	    {
	        "typeid": "1031",
	        "typename": "台湾身份证正面"
	    },
	    {
	        "typeid": "1032",
	        "typename": "台湾身份证背面"
	    }
	 * 网尚护照识别，注意content的内容是图片内容，不是inputs,json
	 * <p>
	 *
	 */
	private static void appCodeCallWS(String imageDataB64) {
		String host = "http://jisusfzsb.market.alicloudapi.com";
		String path = "/idcardrecognition/recognize";
		String method = "POST";
		String appcode = AppCode;
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		//根据API的要求，定义相对应的Content-Type
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("typeid", "13");//护照
		Map<String, String> bodys = new HashMap<String, String>();
		bodys.put("pic", imageDataB64);
		//		System.out.println(content);

		try {
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
			//获取response的body
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void appCodeCallCommonOCR(String imageDataB64) {
		String host = "http://tysbgpu.market.alicloudapi.com";
		String path = "/api/predict/ocr_general";
		String method = "POST";
		String appcode = AppCode;
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		//根据API的要求，定义相对应的Content-Type
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();

		Map<String, Object> configure = Maps.newHashMap();
		configure.put("min_size", 10); //图片中文字的最小高度
		configure.put("output_prob", true);//是否输出文字框的概率
		Map<String, Object> payload = Maps.newHashMap();
		payload.put("image", imageDataB64);
		payload.put("configure", configure);
		String bodys = Json.toJson(payload);

		try {
			/**
			* 重要提示如下:
			* HttpUtils请从
			* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			* 下载
			*
			* 相应的依赖请参照
			* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			*/
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
			//获取response的body
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
