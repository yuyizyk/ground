package cn.yuyizyk.ground.model.pojo.parser;

import cn.yuyizyk.ground.core.bean.ApplicationInfo;

public interface PojoDecoratorFactory {

	/**
	 * 获得系统中，关于pojo信息的对象
	 * 
	 * @return
	 */
	public static PojoDecoratorFactory operation() {
		return ApplicationInfo.getApplicationContext().getBean(PojoDecoratorFactory.class);
	}

	public default <T extends PojoParser> T getPojoParser(Class<T> cls) {
		return ApplicationInfo.getApplicationContext().getBean(cls);
	}

	public default <T extends PojoColumsParser> T getPojoColumsParser(Class<T> cls) {
		return ApplicationInfo.getApplicationContext().getBean(cls);
	}
}
