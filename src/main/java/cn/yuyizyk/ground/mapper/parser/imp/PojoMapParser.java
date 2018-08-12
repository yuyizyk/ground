package cn.yuyizyk.ground.mapper.parser.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import cn.yuyizyk.ground.annotations.Table;
import cn.yuyizyk.ground.mapper.parser.PojoParser;
import cn.yuyizyk.ground.model.pojo.base.POJO;

/**
 * pojo map 映射器
 * 
 * @author yuyi
 *
 */
public class PojoMapParser implements PojoParser<POJO> {

	private static final Map<Class<? extends POJO>, PojoMap> pojoMaps = new HashMap<>();

	@Override
	public void parser(Class<POJO> clz) {
		PojoMap pojom = new PojoMap();
		pojom.clz = clz;
		pojom.tableName = clz.getAnnotation(Table.class).value();
		pojom.indexKeyGetter = pojo -> {
			return new TreeSet<>();
		};
		pojoMaps.put(clz, pojom);
	}

	public static boolean check(Class<? extends POJO> cls) {
		return pojoMaps.containsKey(cls);
	}

	public static final String getTableName(Class<? extends POJO> cls) {
		assert pojoMaps.containsKey(cls);
		return pojoMaps.get(cls).tableName;
	}

	public static Set<Entry<String, Object>> getIndexKey(POJO pojo) {
		assert pojoMaps.containsKey(pojo.getClass());
		return pojoMaps.get(pojo.getClass()).indexKeyGetter.apply(pojo);
	}

	static class PojoMap {
		protected Class<? extends POJO> clz;
		protected String tableName;
		protected Function<POJO, Set<Entry<String, Object>>> indexKeyGetter;
	}
}
