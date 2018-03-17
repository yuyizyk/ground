package cn.yuyizyk.ground.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * 
 * @author yuyi
 *
 */

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "cn.yuyi.ground" }, useDefaultFilters = true)
public class WebConfig extends WebMvcConfigurerAdapter {
	private final static Logger log = LoggerFactory.getLogger(WebConfig.class);

	@Bean
	public ViewResolver viewResolver() {
		FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
		// viewResolver.setSuffix(".ftl");
		viewResolver.setViewClass(org.springframework.web.servlet.view.freemarker.FreeMarkerView.class);
		viewResolver.setOrder(0);
		viewResolver.setExposeContextBeansAsAttributes(true);
		viewResolver.setExposeSessionAttributes(true);
		viewResolver.setContentType("text/html;charset=UTF-8");
		return viewResolver;
	}

	@Bean
	public FreeMarkerConfigurer freemarkerConfig() {
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = loader.getResourceAsStream("properties/freemarker.properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
			freeMarkerConfigurer.setFreemarkerSettings(p);

		} catch (IOException e) {
			e.printStackTrace();
		}
		freeMarkerConfigurer.setTemplateLoaderPath("/template/");
		return freeMarkerConfigurer;
	}

	/**
	 * 静态文件 Spring ResourceHandler
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/*").addResourceLocations("/static/");
		// registry.addResourceHandler("/common/*").addResourceLocations("/common/");
	}

	/**
	 * 默认 Servlet 处理转发对静态资源的 Request 到 Servlet 容器的默认Servlet
	 */
	/*
	 * @Override public void
	 * configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	 * configurer.enable(); }
	 */

}
