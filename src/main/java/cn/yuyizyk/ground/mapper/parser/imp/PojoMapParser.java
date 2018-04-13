package cn.yuyizyk.ground.mapper.parser.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import cn.yuyizyk.ground.mapper.parser.PojoParser;
import cn.yuyizyk.ground.mapper.parser.PojoType;
import cn.yuyizyk.ground.model.pojo.base.POJO;

/**
 * pojo map 映射器
 * 
 * @author yuyi
 *
 */
public class PojoMapParser implements PojoParser {

	private Map<Class<? extends POJO>, PojoType> pojoMap = new HashMap<>();
	private Map<Class<? extends POJO>, Function<POJO, List<Entry<String, Object>>>> indexKeys = new HashMap<>();

	@Override
	public void parser(PojoType pojo) {
		pojoMap.put(pojo.getType(), pojo);
	}

	public boolean check(Class<? extends POJO> cls) {
		return pojoMap.containsKey(cls);
	}

	public PojoType getPojoType(Class<? extends POJO> cls) {
		return pojoMap.get(cls);
	}

	public String getTableName(Class<? extends POJO> cls) {
		assert pojoMap.containsKey(cls);
		return pojoMap.get(cls).getTableInfo().value();
	}

	public List<Entry<String, Object>> getIndexKey(POJO pojo) {
		assert indexKeys.containsKey(pojo.getClass());
		return indexKeys.get(pojo.getClass()).apply(pojo);
	}
	
}
