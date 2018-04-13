package cn.yuyizyk.ground.mapper.parser.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;

import cn.yuyizyk.ground.mapper.parser.PojoParser;
import cn.yuyizyk.ground.mapper.parser.PojoType;
import cn.yuyizyk.ground.model.pojo.base.POJO;
import cn.yuyizyk.ground.model.pojo.base.SNFPOJO;

/**
 * snf pojo map 映射器
 * 
 * @author yuyi
 *
 */
public class SnfPojoMapParser implements PojoParser {

	private Map<Class<? extends POJO>, Function<SNFPOJO, List<Entry<String, Object>>>> primaryKeys = new HashMap<>();

	@Autowired
	PojoMapParser pojoMapParser;

	@Override
	public void parser(PojoType pojo) {
		if (!SNFPOJO.class.isAssignableFrom(pojo.getClass()))
			return;
	}

	public List<Entry<String, Object>> getPrimaryKey(SNFPOJO snfpojo) {
		assert primaryKeys.containsKey(snfpojo.getClass());
		return primaryKeys.get(snfpojo.getClass()).apply(snfpojo);
	}

}
