package cn.yuyizyk.ground.mapper.parser.imp;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import cn.yuyizyk.ground.mapper.parser.PojoColumsParser;
import cn.yuyizyk.ground.mapper.parser.PojoParser;
import cn.yuyizyk.ground.model.pojo.base.POJO;

public class ResultMappingParser implements PojoParser<POJO>, PojoColumsParser<POJO> {

	private Map<Class<? extends POJO>, Function<Configuration, List<ResultMapping>>> resultMapping = new HashMap<>();

	public List<ResultMapping> toResultMappingList(Class<? extends POJO> c, Configuration configuration) {
		assert configuration != null;
		assert c != null;
		assert resultMapping.containsKey(c);
		return resultMapping.get(c).apply(configuration);
	}

	@Override
	public void parser(Class<POJO> c, Field colums) {

	}

	@Override
	public void parser(Class<POJO> pojo) {
	}

}
