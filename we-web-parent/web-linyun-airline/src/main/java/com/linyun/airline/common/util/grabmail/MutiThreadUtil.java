package com.linyun.airline.common.util.grabmail;

import java.util.concurrent.Callable;

import javax.mail.Message;

import lombok.Data;

import com.linyun.airline.admin.drawback.grabfile.timer.MailScrabService;

/**
 * 多线程工具类
 * 
 * Callable==Runnable接口; Callable中的泛型必须和实现的Call方法返回值一致
 * 
 * 如何为多线程的属性赋值:set方法;在当前的多线程类中和spring木有关系
 * 
 * @author 
 */
@Data
public class MutiThreadUtil implements Callable<Integer> {
	private Message[] messages;
	private Integer flag;
	private MailScrabService mailScrabService;

	@Override
	public Integer call() throws Exception {
		/* 查询所有的栏目,循环打印 */
		System.out.println(Thread.currentThread().getName() + Thread.currentThread().getPriority()
				+ "=================================");
		mailScrabService.parseMessage(flag, messages);
		return null;
	}

}
