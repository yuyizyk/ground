package cn.yuyizyk.ground.core.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 系统内部信息
 * 
 * @author yuyi
 *
 */
public class ApplicationInfo implements ApplicationContextAware {
	private static final ApplicationInfo applicationInfo = new ApplicationInfo();
	private ApplicationContext applicationContext;

	public static final ApplicationContext getApplicationContext() {
		return applicationInfo.applicationContext;
	}

	private ApplicationInfo() {
	}

	public static final ApplicationInfo newInstance() {
		return applicationInfo;
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		applicationContext = arg0;
	}

}
