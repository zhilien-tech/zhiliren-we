/**
 * ExampleJunit.java
 * org.zxc.selenium
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * selenium(三)执行js
 * @author   朱晓川
 * @Date	 2017年8月21日 	 
 */
public class ExecuteJs {

	static WebDriver driver;

	@BeforeClass
	public static void init() {
		System.out.println("init...");
		// 如果你的 FireFox 没有安装在默认目录，那么必须在程序中设置  
		//System.setProperty("webdriver.firefox.bin", "your path for firefox.exe");
		// 创建一个 FireFox 的浏览器实例  
		driver = new FirefoxDriver();
	}

	@Test
	public void test() {
		// 让浏览器访问 zTree Demo  
		driver.get("http://www.ztree.me/v3/demo/cn/core/standardData.html");

		// 等待 zTree 初始化完毕，Timeout 设置10秒  
		(new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				WebElement element = driver.findElement(By.id("treeDemo"));
				return element.findElement(By.tagName("a")) != null;
			}
		});

		System.out.println("start...javascript");

		//利用 window 实现全局对象  
		((JavascriptExecutor) driver).executeScript("window.zTreeObj = $.fn.zTree.getZTreeObj('treeDemo');");
		String name = (String) ((JavascriptExecutor) driver)
				.executeScript("return window.zTreeObj.getNodes()[0].name;");
		Long rootCount = (Long) ((JavascriptExecutor) driver)
				.executeScript("return window.zTreeObj.getNodes().length;");

		// 显示js 的结果  
		System.out.println("treeObj[0].name = " + name);
		System.out.println("root nodes count = " + rootCount);
	}

	@AfterClass
	public static void destory() {
		System.out.println("destory...");
		//关闭浏览器  
		driver.quit();
	}

}
