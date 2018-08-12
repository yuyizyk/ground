package cn.yuyizyk.ground.mapper.parser;

import java.lang.reflect.Field;

import cn.yuyizyk.ground.model.pojo.base.POJO;

/**
 * pojo解析器
 * 
 * @author yuyi
 *
 */
public interface PojoColumsParser<T extends POJO> {

	/**
	 * 解析
	 * 
	 * @param pojo
	 * @return
	 */
	public void parser(Class<T> c, Field colums);

}
