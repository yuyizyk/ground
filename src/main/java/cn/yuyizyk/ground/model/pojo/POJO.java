package cn.yuyizyk.ground.model.pojo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;

import cn.yuyizyk.ground.model.annotations.Table;
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
	 */
	public static final String getTableName(POJO pojo) {
		final Table table = pojo.getClass().getAnnotation(Table.class);
		Objects.requireNonNull(table, "POJO not comply Table Annotation");
		return table.value();
	}

	/**
	 * 将POJO转化为MAP对象
	 * 
	 * @return
	 */
	public Map<String, Object> toMap() {
		return SerializationUtil.toMap(this);
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

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + SerializationUtil.getGsonBuilder()
				.excludeFieldsWithModifiers(java.lang.reflect.Modifier.STATIC).create().toJson(this);
	}
}
