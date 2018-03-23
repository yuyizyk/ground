package cn.yuyizyk.ground.mapper.provider;

import static cn.yuyizyk.ground.mapper.provider.EntityInterceptor.toSQLFieldName;
import static cn.yuyizyk.ground.mapper.provider.EntityInterceptor.toSQLFieldValue;
import static cn.yuyizyk.ground.mapper.provider.SqlForMySql.toPage;
import java.util.Map;
import java.util.Objects;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import cn.yuyizyk.ground.model.pojo.POJO;

/**
 * 单表sql语句拼装; <br/>
 * 该表满足1NF
 * 
 * @author yuyi
 *
 */
public class MapperProvider {

	/**
	 * 返回指定的有序集合的sql语句
	 * 
	 * @param context
	 * @param tableName
	 * @param filterMap
	 * @param sortMap
	 * @return
	 */
	public String sortFilterList(ProviderContext context, String tableName, Map<String, Object> filterMap,
			Map<String, Object> sortMap) {
		return new SQL() {
			{
				SELECT("*");
				FROM(tableName);
				if (filterMap != null)
					filterMap.forEach((k, v) -> {
						if (Objects.isNull(v)) {
							WHERE(new StringBuffer().append(toSQLFieldName(k)).append("=").append(toSQLFieldValue(v))
									.toString());
						}
					});
				if (sortMap != null)
					sortMap.forEach((k, v) -> {
						if (Objects.isNull(v)) {
							ORDER_BY(new StringBuffer().append(toSQLFieldName(k)).append(" ").append(v).toString());
						}
					});
			}
		}.toString();
	}

	/**
	 * 返回指定的有序的分页集合的sql语句
	 * 
	 * @param context
	 * @param tableName
	 * @param filterMap
	 * @param sortMap
	 * @return
	 */
	public String sortFilterPage(ProviderContext context, String tableName, int page, int size,
			Map<String, Object> filterMap, Map<String, Object> sortMap) {
		return toPage(sortFilterList(context, tableName, filterMap, sortMap), page, size);
	}

	/**
	 * 根据指定的对象返回集合
	 * 
	 * @param context
	 * @param record
	 *            对象中非空属性为and filter
	 * @return
	 */
	public <T extends POJO> String list(ProviderContext context, T record) {
		return sortFilterList(context, T.getTableName(record), record.toMap(), null);
	}

	public <T extends POJO> String page(ProviderContext context, int page, int size, T record) {
		return sortFilterPage(context, T.getTableName(record), page, size, record.toMap(), null);
	}

	public String filterTotal(ProviderContext context, String tableName, Map<String, Object> filterMap) {
		return new SQL() {
			{
				SELECT(" count(1) ");
				FROM(tableName);
				if (filterMap != null)
					filterMap.forEach((k, v) -> {
						if (Objects.isNull(v)) {
							WHERE(new StringBuffer().append(toSQLFieldName(k)).append("=").append(toSQLFieldValue(v))
									.toString());
						}
					});
			}
		}.toString();
	}

	public <T extends POJO> String total(ProviderContext context, T record) {
		return filterTotal(context, T.getTableName(record), record.toMap());
	}

}
