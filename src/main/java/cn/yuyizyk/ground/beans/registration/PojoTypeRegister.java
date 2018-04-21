package cn.yuyizyk.ground.beans.registration;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import cn.yuyizyk.ground.annotations.Scanning;
import cn.yuyizyk.ground.annotations.Table;
import cn.yuyizyk.ground.model.pojo.addition.PojoType;
import cn.yuyizyk.ground.model.pojo.base.POJO;

@Scanning(resources = "classpath:cn/yuyizyk/ground/model/pojo/**/*.class")
public class PojoTypeRegister implements BeanScanningRegister {
	private final static Logger log = LoggerFactory.getLogger(PojoTypeRegister.class);

	@Override
	public void register(ConfigurableListableBeanFactory beanFactory, Class<?> aClass) {
		boolean match = Stream.of(aClass.getAnnotations()).anyMatch(c -> c instanceof Table);
		if (match && !beanFactory.containsBean(aClass.getSimpleName())) {
			log.info("初始化POJOTYPE[{}]", aClass.getSimpleName());
			beanFactory.registerSingleton(aClass.getSimpleName(), new PojoType((Class<POJO>) aClass));
		}
	}
}
