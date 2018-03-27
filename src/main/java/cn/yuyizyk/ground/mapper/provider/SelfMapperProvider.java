package cn.yuyizyk.ground.mapper.provider;

import static cn.yuyizyk.ground.mapper.provider.EntityProvider.toSQLFieldName;
import static cn.yuyizyk.ground.mapper.provider.EntityProvider.toSQLFieldValue;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.ibatis.jdbc.SQL;

import cn.yuyizyk.ground.mapper.entity.JoinMap;
import cn.yuyizyk.ground.model.entity.Entry;
import cn.yuyizyk.ground.model.pojo.base.POJO;

public class SelfMapperProvider {

	public <T extends POJO> String manyToMany(Class<? extends POJO> cls, List<JoinMap> joins,
			Map<String, Object> filter, Map<String, Object> sort) {
		return new SQL() {
			{
				String tableName = T.tableName(cls);
				SELECT(" " + tableName + ".* ").FROM(tableName);
				for (JoinMap join : joins) {
					String tableName1 = T.tableName(join.getCls());
					StringBuffer sb = new StringBuffer(tableName1).append(" on ");
					StringBuffer tem = new StringBuffer();
					join.getFilter().forEach((Entry<String, Object> e) -> {
						if (Objects.nonNull(e.getValue())) {
							if (tem.length() != 0)
								tem.append(" and ");
							tem.append(tableName1).append(".").append(toSQLFieldName(e.getKey())).append("=")
									.append(toSQLFieldValue(e.getValue()));
						}
					});
					sb.append(tem);
					JOIN(sb.toString());
				}
				if (filter != null)
					filter.forEach((k, v) -> {
						if (Objects.nonNull(v)) {
							WHERE(new StringBuffer().append(toSQLFieldName(k)).append("=").append(toSQLFieldValue(v))
									.toString());
						}
					});
				if (sort != null)
					sort.forEach((k, v) -> {
						if (Objects.nonNull(v)) {
							ORDER_BY(new StringBuffer().append(toSQLFieldName(k)).append(" ").append(v).toString());
						}
					});

			}
		}.toString();
	}
}
