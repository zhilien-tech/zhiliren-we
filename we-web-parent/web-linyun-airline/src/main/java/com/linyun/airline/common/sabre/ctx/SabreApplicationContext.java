package com.linyun.airline.common.sabre.ctx;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sabre.api.sacs.rest.RestApplicationConfiguration;

/**
 * @author   朱晓川
 * @Date	 2017年6月16日 	 
 */
public class SabreApplicationContext {

	public static final ApplicationContext context = new AnnotationConfigApplicationContext(
			new Class<?>[] { RestApplicationConfiguration.class });

}
