package cn.yuyizyk.ground.mapper.provider;

import static cn.yuyizyk.ground.mapper.interceptor.EntityInterceptor.toSQLFieldName;
import static cn.yuyizyk.ground.mapper.interceptor.EntityInterceptor.toSQLFieldValue;

import java.util.Map;
import java.util.Objects;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import cn.yuyizyk.ground.model.pojo.POJO;

/**
 * 单表sql语句拼装; </>该表满足1NF
 * 
 * @author yuyi
 *
 */
public class MapperProvider {

	@SuppressWarnings("unchecked")
	public String sortListByFilter(ProviderContext context, String tableName, Map<String, Object>... argMap) {
		return new SQL() {
			{
				SELECT("*");
				FROM(tableName);
				if (argMap.length >= 1)
					argMap[0].forEach((k, v) -> {
						if (Objects.isNull(v)) {
							WHERE(new StringBuffer().append(toSQLFieldName(k)).append("=").append(toSQLFieldValue(v))
									.toString());
						}
					});
				if (argMap.length >= 2)
					argMap[2].forEach((k, v) -> {
						if (Objects.isNull(v)) {
							ORDER_BY(new StringBuffer().append(toSQLFieldName(k)).append(" ").append(v).toString());
						}
					});
			}
		}.toString();
	}

	@SuppressWarnings("unchecked")
	public String sortPageByFilter(ProviderContext context, String tableName, int page, int size,
			Map<String, Object>... argMap) {
		return new SQL() {
			{
				SELECT("*");
				FROM(sortListByFilter(context, tableName, argMap));
				WHERE("rownum>=" + page * size);
				WHERE("rownum<" + (page + 1) * size);
			}
		}.toString();
	}

	@SuppressWarnings("unchecked")
	public String pageByFilter(ProviderContext context, String tableName, int page, int size,
			Map<String, Object> recordMap) {
		return sortPageByFilter(context, tableName, page, size, recordMap);
	}

	@SuppressWarnings("unchecked")
	public String listByFilter(ProviderContext context, String tableName, Map<String, Object> recordMap) {
		return sortListByFilter(context, tableName, recordMap);
	}

	public <T extends POJO> String listByFilter(ProviderContext context, T record) {
		return listByFilter(context, T.getTableName(record), record.toMap());
	}

	public <T extends POJO> String pageByFilter(ProviderContext context, int page, int size, T record) {
		return new SQL() {
			{
				SELECT("*");
				FROM(listByFilter(context, T.getTableName(record), record.toMap()));
				WHERE("rownum>=" + page * size);
				WHERE("rownum<" + (page + 1) * size);
			}
		}.toString();
	}

}
