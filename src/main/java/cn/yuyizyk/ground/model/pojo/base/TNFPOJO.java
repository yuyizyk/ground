package cn.yuyizyk.ground.model.pojo.base;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.yuyizyk.ground.mapper.parser.imp.TnfPojoMapParser;

/**
 * 满足第三范式的实体类
 * 
 * @author yuyi
 *
 */
public abstract class TNFPOJO extends SNFPOJO {
	private static final long serialVersionUID = 1L;
	private final static Logger log = LoggerFactory.getLogger(TNFPOJO.class);

	/**
	 * 获得主键
	 * 
	 * @return
	 */
	public Entry<String, Object> primaryKey() {
		return POJO_DECORATOR_FACTORY.getPojoParser(TnfPojoMapParser.class).getPrimaryKey(this);
	}

}
