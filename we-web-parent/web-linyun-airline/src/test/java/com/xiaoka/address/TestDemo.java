package com.xiaoka.address;

import org.junit.Test;

public class TestDemo {
	class Inner {
		public void show() {
			System.out.println("aaaaaaaaaaa");
		}
	}

	@Test
	public void test() throws Throwable {
		assert 3 != 5;
		TestDemo s = new TestDemo();
		//Tomcat tomcat =  new Tomcat();
	}
}
