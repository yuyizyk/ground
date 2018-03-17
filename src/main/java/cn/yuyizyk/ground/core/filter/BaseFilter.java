package cn.yuyizyk.ground.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*@WebFilter(urlPatterns = "/*", initParams = { @WebInitParam(name = "errorExts", value = "null,undefined"),
		@WebInitParam(name = "ignore", value = "/api,/page"),
		@WebInitParam(name = "ignoreExts", value = "ico,css,js,jpg,jpeg,gif,png,bmp,doc,xls,pdf,ppt,pptx,txt,html,htm,zip,rar,jsp,json,apk,swf,rtf,docx,xlsx") })
*/public class BaseFilter implements Filter{
	private final static Logger log = LoggerFactory.getLogger(BaseFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	
}
