package cn.yuyizyk.ground.mapper.provider;

import static cn.yuyizyk.ground.mapper.parser.EntityParse.toSQLFieldName;
import static cn.yuyizyk.ground.mapper.parser.EntityParse.toSQLFieldValue;
import static cn.yuyizyk.ground.mapper.provider.SqlForMySql.toPage;

import java.util.Map;
import java.util.Objects;

import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;

import cn.yuyizyk.ground.mapper.parser.imp.PojoMapParser;
import cn.yuyizyk.ground.model.pojo.base.POJO;

/**
 * 单表sql语句拼装; <br/>
 * 该表满足1NF
 * 
 * @author yuyi
 *
 */
public class MapperProvider {

	@Autowired
	protected PojoMapParser pojoMapParser;

	/**
	 * 返回指定的有序分页集合的sql语句
	 * 
	 * @return
	 */
	public <T extends POJO> String page(/* ProviderContext context, */ Class<T> cls, int page, int size,
			Map<String, Object> filterMap, Map<String, Object> sortMap) {
		assert page >= 1 & size >= 1;
		return toPage(list(/* context, */ cls, filterMap, sortMap), page, size);
	}

	/**
	 * 返回指定的有序集合的sql语句
	 * 
	 * @param tableName
	 * @param filterMap
	 * @param sortMap
	 * @return
	 */
	public <T extends POJO> String list(/* ProviderContext context */ Class<T> cls, Map<String, Object> filterMap,
			Map<String, Object> sortMap) {
		assert cls != null;
		return new SQL() {
			{
				SELECT("*");
				FROM(pojoMapParser.getTableName(cls));
				if (filterMap != null)
					filterMap.forEach((k, v) -> {
						if (Objects.nonNull(v)) {
							WHERE(new StringBuffer().append(toSQLFieldName(k)).append("=").append(toSQLFieldValue(v))
									.toString());
						}
					});
				if (sortMap != null)
					sortMap.forEach((k, v) -> {
						if (Objects.nonNull(v)) {
							ORDER_BY(new StringBuffer().append(toSQLFieldName(k)).append(" ").append(v).toString());
						}
					});
			}
		}.toString();
	}

	/**
	 * 总数
	 * 
	 * @param cls
	 * @param filterMap
	 * @return
	 */
	public <T extends POJO> String total(/* ProviderContext context, */ Class<T> cls, Map<String, Object> filterMap) {
		assert cls != null;
		return new SQL() {
			{
				SELECT(" count(1) ");
				FROM(pojoMapParser.getTableName(cls));
				if (filterMap != null)
					filterMap.forEach((k, v) -> {
						if (Objects.nonNull(v)) {
							WHERE(new StringBuffer().append(toSQLFieldName(k)).append("=").append(toSQLFieldValue(v))
									.toString());
						}
					});
			}
		}.toString();
	}

	/**
	 * 返回删除指定属性的对象的sql
	 * 
	 * @param cls
	 * @param filterMap
	 * @return
	 */
	public <T extends POJO> String delete(Class<T> cls, Map<String, Object> filterMap) {
		assert cls != null;
		return new SQL() {
			{
				DELETE_FROM("*");
				FROM(pojoMapParser.getTableName(cls));
				if (filterMap != null)
					filterMap.forEach((k, v) -> {
						if (Objects.nonNull(v)) {
							WHERE(new StringBuffer().append(toSQLFieldName(k)).append("=").append(toSQLFieldValue(v))
									.toString());
						}
					});
			}
		}.toString();
	}

	/**
	 * 返回依据指定属性的对象进行更新的sql
	 * 
	 * @param cls
	 * @param filterMap
	 * @return
	 */
	public <T extends POJO> String update(Class<T> cls, Map<String, Object> filterMap, Map<String, Object> updataMap) {
		assert cls != null;
		assert filterMap != null;
		assert updataMap != null;
		return new SQL() {
			{
				UPDATE(pojoMapParser.getTableName(cls));
				updataMap.forEach((k, v) -> {
					if (Objects.nonNull(v)) {
						SET(new StringBuffer().append(toSQLFieldName(k)).append("=").append(toSQLFieldValue(v))
								.toString());
					}
				});
				filterMap.forEach((k, v) -> {
					if (Objects.nonNull(v)) {
						WHERE(new StringBuffer().append(toSQLFieldName(k)).append("=").append(toSQLFieldValue(v))
								.toString());
					}
				});
			}
		}.toString();
	}

	/**
	 * 返回插入指定属性的对象的sql
	 * 
	 * @param cls
	 * @param filterMap
	 * @return
	 */
	public <T extends POJO> String insert(Class<T> cls, Map<String, Object> map) {
		assert cls != null;
		assert map != null;
		return new SQL() {
			{
				INSERT_INTO(pojoMapParser.getTableName(cls));
				map.forEach((k, v) -> {
					if (Objects.nonNull(v)) {
						VALUES(toSQLFieldName(k), toSQLFieldValue(v));
					}
				});
			}
		}.toString();
	}
}
