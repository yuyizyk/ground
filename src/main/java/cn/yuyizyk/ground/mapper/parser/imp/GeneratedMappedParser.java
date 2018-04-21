package cn.yuyizyk.ground.mapper.parser.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.apache.ibatis.mapping.MappedStatement;

import cn.yuyizyk.ground.mapper.parser.PojoColumsParser;
import cn.yuyizyk.ground.mapper.parser.PojoParser;
import cn.yuyizyk.ground.model.pojo.addition.PojoType;
import cn.yuyizyk.ground.model.pojo.addition.PojoType.Columns;
import cn.yuyizyk.ground.model.pojo.base.POJO;

public class GeneratedMappedParser implements PojoParser, PojoColumsParser {

	private Map<Class<? extends POJO>, BiFunction<MappedStatement, Object[], MappedStatement>> generatedMappeds = new HashMap<>();

	@Override
	public void columnsParser(Columns colums) {
		// TODO Auto-generated method stub

	}

	@Override
	public void parser(PojoType pojo) {
		// TODO Auto-generated method stub

	}

	static class GMArgs {
		MappedStatement ms;
		Object[] args;
	}

	public MappedStatement generatedMappedStatement(Class<? extends POJO> c, MappedStatement ms, Object... args) {
		assert generatedMappeds.containsKey(c);
		return generatedMappeds.get(c).apply(ms, args);
	}
}
