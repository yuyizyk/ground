package cn.yuyizyk.ground.beans;

import java.io.IOException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import cn.yuyizyk.ground.annotations.NeedRegisters;
import cn.yuyizyk.ground.annotations.Scanning;
import cn.yuyizyk.ground.annotations.Table;
import cn.yuyizyk.ground.beans.registration.AbstractRegister;
import cn.yuyizyk.ground.exception.InitException;
import cn.yuyizyk.ground.util.cls.LoaderUtil;

/**
 * 动态注入bean
 * 
 * @author yuyi
 *
 */
public class DynaRegisterBean {
	private final static Logger log = LoggerFactory.getLogger(DynaRegisterBean.class);

	private final static String basePackage = "cn.yuyizyk.ground.beans";

	@SuppressWarnings("unchecked")
	public static void init(ApplicationContext context) {
		// Set<Class<?>> set =;
		TreeSet<Class<AbstractRegister>> set = new TreeSet<>((c1, c2) -> {
			NeedRegisters nrs1 = c1.getAnnotation(NeedRegisters.class);
			NeedRegisters nrs2 = c2.getAnnotation(NeedRegisters.class);
			int i = 0, j = 0;
			if (nrs1 != null) {
				i = Stream.of(nrs1.value()).anyMatch(c -> c2.equals(c)) ? 1 : 0;
			}
			if (nrs2 != null) {
				j = Stream.of(nrs2.value()).anyMatch(c -> c1.equals(c)) ? -1 : 0;
				if (i == 1 && j == -1) {
					log.error("异常");
					throw new InitException();
				}
			}
			return i + j;
		});
		LoaderUtil.scanning(basePackage, a -> {
			boolean match = AbstractRegister.class.isAssignableFrom(a)
					&& Stream.of(a.getAnnotations()).anyMatch(c -> c instanceof Scanning);
			if (match)
				set.add((Class<AbstractRegister>) a);
		});
		log.info("DynaRegisterBean init BeanRegisters total : {} ", set.size());
		set.forEach(c -> {
			try {
				c.newInstance().scanning(context);
			} catch (InstantiationException | IllegalAccessException | IOException e) {
				log.error("异常", e);
			}
		});
		log.info("DynaRegisterBean init success. ");
	}

	@Test
	public void test() {
		DynaRegisterBean.init(null);
	}

}
