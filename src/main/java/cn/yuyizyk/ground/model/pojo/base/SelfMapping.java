package cn.yuyizyk.ground.model.pojo.base;

import cn.yuyizyk.ground.core.bean.ApplicationInfo;
import cn.yuyizyk.ground.mapper.SelfMapper;

/**
 * 自映射
 * 
 * @author yuyi
 *
 */
public interface SelfMapping {
	public void autoMapping(SelfMapper mapper);

	default void map() {
		autoMapping(ApplicationInfo.getApplicationContext().getBean(SelfMapper.class));
	}

}
