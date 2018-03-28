package cn.yuyizyk.ground.mapper.provider;

import static cn.yuyizyk.ground.mapper.parse.EntityParse.toSQLFieldName;
import static cn.yuyizyk.ground.mapper.parse.EntityParse.toSQLFieldValue;

import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import org.apache.ibatis.jdbc.SQL;

import cn.yuyizyk.ground.model.pojo.base.SNFPOJO;

/**
 * SNF单表sql语句拼装; </>
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
	public <T extends SNFPOJO> String byId(Class<T> snfpojo, List<Entry<String, Object>> idMap) {
		Objects.requireNonNull(idMap, " idMap is null ");
		return new SQL() {
			{
				SELECT("*");
				FROM(T.tableName(snfpojo));
				idMap.forEach(a -> {
					if (Objects.nonNull(a.getValue())) {
						WHERE(new StringBuffer().append(toSQLFieldName(a.getKey())).append("=")
								.append(toSQLFieldValue(a.getValue())).toString());
					}
				});
			}
		}.toString();
	}


}
