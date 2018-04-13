package cn.yuyizyk.ground.mapper.parser;

public class EntityParse {

	public static final String toSQLFieldValue(Object val) {
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
