package cn.yuyizyk.ground.application.cache.imp;

import cn.yuyizyk.ground.application.cache.CacheEngine;
import cn.yuyizyk.ground.application.cache.HttpSession;
import cn.yuyizyk.ground.model.pojo.UserInfo;

public class HttpSessionImp implements HttpSession {
	private static CacheEngine cacheEngine = CacheEngine.getInstance();

	@Override
	public HttpSession put(String key, Object val) {
		return null;
	}

	@Override
	public <T> T get(String key) {
		return null;
	}

	@Override
	public UserInfo getCurrentUser() {
		return null;
	}

}
