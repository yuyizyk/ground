package cn.yuyizyk.ground.beans.registration;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.asm.IProgramElement.Modifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import cn.yuyizyk.ground.annotations.Scanning;
import cn.yuyizyk.ground.exception.InitException;
import cn.yuyizyk.ground.mapper.parser.PojoColumsParser;
import cn.yuyizyk.ground.mapper.parser.PojoParser;

@Scanning(resources = "classpath:cn/yuyizyk/ground/mapper/parser/**/*.class")
public class PojoParserRegister extends AbstractRegister {
	private final static Logger log = LoggerFactory.getLogger(PojoParserRegister.class);

	private Set<PojoParser> pojoParser = new HashSet<>();
	private Set<PojoColumsParser> pojoColumsParser = new HashSet<>();

	@Override
	public void scanning(ApplicationContext context, BeanRegister br) throws IOException {
		ConfigurableApplicationContext ccontext = (ConfigurableApplicationContext) context;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) ccontext.getBeanFactory();
		if (!beanFactory.containsBean("PojoParserSet")) {
			log.info("注册 PojoParserSet");
			beanFactory.registerSingleton("PojoParserSet", pojoParser);
		}
		if (!beanFactory.containsBean("PojoColumsParserSet")) {
			log.info("注册 PojoColumsParserSet");
			beanFactory.registerSingleton("PojoColumsParserSet", pojoColumsParser);
		}
		super.scanning(context, br);
	}

	@Override
	public void register(ConfigurableListableBeanFactory beanFactory, Class<?> aClass) {
		boolean match = PojoParser.class.isAssignableFrom(aClass)
				&& (0 == (aClass.getModifiers() & Modifiers.ABSTRACT.getBit()) && !aClass.isInterface());
		Object obj = null;
		if (match) {
			log.info("注册 PojoParser [{}]", aClass.getSimpleName());
			try {
				obj = obj == null ? aClass.newInstance() : obj;
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("初始化异常", e);
				throw new InitException();
			}
			if (!beanFactory.containsBean(aClass.getSimpleName())) {
				beanFactory.registerSingleton(aClass.getSimpleName(), obj);
			}
			pojoParser.add((PojoParser) obj);
		}
		match = PojoColumsParser.class.isAssignableFrom(aClass)
				&& (0 == (aClass.getModifiers() & Modifiers.ABSTRACT.getBit()) && !aClass.isInterface());
		if (match) {
			log.info("注册 PojoColumsParser [{}]", aClass.getSimpleName());
			try {
				obj = obj == null ? aClass.newInstance() : obj;
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("初始化异常", e);
				throw new InitException();
			}
			if (!beanFactory.containsBean(aClass.getSimpleName())) {
				beanFactory.registerSingleton(aClass.getSimpleName(), obj);
			}
			pojoColumsParser.add((PojoColumsParser) obj);
		}
	}
}
