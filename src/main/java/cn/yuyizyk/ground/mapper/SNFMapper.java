package cn.yuyizyk.ground.mapper;

import org.apache.ibatis.annotations.SelectProvider;

import cn.yuyizyk.ground.mapper.provider.SNFMapperProvider;
import cn.yuyizyk.ground.model.pojo.SNFPOJO;

/**
 * Second Normal Form
 * 
 * @author yuyi
 *
 */
public interface SNFMapper<T extends SNFPOJO> extends Mapper<SNFPOJO> {

	/**
	 * 通过主键ID获得记录
	 * 
	 * @param id
	 * @return
	 */
	//@SelectProvider(type = SNFMapperProvider.class, method = "byId")
	T byId(T record);

	/**
	 * 保存记录 <br/>
	 * update and insert
	 * 
	 * @param record
	 * 
	 * @return 保存后的记录，失败为空
	 */
	T save(T record);

	/**
	 * 删除
	 * 
	 * @param second
	 * 
	 * @return 被删除的记录,删除失败为空
	 */
	T deleteById(String id);

}
