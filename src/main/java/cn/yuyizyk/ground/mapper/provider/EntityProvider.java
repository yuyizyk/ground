package cn.yuyizyk.ground.mapper.provider;

import cn.yuyizyk.ground.mapper.entity.JoinField;

public class EntityProvider {

	public static final String toSQLFieldValue(Object val) {
		if (val instanceof JoinField)
			return val.toString();
		return String.format(" '%s' ", val != null ? val.toString() : "");
	}

	/**
	 * 将实体字段转化为数据库字段
	 * 
	 * @param key
	 * @return
	 */
	public static final String toSQLFieldName(String fieldName) {
		return fieldName;
	}

}
