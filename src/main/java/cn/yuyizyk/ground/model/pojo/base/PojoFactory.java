package cn.yuyizyk.ground.model.pojo.base;

import java.util.List;
import java.util.Map.Entry;

import cn.yuyizyk.ground.core.bean.ApplicationInfo;

public interface PojoFactory {
	/**
	 * 获得系统中，关于pojo信息的对象
	 * 
	 * @return
	 */
	public static PojoFactory operation() {
		return ApplicationInfo.getApplicationContext().getBean(PojoFactory.class);
	}

	public abstract String tableName(Class<? extends POJO> cls);

	public abstract <T extends SNFPOJO> List<Entry<String, Object>> primaryKey(T snfpojo);
}
