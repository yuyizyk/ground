package cn.yuyizyk.ground.core.bean;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import cn.yuyizyk.ground.mapper.parser.PojoType;
import cn.yuyizyk.ground.model.annotations.Table;
import cn.yuyizyk.ground.model.pojo.base.POJO;

/**
 * 资源注册器
 * 
 * @author yuyi
 *
 */
@PropertySource("properties/init.properties")
public final class ApplicationRegistered {
	private final static Logger log = LoggerFactory.getLogger(ApplicationRegistered.class);
	@Value(value = "interceptors")
	private String interceptors;

	private ApplicationRegistered() {
	}

	public static final void registered(ApplicationContext arg0) {
		initPojoType(arg0);
	}

	@Value("pojo_resources")
	private String pojoResources;

	private static void initPojoType(ApplicationContext arg0) {
		// Set<Class<?>> set =
		// LoaderUtil.getClzFromPkg("cn.yuyizyk.ground.model.pojo.parser");
		/*
		 * set.forEach((c) -> { if (PojoParser.class.isAssignableFrom(c) &&
		 * !Modifier.isAbstract(c.getModifiers())) { registered((Class<PojoParser>) c);
		 * } }); set = LoaderUtil.getClzFromPkg("cn.yuyizyk.ground.model.pojo");
		 * set.forEach((c) -> { if (POJO.class.isAssignableFrom(c) &&
		 * !Modifier.isAbstract(c.getModifiers())) { // 接收实体类 new PojoType((Class<POJO>)
		 * c); } });
		 */
		// DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)
		// ApplicationInfo.getApplicationContext().getParentBeanFactory();
		// 用于存储Spring容器管理之外的Bean，Spring内部很少使用，应用有些情况通过1拿到的beanFactory是null，特别是在应用第三方框架时，dubbo中遇到过。
		// DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)
		// ApplicationInfo.getApplicationContext()
		// .getAutowireCapableBeanFactory();
		// BeanDefinition beanDefinition = new GenericBeanDefinition();
		// beanDefinition.setBeanClassName(obj.getClass().getName());
		// beanFactory.registerBeanDefinition(obj.getClass().getName(), beanDefinition);
		// beanFactory.registerSingleton(beanName, singletonObject);
		// System.out.println(applicationContext.getBean(SQLManager.class));
		ConfigurableApplicationContext context = (ConfigurableApplicationContext) arg0;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
		ResourcePatternResolver rpr = new PathMatchingResourcePatternResolver(arg0);
		try {
			Resource[] resources = rpr.getResources("");
			Stream.of(resources).map(f -> {
				try {
					return f.getURI().getPath().split("(classes/)|(!/)")[1].replace("/", ".").replace(".class", "");
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}).filter(Objects::nonNull).forEach(f -> {
				try {
					Class<?> aClass = Class.forName(f);
					boolean match = Stream.of(aClass.getAnnotations()).anyMatch(c -> c instanceof Table);
					if (match && !beanFactory.containsBean(aClass.getSimpleName())) {
						log.info("初始化POJOTYPE[{}]", aClass.getSimpleName());
						beanFactory.registerSingleton(aClass.getSimpleName(),
								new PojoType((Class<? extends POJO>) aClass));
					}
				} catch (ClassNotFoundException | IllegalStateException e) {
					log.error("初始化异常", e);
				}
			});
		} catch (IOException e) {
			log.error("初始化异常", e);
		}

	}

}
