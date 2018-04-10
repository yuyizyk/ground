package cn.yuyizyk.test;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.yuyizyk.ground.core.WebApplicationInitializer;
import cn.yuyizyk.ground.core.bean.ApplicationInfo;
import cn.yuyizyk.ground.core.config.ApplicationDataConfig;
import cn.yuyizyk.ground.mapper.UserMapper;
import cn.yuyizyk.ground.model.annotations.Table;
import cn.yuyizyk.ground.model.pojo.UserInfo;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(classes = { WebApplicationInitializer.class, ApplicationDataConfig.class })
public class Demo {
	@Autowired
	private UserMapper userMapper;

	// @Test
	public void test1() {
		UserInfo ui = new UserInfo();
		System.out.println(userMapper.list(ui));

	}

	@Test
	public void test() {
		System.out.println("测试Spring整合Junit4进行单元测试");
		// UserInfo ui = new UserInfo();
		// System.out.println(userMapper.total(ui));
		// ui.setUserid("2");
		// System.out.println(userMapper.list(ui));
		// System.out.println(userMapper.list(ui));
		// System.out.println(JSONObject.toJSONString(ui.primaryKey()));
		// System.out.println(userMapper.insert1(ui));

		// ui.setUserid(null);
		// UserInfo ui1 = new UserInfo();
		// ui1.setUserid("121112");
		// System.out.println(userMapper.insert(ui1));
		// System.out.println(userMapper.page(ui, 1, 10));
		// ui.setPassword("123");
		// System.out.println(userMapper.update(ui1, ui));
		// System.out.println(userMapper.list(UserInfo.class, ui.toSqlMap()));
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

		// ConfigurableApplicationContext context = (ConfigurableApplicationContext)
		// ApplicationInfo
		// .getApplicationContext();
		// DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)
		// context.getBeanFactory();
		// ResourcePatternResolver rpr = new
		// PathMatchingResourcePatternResolver(ApplicationInfo.getApplicationContext());
		// try {
		// Resource[] resources =
		// rpr.getResources("classpath:cn/yuyizyk/ground/model/pojo/**/*.class");
		// Stream.of(resources).map(f -> {
		// try {
		// return f.getURI().getPath().split("(classes/)|(!/)")[1].replace("/",
		// ".").replace(".class", "");
		// } catch (IOException e) {
		// e.printStackTrace();
		// return null;
		// }
		// }).filter(Objects::nonNull).forEach(f -> {
		// try {
		// Class<?> aClass = Class.forName(f);
		// boolean match = Stream.of(aClass.getAnnotations()).anyMatch(c -> c instanceof
		// Table);
		// System.out.println(aClass);
		// if (match && !beanFactory.containsBean(aClass.getSimpleName())) {
		// System.out.println("------------" + aClass);
		// beanFactory.registerSingleton(aClass.getSimpleName(), aClass.newInstance());
		// }
		// } catch (ClassNotFoundException | IllegalStateException |
		// InstantiationException
		// | IllegalAccessException e) {
		// e.printStackTrace();
		// }
		// });
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// System.out.println(ApplicationInfo.getApplicationContext().getBean(UserInfo.class));
	}
}
