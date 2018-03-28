package cn.yuyizyk.ground.model.pojo.base;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.yuyizyk.ground.model.annotations.IndexKey;
import cn.yuyizyk.ground.model.annotations.PrimaryKey;

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
	 * 获得主键的列名
	 * 
	 * @return
	 */
	public static final <T extends SNFPOJO> List<String> primaryKey(Class<T> cls) {
		List<Field> fields = Arrays.asList(cls.getDeclaredFields());
		List<String> list = new LinkedList<>();
		fields.forEach(f -> {
			PrimaryKey primarykey = f.getAnnotation(PrimaryKey.class);
			// 主键
			if (primarykey != null) {
				try {
					list.add(primarykey.priority(), f.getName());
				} catch (IllegalArgumentException e) {
					log.error("", e);
				}
			}
		});
		Collections.reverse(list);
		return list;
	}

	/**
	 * 获得主键
	 * 
	 * @return
	 */
	public static final List<Entry<String, Object>> primaryKey(SNFPOJO pojo) {
		List<Field> fields = Arrays.asList(pojo.getClass().getDeclaredFields());
		List<Entry<String, Object>> list = new LinkedList<>();
		fields.forEach(f -> {
			PrimaryKey primarykey = f.getAnnotation(PrimaryKey.class);
			// 主键
			if (primarykey != null) {
				try {
					f.setAccessible(true);
					list.add(primarykey.priority(),
							new cn.yuyizyk.ground.model.entity.Entry<String, Object>(f.getName(), f.get(pojo)));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					log.error("", e);
				}
			}
		});
		Collections.reverse(list);
		return list;
	}

	/**
	 * 获得索引
	 * 
	 * @return
	 */
	public static final List<Entry<String, Object>> indexKey(SNFPOJO pojo) {
		List<Field> fields = Arrays.asList(pojo.getClass().getDeclaredFields());
		List<Entry<String, Object>> list = new LinkedList<>();
		fields.forEach(f -> {
			IndexKey indexKey = f.getAnnotation(IndexKey.class);
			if (indexKey != null) {
				try {
					f.setAccessible(true);
					list.add(indexKey.priority(),
							new cn.yuyizyk.ground.model.entity.Entry<String, Object>(f.getName(), f.get(pojo)));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					log.error("", e);
				}
			}
		});
		Collections.reverse(list);
		list.addAll(0, primaryKey(pojo));
		return list;
	}

	/**
	 * 获得主键
	 * 
	 * @return
	 */
	public List<Entry<String, Object>> primaryKey() {
		return primaryKey(this);
	}

	/**
	 * 获得索引
	 * 
	 * @return
	 */
	public List<Entry<String, Object>> indexKey() {
		return indexKey(this);
	}

}
