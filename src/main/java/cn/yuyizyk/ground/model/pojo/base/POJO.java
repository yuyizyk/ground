package cn.yuyizyk.ground.model.pojo.base;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import cn.yuyizyk.ground.util.data.SerializationUtil;

/**
 * Plain Old Java Object
 * 
 * @author yuyi
 *
 */
public abstract class POJO implements Serializable {

	private transient static final long serialVersionUID = 1L;
	
	/**
	 * 获得当前实体在数据库中的表名
	 * 
	 * @return
	 * 
	 * @return
	 */
	public String tableName() {
		return PojoFactory.operation().tableName(this.getClass());
	}


	/**
	 * 将POJO转化为MAP对象
	 * 
	 * @return
	 */
	public Map<String, Object> toMap() {
		return SerializationUtil.toMapByGetter(this);
	}

	/**
	 * 将POJO转化为MAP对象
	 * 
	 * @return
	 */
	public Map<String, Object> toSqlMap() {
		return SerializationUtil.toMapByGetter(this, pd -> {
			if (POJO.class.isAssignableFrom(pd.getPropertyType()))
				return false;
			if (String.class.isAssignableFrom(pd.getPropertyType()))
				return true;
			if (Number.class.equals(pd.getPropertyType()))
				return true;
			return false;
		}, val -> {
			if (val == null)
				return false;
			return true;
		});
	}

	/**
	 * 转化为json字符串
	 * 
	 * @return
	 */
	public String toJsonStr() {
		return SerializationUtil.toJsonStr(this);
	}

	/**
	 * 将MAP转化为POJO
	 * 
	 * @param map
	 * @return
	 */
	public static final <T extends POJO> T formJsonStr(String jsonStr, Class<T> cls) {
		return SerializationUtil.toBeanByJson(jsonStr, cls);
	}

	/**
	 * 将MAP转化为POJO
	 * 
	 * @param map
	 * @return
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static final <T> T formMap(Class<T> cls, Map<String, Object> map)
			throws IllegalAccessException, InvocationTargetException, InstantiationException {
		return SerializationUtil.toBean(cls.newInstance(), map);
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
