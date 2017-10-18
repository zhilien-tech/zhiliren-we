/**
 * ExampleForFireFox.java
 * org.zxc.selenium
 * Copyright (c) 2017, 北京科技有限公司版权所有.
*/

package org.zxc.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * 启动火狐浏览器，测试selenium
 * <p>
 *
 * @author   朱晓川
 * @Date	 2017年8月21日 	 
 */
public class HelloFireFox {

	public static void main(String[] args) {
		//		System.setProperty("webdriver.gecko.driver", "C:\\Program Files (x86)\\Mozilla Firefox\\geckodriver.exe");

		// 创建一个 FireFox 的浏览器实例  
		WebDriver driver = new FirefoxDriver();

		// 让浏览器访问 Baidu  
		driver.get("http://www.baidu.com");
		// 用下面代码也可以实现  
		// driver.navigate().to("http://www.baidu.com");  

		// 获取 网页的 title  
		System.out.println("1 Page title is: " + driver.getTitle());

		// 通过 id 找到 input 的 DOM  
		WebElement element = driver.findElement(By.id("kw"));

		// 输入关键字  
		element.sendKeys("zTree");

		// 提交 input 所在的  form  
		element.submit();

		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 显示搜索结果页面的 title  
		System.out.println("2 Page title is: " + driver.getTitle());

		//关闭浏览器  
		driver.quit();
	}

}
