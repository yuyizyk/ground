package cn.yuyizyk.ground.mapper.parser.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import cn.yuyizyk.ground.mapper.parser.PojoColumsParser;
import cn.yuyizyk.ground.mapper.parser.PojoParser;
import cn.yuyizyk.ground.model.pojo.addition.PojoType;
import cn.yuyizyk.ground.model.pojo.addition.PojoType.Columns;
import cn.yuyizyk.ground.model.pojo.base.POJO;

public class ResultMappingParser implements PojoParser, PojoColumsParser {

	private Map<Class<? extends POJO>, Function<Configuration, List<ResultMapping>>> resultMapping = new HashMap<>();

	@Override
	public void columnsParser(Columns colums) {

	}

	@Override
	public void parser(PojoType pojo) {

	}

	public List<ResultMapping> toResultMappingList(Class<? extends POJO> c, Configuration configuration) {
		assert configuration != null;
		assert c != null;
		assert resultMapping.containsKey(c);
		return resultMapping.get(c).apply(configuration);
	}

}
