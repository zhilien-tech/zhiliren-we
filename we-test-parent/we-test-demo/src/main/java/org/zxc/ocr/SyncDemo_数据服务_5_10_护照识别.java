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

import org.apache.commons.codec.binary.Base64;
import org.nutz.json.Json;
import org.nutz.lang.Files;
import org.zxc.ocr.bean.Input;
import org.zxc.ocr.bean.RecognizeData;

import com.alibaba.cloudapi.sdk.core.model.ApiResponse;

public class SyncDemo_数据服务_5_10_护照识别 {

	private SyncApiClient_数据服务_5_10_护照识别 syncClient = null;

	private static final String app_key = "24624389";
	private static final String app_secret = "3a28e8c97af2d2eadcf2720b279bdc9d";

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
		String filepath = "D:\\test\\tmp\\QQ图片20170915163253.jpg";
		byte[] bytes = Files.readBytes(filepath);
		String imageDataValue = Base64.encodeBase64String(bytes);
		Input input = new Input(imageDataValue, null);

		RecognizeData rd = new RecognizeData();
		rd.getInputs().add(input);

		String content = Json.toJson(rd);
		byte[] body = content.getBytes(Charset.forName("utf-8"));

		SyncDemo_数据服务_5_10_护照识别 service = new SyncDemo_数据服务_5_10_护照识别();
		service.印刷文字识别_护照识别Demo(body);
	}
}
