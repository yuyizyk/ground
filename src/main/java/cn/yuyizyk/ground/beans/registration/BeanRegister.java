package cn.yuyizyk.ground.beans.registration;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 
 * @author yuyi
 *
 */
@FunctionalInterface
public interface BeanRegister {
	void register(ConfigurableListableBeanFactory beanFactory, Class<?> aClass);
}
