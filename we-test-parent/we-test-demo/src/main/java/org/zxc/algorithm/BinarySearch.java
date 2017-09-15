/**
 * BinarySearch.java
 * org.zxc.algorithm
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.algorithm;

import java.util.Arrays;

/**
 * 二分查找
 * <p>
 * 演示编程语言基础:
 * 1，数据类型:一组数据和对其所能进行的操作的集合
 * 2，语句
 *    声明，赋值，条件，循环，调用，返回
 * 3，数组
 * 4，静态方法
 * 5，字符串
 * 6，数据抽象
 * 
 * @author   朱晓川
 * @Date	 2017年9月6日 	 
 */
public class BinarySearch {

	/**非递归实现*/
	public static int rank(int key, int[] a) {
		int lo = 0;//左端点
		int hi = a.length - 1;//右端点
		while (lo <= hi) {
			int mid = lo + ((hi - lo) >> 1);
			if (key < a[mid])
				//移动右端点
				hi = mid - 1;
			else if (key > a[mid])
				//移动左端点
				lo = mid + 1;
			else
				return mid;
		}
		return -1;
	}

	public static int rank2(int key, int[] a) {
		return recursiveRank(key, a, 0, a.length - 1);
	}

	/**递归*/
	public static int recursiveRank(int key, int[] a, int lo, int hi) { //如果key存在于a[]中，它的索引不会小于lo且不会大于hi
		if (lo > hi)
			return -1;
		int mid = lo + (hi - lo) / 2;
		if (key < a[mid])
			return recursiveRank(key, a, lo, mid - 1);
		else if (key > a[mid])
			return recursiveRank(key, a, mid + 1, hi);
		else
			return mid;
	}

	public static void main(String[] args) {
		int[] whitelist = new int[] { 1, 9, 0, 5, 7, 3, 2, 5, 4 };
		Arrays.sort(whitelist);
		int key = 9;
		System.out.println(rank2(key, whitelist));
	}

}
