package com.yy.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import sun.misc.BASE64Encoder;

import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSONObject;

public class HttpEnsms {
	/**
	 * http加密
	 * @param args
	 */
	public static void main(String[] args) {

		SimpleDateFormat df = new SimpleDateFormat("MMddHHmmss");
		String Stamp = df.format(new Date());
		String password = "123456";
		String Secret = MD5.GetMD5Code(password + Stamp).toUpperCase();

		try {
			JSONObject j = new JSONObject();
			j.put("UserName", "qq");
			j.put("Stamp", Stamp);
			j.put("Secret", Secret);
			j.put("Moblie", "18910815601");
			j.put("Text", "您的验证码是：8859【华信】");
			j.put("Ext", "");
			j.put("SendTime", "");
			//获取json字符串
			String json = j.toString();
			byte[] data = json.getBytes("utf-8");
			byte[] key = password.getBytes();
			//获取加密的key
			byte[] nkey = new byte[8];
			System.arraycopy(key, 0, nkey, 0, key.length > 8 ? 8 : key.length);
			//Des加密，base64转码
			String str = new BASE64Encoder().encode(DesHelper.encrypt(data, nkey));

			System.out.println(str);
			//url编码
			//str=URLEncoder.encode(str, "utf-8");

			//发送http请求
			String Url = "http://sh2.cshxsp.com/ensms.ashx";
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod(Url);
			post.setRequestHeader("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			NameValuePair UserId = new NameValuePair("UserId", "1");
			NameValuePair Text64 = new NameValuePair("Text64", str);
			post.setRequestBody(new NameValuePair[] { UserId, Text64 });
			int statu = client.executeMethod(post);
			System.out.println("statu=" + statu);
			//返回结果
			String result = post.getResponseBodyAsString();
			System.out.println("result=" + result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
