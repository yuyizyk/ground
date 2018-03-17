package cn.yuyizyk.ground.model.pojo;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

import cn.yuyizyk.ground.model.annotations.Table;

/**
 * Plain Old Java Object
 * 
 * @author yuyi
 *
 */
public abstract class POJO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 538421996313900437L;
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
		return null;
	}

	/**
	 * 转化为json字符串
	 * 
	 * @return
	 */
	public String toJSONString() {
		return "";
	}

	/**
	 * 将MAP转化为POJO
	 * 
	 * @param map
	 * @return
	 */
	public static final <T extends POJO> T byJSONString(Class<?> cls, String jsonStr) {
		return null;
	}

	/**
	 * 将MAP转化为POJO
	 * 
	 * @param map
	 * @return
	 */
	public static final <T extends POJO> T byMap(Map<String, Object> map) {
		return null;
	}

}
