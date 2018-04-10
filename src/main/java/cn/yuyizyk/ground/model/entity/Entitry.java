package cn.yuyizyk.ground.model.entity;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import cn.yuyizyk.ground.util.data.SerializationUtil;

/**
 * 实体
 * 
 * @author yuyi
 *
 */
public abstract class Entitry<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 将 Entitry 转化为json字符串
	 * 
	 * @return
	 */
	public String toJsonStr() {
		return SerializationUtil.toJsonStr(this);
	}

	/**
	 * 将MAP转化为Entitry
	 * 
	 * @param map
	 * @return
	 */
	public static final <B extends Entitry<? extends Serializable>> B formJsonStr(String jsonStr, Class<B> cls) {
		return SerializationUtil.toBeanByJson(jsonStr, cls);
	}

	/**
	 * 将MAP转化为Entitry
	 * 
	 * @param map
	 * @return
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static final <T extends Entitry<? extends Serializable>> T formMap(Class<T> cls, Map<String, Object> map)
			throws IllegalAccessException, InvocationTargetException, InstantiationException {
		return SerializationUtil.toBean(cls.newInstance(), map);
	}

	/**
	 * 将Entitry转化为MAP对象
	 * 
	 * @return
	 */
	public Map<String, Object> toMap() {
		return SerializationUtil.toMapByGetter(this);
	}

	protected static final Gson consoleGson = SerializationUtil.getGsonBuilder()
			.excludeFieldsWithModifiers(java.lang.reflect.Modifier.STATIC).create();

	@Override
	public String toString() {
		if (Objects.isNull(this.getClass().getCanonicalName())) {
			return JSONObject.toJSONString(this);
		}
		return consoleGson.toJson(this);
	}

}
