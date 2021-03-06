package cn.yuyizyk.ground.mapper.base;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectProvider;

import cn.yuyizyk.ground.mapper.provider.SNFMapperProvider;
import cn.yuyizyk.ground.model.pojo.UserInfo;
import cn.yuyizyk.ground.model.pojo.base.SNFPOJO;

/**
 * Second Normal Form
 * 
 * @author yuyi
 *
 */
public interface TNFMapper extends SNFMapper {

	/**
	 * 主键
	 * 
	 * @param snfpojo
	 * @return
	 */
	@SelectProvider(type = SNFMapperProvider.class, method = "primaryKey")
	<T extends SNFPOJO> Map<String, Object> primaryKey(Class<T> snfpojo);

	/**
	 * 通过主键ID获得记录
	 * 
	 * @param idMap
	 *            含有ID 的map对
	 * @return
	 */
	@SelectProvider(type = SNFMapperProvider.class, method = "byId")
	<T extends SNFPOJO> T byId(Class<T> snfpojo, List<Entry<String, Object>> idMap);

	@Insert(value = { " insert into user_info(account,userid)  values(#{account},#{userid})" })
	public int insert1(UserInfo u);

	// /**
	// * 通过主键ID获得记录
	// *
	// * @param snfpojo
	// * @param id
	// * @return
	// * @throws NotUniquePrimaryKeyException
	// */
	// default <T extends SNFPOJO> T byId(Class<T> snfpojo, Object id) {
	// List<String> l = snfpojo.primaryKey();
	// if (l.size() != 1) {
	// throw new NotUniquePrimaryKeyException(
	// String.format("POJO [%s] An entity that is not the only primary key",
	// snfpojo.getName()));
	// }
	// List<Entry<String, Object>> li = new ArrayList<>();
	// li.add(new cn.yuyizyk.ground.model.entity.Entry<String, Object>(l.get(0),
	// id));
	// return byId(snfpojo, li);
	// }

	/**
	 * 通过主键ID获得记录
	 * 
	 * @param snfpojo
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default <T extends SNFPOJO> T byId(T pojo) {
		assert pojo != null;
		return byId((Class<T>) pojo.getClass(), pojo.primaryKeys());
	}

	/*	*//**
			 * 保存记录 <br/>
			 * insert
			 * 
			 * @param record
			 * 
			 * @return 保存后的记录，失败为空
			 *//*
				 * default <T extends SNFPOJO> T insert(T record) { return record; }
				 */

	/**
	 * 删除
	 * 
	 * @param second
	 * 
	 * @return 被删除的记录,删除失败为空
	 */
	<T extends SNFPOJO> T deleteById(String id);

}
