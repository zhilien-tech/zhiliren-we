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

import com.alibaba.cloudapi.sdk.core.BaseApiClient;
import com.alibaba.cloudapi.sdk.core.BaseApiClientBuilder;
import com.alibaba.cloudapi.sdk.core.annotation.NotThreadSafe;
import com.alibaba.cloudapi.sdk.core.annotation.ThreadSafe;
import com.alibaba.cloudapi.sdk.core.enums.Method;
import com.alibaba.cloudapi.sdk.core.enums.Scheme;
import com.alibaba.cloudapi.sdk.core.model.ApiRequest;
import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.alibaba.cloudapi.sdk.core.model.BuilderParams;

@ThreadSafe
public final class SyncApiClient_数据服务_人脸识别 extends BaseApiClient {
	public final static String GROUP_HOST = "rlsxsb.market.alicloudapi.com";

	private SyncApiClient_数据服务_人脸识别(BuilderParams builderParams) {
		super(builderParams);
	}

	@NotThreadSafe
	public static class Builder extends BaseApiClientBuilder<SyncApiClient_数据服务_人脸识别.Builder, SyncApiClient_数据服务_人脸识别> {

		@Override
		protected SyncApiClient_数据服务_人脸识别 build(BuilderParams params) {
			return new SyncApiClient_数据服务_人脸识别(params);
		}
	}

	public static Builder newBuilder() {
		return new SyncApiClient_数据服务_人脸识别.Builder();
	}

	public static SyncApiClient_数据服务_人脸识别 getInstance() {
		return getApiClassInstance(SyncApiClient_数据服务_人脸识别.class);
	}

	public ApiResponse 数据服务_人脸识别(byte[] _body) {
		String _apiPath = "/face/attribute";

		ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.POST_BODY, GROUP_HOST, _apiPath, _body);

		return syncInvoke(_apiRequest);
	}

}
