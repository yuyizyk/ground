package cn.yuyizyk.ground.constant;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * web 应用信息
 * 
 * @author yuyi
 *
 */
public class AppConstantInfoInitializer implements ApplicationContextAware {

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		AppConfigInfo.AppConfigInfoInit.init(context);
	}
}
