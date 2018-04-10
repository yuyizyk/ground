package cn.yuyizyk.ground.model.pojo.parser.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import cn.yuyizyk.ground.model.pojo.base.TNFPOJO;
import cn.yuyizyk.ground.model.pojo.parser.PojoParser;
import cn.yuyizyk.ground.model.pojo.parser.PojoType;

/**
 * Tnf pojo map 映射器
 * 
 * @author yuyi
 *
 */
public class TnfPojoMapParser implements PojoParser {

	private Map<Class<? extends TNFPOJO>, Function<TNFPOJO, Entry<String, Object>>> primaryKeys = new HashMap<>();

	@Override
	public void parser(PojoType pojo) {
		if (!TNFPOJO.class.isAssignableFrom(pojo.getClass()))
			return;
	}

	public Entry<String, Object> getPrimaryKey(TNFPOJO tnfpojo) {
		assert primaryKeys.containsKey(tnfpojo.getClass());
		return primaryKeys.get(tnfpojo.getClass()).apply(tnfpojo);
	}

}
