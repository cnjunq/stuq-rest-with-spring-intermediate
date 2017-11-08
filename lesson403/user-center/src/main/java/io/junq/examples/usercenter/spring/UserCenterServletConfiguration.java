package io.junq.examples.usercenter.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import io.junq.examples.usercenter.security.UserCenterCorsFilter;

@Configuration
public class UserCenterServletConfiguration {

	public UserCenterServletConfiguration() {
		super();
	}
	
	@Bean
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}
	
	@Bean
	public ServletRegistrationBean dispatcherServletRegistration() {
		final ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet(), "/api/**");
		
		final Map<String, String> params = new HashMap<String, String>();
		params.put("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
		params.put("contextConfigLocation", "org.spring.sec2.spring");
		params.put("dispatchOptionsRequest", "true");
		registration.setInitParameters(params);
		
		registration.setLoadOnStartup(1);
		return registration;
	}
	
//	@Bean
//	public UserCenterCorsFilter userCenterCorsFilter() {
//		return new UserCenterCorsFilter();
//	}
	
}
