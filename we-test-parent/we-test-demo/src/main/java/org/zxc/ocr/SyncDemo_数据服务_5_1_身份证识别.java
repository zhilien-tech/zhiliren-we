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

public class SyncDemo_数据服务_5_1_身份证识别 {

	private SyncApiClient_数据服务_5_1_身份证识别 syncClient = null;

	private static final String app_key = "24624389";
	private static final String app_secret = "3a28e8c97af2d2eadcf2720b279bdc9d";
	private static final String AppCode = "36c5ae22ed87410290bd90cb198e47a7";

	public SyncDemo_数据服务_5_1_身份证识别() {
		this.syncClient = SyncApiClient_数据服务_5_1_身份证识别.newBuilder().appKey(app_key).appSecret(app_secret).build();
	}

	public void 印刷文字识别_身份证识别Demo(byte[] body) {
		ApiResponse response = syncClient.印刷文字识别_身份证识别(body);
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
		String filepath = "D:\\test\\tmp\\ID1.jpg";

		byte[] bytes = Files.readBytes(filepath);
		String imageDataValue = Base64.encodeBase64String(bytes);
		Input input = new Input(imageDataValue, "face");

		RecognizeData rd = new RecognizeData();
		rd.getInputs().add(input);

		String content = Json.toJson(rd);
		appSecretCall(content);
	}

	private static void appSecretCall(String content) {
		byte[] body = content.getBytes(Charset.forName("utf-8"));
		SyncDemo_数据服务_5_1_身份证识别 service = new SyncDemo_数据服务_5_1_身份证识别();
		service.印刷文字识别_身份证识别Demo(body);
	}

	private static void appCodeCall(String content) {
		String host = "https://dm-51.data.aliyun.com";
		String path = "/rest/160601/ocr/ocr_idcard.json";
		String method = "POST";

		String appcode = AppCode;
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		//根据API的要求，定义相对应的Content-Type
		headers.put("Content-Type", "application/json; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		System.out.println(content);
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
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, content);
			//获取response的body
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
