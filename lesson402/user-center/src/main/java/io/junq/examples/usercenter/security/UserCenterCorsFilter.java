package io.junq.examples.usercenter.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class UserCenterCorsFilter implements Filter {

	public UserCenterCorsFilter() {
		super();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		httpResponse.setHeader("Access-Control-Allow-Origin", "*"); //表明允许任意源进行请求

		httpResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE"); // 表明允许请求方法
		httpResponse.setHeader("Access-Control-Max-Age", "3600"); // 表明允许缓存时长
		// 表明允许请求的头部参数
		httpResponse.setHeader("Access-Control-Allow-Headers", "X-Requested-With, WWW-Authenticate, Authorization, Origin, Content-Type, Version");
		// 表明允许浏览器存取的响应头部参数
		httpResponse.setHeader("Access-Control-Expose-Headers", "X-Requested-With, WWW-Authenticate, Authorization, Origin, Content-Type");

        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (httpRequest.getMethod() != "OPTIONS") {
            chain.doFilter(request, response);
        } else {
            //
        }
		
	}

	@Override
	public void destroy() {
		
	}
	
}
