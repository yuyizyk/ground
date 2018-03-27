package cn.yuyizyk.ground.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;

import cn.yuyizyk.ground.mapper.entity.JoinMap;
import cn.yuyizyk.ground.mapper.provider.SelfMapperProvider;
import cn.yuyizyk.ground.model.pojo.RoleInfo;
import cn.yuyizyk.ground.model.pojo.base.POJO;

public interface SelfMapper {

	/**
	 * 查询满足条件的一条记录 <br/>
	 * 一对一
	 * 
	 * 
	 * @param cls
	 *            对象类型
	 * @param filter
	 *            不为空的map 键值对 用于筛选
	 * @param sort
	 *            不为空的map 键值对 用于排序
	 * @return
	 */
	@SelectProvider(type = SelfMapperProvider.class, method = "oneToOne")
	<T extends POJO> T oneToOne(Class<T> cls, Map<String, Object> filter);

	/**
	 * 查询满足条件的记录集合 <br/>
	 * 一对多
	 * 
	 * @param cls
	 *            对象类型
	 * @param filter
	 *            不为空的map 键值对 用于筛选
	 * @param sort
	 *            不为空的map 键值对 用于排序
	 * @return
	 */
	@SelectProvider(type = SelfMapperProvider.class, method = "oneToMany")
	<T extends POJO> List<T> oneToMany(Class<T> cls, Map<String, Object> filter, Map<String, Object> sort);

	/**
	 * 查询满足条件的记录集合(中间表) <br/>
	 * 多对多 先进行1对多，在进行1对1
	 * 
	 * @param cls
	 *            对象类型
	 * @param joins
	 * @param filter
	 *            不为空的map 键值对 用于筛选
	 * @param sort
	 *            不为空的map 键值对 用于排序
	 * @return
	 */
	@SelectProvider(type = SelfMapperProvider.class, method = "manyToMany")
	<T extends POJO> List<T> manyToMany(Class<? extends POJO> cls, List<JoinMap> joins,
			Map<String, Object> filter, Map<String, Object> sort);

	/**
	 * 查询满足条件的记录集合(中间表) <br/>
	 * 多对多 先进行1对多，在进行1对1
	 * 
	 * @param cls
	 *            对象类型
	 * @param joins
	 * @param filter
	 *            不为空的map 键值对 用于筛选
	 * @return
	 */
	default <T extends POJO> List<T> manyToMany(Class<? extends POJO> cls, List<JoinMap> joins,
			Map<String, Object> filter) {
		return manyToMany(cls, joins, filter, null);
	}

	/**
	 * 查询满足条件的记录集合(中间表) <br/>
	 * 多对多 先进行1对多，在进行1对1
	 * 
	 * @param cls
	 *            对象类型
	 * @param joins
	 * @return
	 */
	default <T extends POJO> List<T> manyToMany(Class<? extends POJO> cls, List<JoinMap> joins) {
		return manyToMany(cls, joins, null, null);
	}

	/**
	 * 查询满足条件的记录集合(中间表) <br/>
	 * 多对多 先进行1对多，在进行1对1
	 * 
	 * @param cls
	 *            对象类型
	 * @param joins
	 * @return
	 */
	default <T extends POJO> List<T> manyToMany(Class<? extends POJO> cls, JoinMap... joins) {
		List<JoinMap> l = new ArrayList<JoinMap>();
		Collections.addAll(l, joins);
		return manyToMany(cls, l, null, null);
	}

}
