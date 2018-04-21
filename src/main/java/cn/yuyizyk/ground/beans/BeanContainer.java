package cn.yuyizyk.ground.beans;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * bean容器
 * 
 * @author yuyi
 *
 */
public class BeanContainer implements ApplicationContextAware {

	private static ApplicationContext context;

	public static final ApplicationContext getApplicationContext() {
		return context;
	}

	public static final <T> T getBean(Class<T> beanCls) {
		return context.getBean(beanCls);
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		context = arg0;
	}
}
