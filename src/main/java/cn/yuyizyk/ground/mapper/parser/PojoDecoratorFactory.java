package cn.yuyizyk.ground.mapper.parser;

import cn.yuyizyk.ground.beans.BeanContainer;

public interface PojoDecoratorFactory {

	/**
	 * 获得系统中，关于pojo信息的对象
	 * 
	 * @return
	 */
	public static PojoDecoratorFactory operation() {
		return BeanContainer.getBean(PojoDecoratorFactory.class);
	}

	public default <T extends PojoParser> T getPojoParser(Class<T> cls) {
		return BeanContainer.getBean(cls);
	}

	public default <T extends PojoColumsParser> T getPojoColumsParser(Class<T> cls) {
		return BeanContainer.getBean(cls);
	}
}
