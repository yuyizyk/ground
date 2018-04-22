package cn.yuyizyk.ground.beans.registration;

import java.io.IOException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import cn.yuyizyk.ground.annotations.Scanning;
import cn.yuyizyk.ground.exception.InitException;
import cn.yuyizyk.ground.util.cls.LoaderUtil;

public abstract class AbstractRegister implements BeanRegister {
	private final static Logger log = LoggerFactory.getLogger(PojoTypeRegister.class);

	protected void scanning(String resourcesStr, ResourcePatternResolver rpr,
			ConfigurableListableBeanFactory beanFactory, BeanRegister br) throws IOException {
		Resource[] resources = rpr.getResources(resourcesStr);
		LoaderUtil.scanningResource(resources, f -> {
			try {
				br.register(beanFactory, Class.forName(f));
			} catch (ClassNotFoundException e) {
				log.error("", e);
				throw new InitException();
			}
		});
	}

	protected void scanning(String basePackage, ConfigurableListableBeanFactory beanFactory, BeanRegister br) {
		Set<Class<?>> set = LoaderUtil.scanning(basePackage);
		set.forEach(c -> br.register(beanFactory, c));
	}

	public void scanning(ApplicationContext context) throws IOException {
		scanning(context, this);
	}

	public void scanning(ApplicationContext context, BeanRegister br) throws IOException {
		log.info("scanning Register {} ", this.getClass().getSimpleName());
		Scanning scann = this.getClass().getAnnotation(Scanning.class);
		if (scann == null) {
			log.error("scanning Register {} error : not is Scanning", this.getClass().getSimpleName());
			throw new InitException();
		}
		ConfigurableApplicationContext ccontext = (ConfigurableApplicationContext) context;
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) ccontext.getBeanFactory();
		ResourcePatternResolver rpr = new PathMatchingResourcePatternResolver(context);

		for (String bp : scann.basePackages())
			scanning(bp, beanFactory, br);
		for (String resource : scann.resources())
			scanning(resource, rpr, beanFactory, br);

	}
}
