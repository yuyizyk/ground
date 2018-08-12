package cn.yuyizyk.ground.mapper.parser.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;

import cn.yuyizyk.ground.mapper.parser.PojoParser;
import cn.yuyizyk.ground.mapper.parser.imp.PojoMapParser.PojoMap;
import cn.yuyizyk.ground.model.pojo.addition.PojoType;
import cn.yuyizyk.ground.model.pojo.base.POJO;
import cn.yuyizyk.ground.model.pojo.base.SNFPOJO;

/**
 * snf pojo map 映射器
 * 
 * @author yuyi
 *
 */
public class SnfPojoMapParser implements PojoParser<SNFPOJO> {
	private Map<Class<? extends POJO>, SnfPojoMap> snfpojoMap = new HashMap<>();
	private Map<Class<? extends POJO>, Function<SNFPOJO, List<Entry<String, Object>>>> primaryKeys = new HashMap<>();

	@Override
	public void parser(Class<SNFPOJO> clz) {
		if (!SNFPOJO.class.isAssignableFrom(clz))
			return;
	}

	public List<Entry<String, Object>> getPrimaryKey(SNFPOJO snfpojo) {
		assert primaryKeys.containsKey(snfpojo.getClass());
		return primaryKeys.get(snfpojo.getClass()).apply(snfpojo);
	}

	static class SnfPojoMap extends PojoMapParser.PojoMap {
		protected Function<POJO, Set<Entry<String, Object>>> primaryKeyGetter;
	}
}
