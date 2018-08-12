package cn.yuyizyk.ground.mapper.parser;

import cn.yuyizyk.ground.model.pojo.base.POJO;

/**
 * pojo解析器
 * 
 * @author yuyi
 *
 */
public interface PojoParser<T extends POJO> {

	/**
	 * 解析
	 * 
	 * @param pojo
	 * @return
	 */
	public void parser(Class<T> pojo);
}
