package cn.yuyizyk.ground.model.pojo.parser.imp;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import cn.yuyizyk.ground.core.bean.ApplicationInfo;
import cn.yuyizyk.ground.model.annotations.Table;
import cn.yuyizyk.ground.model.pojo.base.POJO;
import cn.yuyizyk.ground.model.pojo.parser.PojoDecoratorFactory;
import cn.yuyizyk.ground.model.pojo.parser.PojoType;

/**
 * pojo工厂
 * 
 * @author yuyi
 *
 */
public class PojoDecoratorFactoryImp implements PojoDecoratorFactory {
	private final static Logger log = LoggerFactory.getLogger(PojoDecoratorFactoryImp.class);
	private final static PojoDecoratorFactoryImp pojoFactory = new PojoDecoratorFactoryImp();

	@SuppressWarnings("unchecked")
	public void init() {
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

		ConfigurableApplicationContext context = (ConfigurableApplicationContext) ApplicationInfo
				.getApplicationContext();
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();
		ResourcePatternResolver rpr = new PathMatchingResourcePatternResolver(ApplicationInfo.getApplicationContext());
		try {
			Resource[] resources = rpr.getResources("classpath:cn/yuyizyk/ground/model/pojo/**/*.class");
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

	private PojoDecoratorFactoryImp() {
		init();
	}

	public static final PojoDecoratorFactoryImp newInstance() {
		return pojoFactory;
	}

}
