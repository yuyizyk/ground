package cn.yuyizyk.ground.application.cache;

import cn.yuyizyk.ground.model.pojo.UserInfo;

public interface HttpSession {

	/**
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	HttpSession put(String key, Object val);

	/**
	 * 
	 * @param key
	 * @return
	 */
	<T> T get(String key);

	/**
	 * 获得当前用户
	 * 
	 * @return
	 */
	UserInfo getCurrentUser();
}
