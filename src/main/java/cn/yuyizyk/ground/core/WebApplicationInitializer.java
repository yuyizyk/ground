package cn.yuyizyk.ground.core;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import cn.yuyizyk.ground.core.config.ApplicationConfig;
import cn.yuyizyk.ground.core.config.WebConfig;

/**
 * 
 * @author yuyi
 *
 */

public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { ApplicationConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { WebConfig.class };
	}

	/**
	 * identifies one or more paths that DispatcherServlet will be mapped to.<br>
	 * In this case, it’s mapped to /, indicating that it will be the application’s
	 * default servlet.<br>
	 * It will handle all requests coming into the application.
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	/**
	 * 注册过滤器，映射路径与DispatcherServlet一致，路径不一致的过滤器需要注册到另外的WebApplicationInitializer中
	 */
	@Override
	protected Filter[] getServletFilters() {
		// 字符过滤器
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		characterEncodingFilter.setBeanName("characterEncodingFilter");

		DelegatingFilterProxy securityFilterChain = new DelegatingFilterProxy();
		securityFilterChain.setBeanName("shiroFilter");
		securityFilterChain.setTargetBeanName("shiroFilter");
		securityFilterChain.setTargetFilterLifecycle(true);

		return new Filter[] { characterEncodingFilter, securityFilterChain };
	}

}
