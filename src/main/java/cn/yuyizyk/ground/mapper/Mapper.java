package cn.yuyizyk.ground.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;

import cn.yuyizyk.ground.mapper.provider.MapperProvider;
import cn.yuyizyk.ground.model.pojo.POJO;

/**
 * 基础mapper
 * 
 * @author yuyi
 *
 */
public interface Mapper<T extends POJO> {

	/**
	 * 添加记录 <br/>
	 * 
	 * @param record
	 * @return
	 */
	T insert(T record);

	/**
	 * 修改记录 <br/>
	 * 
	 * @return
	 */
	T updata(T oldRecord, T newRecord);

	/**
	 * 删除
	 * 
	 * @param record
	 * @return
	 */
	int delete(T record);

	/**
	 * 查询记录 <br/>
	 * 通过主要属性查找
	 * 
	 * @param record
	 * @return
	 */
	@SelectProvider(type = MapperProvider.class, method = "listByFilter")
	List<T> listByFilter(T record);

	/**
	 * 查询记录 <br/>
	 * 通过主要属性查找
	 * 
	 * @param record
	 * @return
	 */
	@SelectProvider(type = MapperProvider.class, method = "listByFilter")
	List<T> listByFilter(String tableName, Map<String, Object> record);

	/**
	 * 查询记录总数 <br/>
	 * 通过主要属性查找
	 * 
	 * @param record
	 * @return
	 */
	@SelectProvider(type = MapperProvider.class, method = "total")
	long total(T record);

}
