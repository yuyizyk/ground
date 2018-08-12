package cn.yuyizyk.ground.model.pojo.base;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.yuyizyk.ground.beans.BeanContainer;
import cn.yuyizyk.ground.mapper.parser.imp.PojoMapParser;
import cn.yuyizyk.ground.model.entity.Entitry;
import cn.yuyizyk.ground.util.data.SerializationUtil;

/**
 * Plain Old Java Object
 * 
 * @author yuyi
 *
 */
public abstract class POJO extends Entitry<POJO> {

	private transient static final long serialVersionUID = 1L;

	/**
	 * 获得当前实体在数据库中的表名
	 * 
	 * @return
	 * 
	 * @return
	 */
	public String tableName() {
		return BeanContainer.getBean(PojoMapParser.class).getTableName(this.getClass());
	}

	/**
	 * 获得索引
	 * 
	 * @return
	 */
	public Set<Entry<String, Object>> indexKeys() {
		return BeanContainer.getBean(PojoMapParser.class).getIndexKey(this);
	}

	/**
	 * 将POJO转化为MAP对象
	 * 
	 * @return
	 */
	public Map<String, Object> toSqlMap() {
		return SerializationUtil.toMapByGetter(this, pd -> {
			if (POJO.class.isAssignableFrom(pd.getPropertyType()))
				return false;
			if (String.class.isAssignableFrom(pd.getPropertyType()))
				return true;
			if (Number.class.equals(pd.getPropertyType()))
				return true;
			return false;
		}, val -> {
			if (val == null)
				return false;
			return true;
		});
	}

}
