/**
 * TestPnr.java
 * com.xiaoka.test
 * Copyright (c) 2017, 北京科技有限公司版权所有.
 */

package com.xiaoka.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO(这里用一句话描述这个类的作用)
 * <p>
 * TODO(这里描述这个类补充说明 – 可选)
 *
 * @author   彭辉
 * @Date	 2017年2月16日 	 
 */
public class TestEtem {
	public static void main(String[] args) throws Exception {
		String input = "avh/aklsyd/28feb/ek\n" + " 28FEB17(TUE) AKLSYD VIA EK\n"
				+ "1- *EK5050 DS# JL CL IL OL YA EA RA WA MA BA  AKLSYD 0600  0740 73H 0 M E\n"
				+ ">   QF140      UL KL HL QL LL TC VC XC                          I  1   3:40\n"
				+ "2  *EK5142 DS# JL CL IL OL YA EA RA WL ML BL  AKLSYD 0735  0915 73H 0 M  E\n"
				+ ">   QF142      UC KC HL QC LC TC VC XC                          I  1   3:40\n"
				+ "3  *EK5114 DS# JL CL IL OL YA EA RA WA MA BA  AKLSYD 0835  1015 73H 0 M  E\n"
				+ ">   QF144      UC KC HL QC LC TC VC XC                          I  1   3:40\n"
				+ "4  *EK5056 DS# JA CL IL OL YA EA RA WA ML BL  AKLSYD 1400  1540 73H 0 M  E\n"
				+ ">   QF146      UL KL HL QC LC TC VC XC                          I  1   3:40\n"
				+ "5  *EK419  DS# FX AX JX CX IX OX PX YX EX RX  AKLSYD 1630  1755 388 0^M  E\n"
				+ ">              WX MX BX UX KX HA QA LA TC VC XC                 I  1   3:25\n"
				+ "6  *EK7525 DS# YA EA IL RA WA BA UA KA HA QA  AKLCHC 0640  0805 320 0    E\n"
				+ ">   JQ225      LA TA VA XA                                      D  --  1:25\n"
				+ "    EK413  DS# FA AA JX CX IX OA PX YX EX RX     SYD 1845  2005 388 0^M  E\n"
				+ ">              WX MX BX UA KX HC QX LC TC VX XC                 --1   15:25\n"
				+ "7+ *EK7527 DS# YL EL RL WL ML BL UL KL HL QL  AKLCHC 0820  0945 320 0    E\n"
				+ ">   JQ229      LL TL VL XL                                      D  --  1:25\n"
				+ "    EK413  DS# FA AA JX CX IX OA PX YX EX RX     SYD 1845  2005 388 0^M  E\n"
				+ ">              WX MX BX UA KX HC QX LC TC VX XC                 --1   13:45\n";
		//(?s)表示开启跨行匹配，\\d{1}一位数字，[A-Za-z]{2}两位字母，/斜线，\\s空白字符,.+任意字符出现1到多次，?非贪婪模式，最后以\n换行结束
		String regex = "(?s)((\\d{1}.{2}[*])|(\\s{4}))[A-Za-z]{2}\\d+\\s.+?\\d\n";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(input);
		while (m.find()) {
			System.out.println(m.group() + "|||");
		}
		/*String regex1 = "(?s)\\s{4}[A-Za-z]{2}\\d+\\s.+?\\d\n";
		Pattern pattern1 = Pattern.compile(regex1);
		Matcher m1 = pattern1.matcher(input);
		while (m1.find()) {
			System.out.println(m1.group() + "|||");
		}*/
	}
}
