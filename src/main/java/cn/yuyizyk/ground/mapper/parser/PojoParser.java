package cn.yuyizyk.ground.mapper.parser;

import cn.yuyizyk.ground.model.pojo.addition.PojoType;

/**
 * pojo解析器
 * 
 * @author yuyi
 *
 */
public interface PojoParser {

	/**
	 * 解析
	 * 
	 * @param pojo
	 * @return
	 */
	public void parser(PojoType pojo);

}
