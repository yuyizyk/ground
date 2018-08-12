package cn.yuyizyk.ground.beans.registration;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import cn.yuyizyk.ground.annotations.NeedRegisters;
import cn.yuyizyk.ground.annotations.Scanning;
import cn.yuyizyk.ground.annotations.Table;
import cn.yuyizyk.ground.mapper.parser.PojoColumsParser;
import cn.yuyizyk.ground.mapper.parser.PojoParser;
import cn.yuyizyk.ground.model.pojo.base.POJO;

@NeedRegisters(PojoParserRegister.class)
@Scanning(resources = "classpath:cn/yuyizyk/ground/model/pojo/**/*.class")
public class PojoTypeRegister extends AbstractRegister {
	private final static Logger log = LoggerFactory.getLogger(PojoTypeRegister.class);

	private Set<PojoParser> pojoParser;
	private Set<PojoColumsParser> pojoColumsParser;

	@SuppressWarnings("unchecked")
	@Override
	public void scanning(ApplicationContext context, BeanRegister br) throws IOException {
		ConfigurableApplicationContext ccontext = (ConfigurableApplicationContext) context;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) ccontext.getBeanFactory();
		pojoParser = (Set<PojoParser>) beanFactory.getBean("PojoParserSet");
		pojoColumsParser = (Set<PojoColumsParser>) beanFactory.getBean("PojoColumsParserSet");
		super.scanning(context, br);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void register(ConfigurableListableBeanFactory beanFactory, Class<?> aClass) {
		boolean match = Stream.of(aClass.getAnnotations()).anyMatch(c -> c instanceof Table);
		if (match) {
			log.info("初始化 POJOTYPE [{}]", aClass.getSimpleName());
			parser((Class<? extends POJO>) aClass);
		}
	}

	private void parser(Class<? extends POJO> c) {
		pojoColumsParser.forEach(parser -> Stream.of(c.getDeclaredFields()).forEach(f -> parser.parser(c, f)));
		pojoParser.forEach(parser -> parser.parser(c));
	}
}
