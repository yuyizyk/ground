package cn.yuyizyk.ground.model.pojo.base;

import java.util.List;
import java.util.Map.Entry;

import cn.yuyizyk.ground.model.entity.Entitry;
import cn.yuyizyk.ground.model.pojo.parser.PojoDecoratorFactory;
import cn.yuyizyk.ground.model.pojo.parser.imp.PojoMapParser;

/**
 * Plain Old Java Object
 * 
 * @author yuyi
 *
 */
public abstract class POJO extends Entitry<POJO> {

	private transient static final long serialVersionUID = 1L;

	protected transient static final PojoDecoratorFactory POJO_DECORATOR_FACTORY = PojoDecoratorFactory.operation();

	/**
	 * 获得当前实体在数据库中的表名
	 * 
	 * @return
	 * 
	 * @return
	 */
	public String tableName() {
		return POJO_DECORATOR_FACTORY.getPojoParser(PojoMapParser.class).getTableName(this.getClass());
	}

	/**
	 * 获得索引
	 * 
	 * @return
	 */
	public List<Entry<String, Object>> indexKeys() {
		return POJO_DECORATOR_FACTORY.getPojoParser(PojoMapParser.class).getIndexKey(this);
	}
}
