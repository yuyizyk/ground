package cn.yuyizyk.ground.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.yuyizyk.ground.mapper.provider.MapperProvider;
import cn.yuyizyk.ground.model.pojo.base.POJO;
import cn.yuyizyk.ground.util.data.SerializationUtil;

/**
 * 基础mapper
 * 
 * @author yuyi
 *
 */
public interface Mapper {

	/**
	 * 添加记录 <br/>
	 * 
	 * @param record
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default <T extends POJO> int insert(T record) {
		assert record != null & record != null;
		return insert((Class<T>) record.getClass(), SerializationUtil.toMapByGetter(record, pd -> {
			if (POJO.class.isAssignableFrom(pd.getPropertyType()))
				return false;
			if (String.class.isAssignableFrom(pd.getPropertyType()))
				return true;
			if (Number.class.equals(pd.getPropertyType()))
				return true;
			return false;
		}, val -> {
			return true;
		}));
	}

	/**
	 * 添加记录 <br/>
	 * 
	 * @param cls
	 *            对象类型
	 * @param filterMap
	 *            不为空的map 键值对 用于赛值
	 * @return
	 */
	@InsertProvider(type = MapperProvider.class, method = "insert")
	<T extends POJO> int insert(Class<T> cls, Map<String, Object> recordMap);

	/**
	 * 修改记录 <br/>
	 * 
	 * @param cls
	 *            对象类型
	 * @param filterMap
	 *            不为空的map 键值对 用于筛选
	 * @param updataMap
	 *            不为空的map 键值对 用于更新
	 * @return
	 */
	@UpdateProvider(type = MapperProvider.class, method = "update")
	<T extends POJO> int update(Class<T> cls, Map<String, Object> filterMap, Map<String, Object> updataMap);

	/**
	 * 修改记录 <br/>
	 * 
	 * @param oldRecord
	 *            通过不为空的属性 进行筛选
	 * @param newRecord
	 *            通过不为空的属性 进行更新
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default <T extends POJO> int update(T oldRecord, T newRecord) {
		assert oldRecord != null & newRecord != null;
		return update((Class<T>) oldRecord.getClass(), oldRecord.toSqlMap(), newRecord.toSqlMap());
	}

	/**
	 * 返回删除指定属性的对象
	 * 
	 * @param cls
	 *            对象类型
	 * @param filter
	 *            不为空的map 键值对 用于筛选
	 * @return
	 */
	@DeleteProvider(type = MapperProvider.class, method = "delete")
	<T extends POJO> int delete(Class<T> cls, Map<String, Object> filterMap);

	/**
	 * 返回删除指定属性的对象
	 * 
	 * @param record
	 *            通过不为空的属性
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default <T extends POJO> int delete(T record) {
		assert record != null;
		return delete((Class<T>) record.getClass(), record.toSqlMap());
	}

	/**
	 * 查询满足条件的记录集合
	 * 
	 * @param cls
	 *            对象类型
	 * @param filter
	 *            不为空的map 键值对 用于筛选
	 * @param sort
	 *            不为空的map 键值对 用于排序
	 * @return
	 */
	@SelectProvider(type = MapperProvider.class, method = "list")
	<T extends POJO> List<T> list(Class<T> cls, Map<String, Object> filter, Map<String, Object> sort);

	/**
	 * 查询满足条件的记录集合
	 * 
	 * 
	 * @param record
	 *            通过不为空的属性
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default public <T extends POJO> List<T> list(T record) {
		assert record != null;
		return list((Class<T>) record.getClass(), record.toSqlMap(), null);
	}

	/**
	 * 查询满足条件的记录集合
	 * 
	 * @param cls
	 *            对象类型
	 * @param filter
	 *            不为空的map 键值对
	 * @return
	 */
	default public <T extends POJO> List<T> list(Class<T> cls, Map<String, Object> filter) {
		return list(cls, filter, null);
	}

	/**
	 * 查询满足条件的记录分页集合
	 * 
	 * @param cls
	 *            pojo类型
	 * @param page
	 *            页码
	 * @param size
	 *            页长度
	 * @param filter
	 *            不为空的map 键值对 用于筛选
	 * @param sort
	 *            不为空的map 键值对 用于排序
	 * @return
	 */
	@SelectProvider(type = MapperProvider.class, method = "page")
	<T extends POJO> List<T> page(Class<T> cls, int page, int size, Map<String, Object> filter,
			Map<String, Object> sort);

	/**
	 * 查询满足条件的记录分页集合
	 * 
	 * 
	 * @param record
	 *            通过不为空的属性
	 * @param page
	 *            页码
	 * @param size
	 *            页长度
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default public <T extends POJO> List<T> page(T record, int page, int size) {
		assert record != null;
		return page((Class<T>) record.getClass(), page, size, record.toSqlMap(), null);
	}

	/**
	 * 查询满足条件的记录分页集合
	 * 
	 * @param cls
	 *            对象类型
	 * @param page
	 *            页码
	 * @param size
	 *            页长度
	 * @param filter
	 *            不为空的map 键值对
	 * @return
	 */
	default public <T extends POJO> List<T> page(Class<T> cls, int page, int size, Map<String, Object> filter) {
		return page(cls, page, size, filter);
	}

	/**
	 * 查询记录总数
	 * 
	 * 
	 * @param cls
	 *            pojo
	 * @param filter
	 *            不为空的map 键值对
	 * @return
	 */
	@SelectProvider(type = MapperProvider.class, method = "total")
	<T extends POJO> Long total(Class<T> cls, Map<String, Object> filterMap);

	/**
	 * 查询记录总数
	 * 
	 * 
	 * @param record
	 *            通过不为空的属性
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default public <T extends POJO> Long total(T record) {
		assert record != null;
		return total((Class<T>) record.getClass(), record.toSqlMap());
	}

}
