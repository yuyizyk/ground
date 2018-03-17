package cn.yuyizyk.ground.mapper.interceptor;

public class EntityInterceptor {

	public static final String toSQLFieldValue(Object val) {
		return val.toString();
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
