package cn.yuyizyk.ground.model.pojo.base;

import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.yuyizyk.ground.mapper.parser.imp.SnfPojoMapParser;

/**
 * 满足第二范式的实体类
 * 
 * @author yuyi
 *
 */
public abstract class SNFPOJO extends POJO {
	private static final long serialVersionUID = 1L;
	private final static Logger log = LoggerFactory.getLogger(SNFPOJO.class);

	/**
	 * 获得主键
	 * 
	 * @return
	 */
	public List<Entry<String, Object>> primaryKeys() {
		return POJO_DECORATOR_FACTORY.getPojoParser(SnfPojoMapParser.class).getPrimaryKey(this);
	}

}
