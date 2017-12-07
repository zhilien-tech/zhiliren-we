/**
 * SimpleTest.java
 * org.zxc.shiro.test
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.shiro.test;

/**
 * 自动装箱测试,自动装箱会调用包装类的valueOf方法.
 * <p>
 * **注意：==过程就是比较对象地址**
 * @author   朱晓川
 * @Date	 2017年11月2日 	 
 */
public class AutoPackingTest {

	public static void main(String[] args) {
		integerPack();
	}

	/**
	 * 注意:基本类型直接赋值给包装类等价于调用包装类的valueOf()方法。
	 * ==过程就是比较对象地址，这个地址是在自动装箱时赋值的。
	 * 类似于虚拟机针对字符串的优化一样，存在一个介于-128到127的缓存用来缓存Integer的值，
	 * 如果 Integer大于缓存中的 最小值（-128） 并且 小于 缓存中的最大值（127），
	 * 直接返回IntegerCache 中缓存的Integer对象。否则就新建一个Integer对象并返回。
	 * 
	 * 对于Long也是一样的
	 */
	private static void integerPack() {
		Integer a = 100;//基本类型直接赋值给包装类等价于调用包装类的valueOf()方法
		Integer b = 100;
		if (a == b) {
			System.out.println("a=b");
		} else {
			System.out.println("a!=b");
		}

		Integer c = 200;
		Integer d = 200;
		if (c == d) {
			System.out.println("c=d");
		} else {
			System.out.println("c!=d");
		}

		Integer e = new Integer(100);
		Integer f = 100;
		if (e == f) {
			System.out.println("e=f");
		} else {
			System.out.println("e!=f");
		}
	}

}
