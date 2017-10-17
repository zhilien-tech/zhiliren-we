package com.yy.test;

public class HttpEnsms {
	/**
	 * http加密
	 * @param args
	 */
	public static void main(String[] args) {
		String cn = "您的验证码是:【变量】。如非本人操作，请忽略本短信。【优悦签】";
		System.out.println(cnToUnicode(cn));
	}

	private static String cnToUnicode(String cn) {
		cn = (cn == null ? "" : cn);
		String tmp;
		StringBuffer sb = new StringBuffer(1000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < cn.length(); i++) {
			c = cn.charAt(i);
			sb.append("\\u");
			j = (c >>> 8); //取出高8位 
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);
			j = (c & 0xFF); //取出低8位 
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);

		}
		return (new String(sb));
	}

}
