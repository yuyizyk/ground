package cn.yuyizyk.ground.mapper.provider;

import static cn.yuyizyk.ground.mapper.interceptor.EntityInterceptor.toSQLFieldName;
import static cn.yuyizyk.ground.mapper.interceptor.EntityInterceptor.toSQLFieldValue;

import java.util.List;
import java.util.Map.Entry;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.jdbc.SQL;

import cn.yuyizyk.ground.model.pojo.SNFPOJO;

/**
 * 单表sql语句拼装; </>
 * 
 * @author yuyi
 *
 */
public class SNFMapperProvider extends MapperProvider {

	/**
	 * 通过ID获取
	 * 
	 * @param context
	 * @param record
	 * @return
	 */
	public <T extends SNFPOJO> String byId(ProviderContext context, String tableName,
			List<Entry<String, Object>> primaryKeys) {
		return new SQL() {
			{
				SELECT("*");
				FROM(tableName);
				primaryKeys.forEach((a) -> {
					WHERE(new StringBuffer().append(toSQLFieldName(a.getKey())).append("=")
							.append(toSQLFieldValue(a.getValue())).toString());
				});
			}
		}.toString();
	}

	/**
	 * 通过ID获取
	 * 
	 * @param context
	 * @param record
	 * @return
	 */
	public <T extends SNFPOJO> String byId(ProviderContext context, T record) {
		return byId(context, T.getTableName(record), T.getPrimaryKey(record));
	}
}