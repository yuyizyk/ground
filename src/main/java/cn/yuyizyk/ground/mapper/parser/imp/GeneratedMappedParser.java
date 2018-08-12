package cn.yuyizyk.ground.mapper.parser.imp;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.apache.ibatis.mapping.MappedStatement;

import cn.yuyizyk.ground.mapper.parser.AbstractPojoTypeParser;
import cn.yuyizyk.ground.model.pojo.base.POJO;

public class GeneratedMappedParser extends AbstractPojoTypeParser<POJO> {

	private Map<Class<? extends POJO>, BiFunction<MappedStatement, Object[], MappedStatement>> generatedMappeds = new HashMap<>();

	static class GMArgs {
		MappedStatement ms;
		Object[] args;
	}

	public MappedStatement generatedMappedStatement(Class<? extends POJO> c, MappedStatement ms, Object... args) {
		assert generatedMappeds.containsKey(c);
		return generatedMappeds.get(c).apply(ms, args);
	}

	@Override
	public void parser(Class<POJO> pojo) {

	}

	@Override
	public void parser(Class<POJO> c, Field colums) {
	}
}
