package cn.yuyizyk.ground.model.pojo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.yuyizyk.ground.model.annotations.Primarykey;

/**
 * 满足第二范式的实体类
 * 
 * @author yuyi
 *
 */
public abstract class SNFPOJO extends POJO {
	private final static Logger log = LoggerFactory.getLogger(SNFPOJO.class);

	/**
	 * 获得主键
	 * 
	 * @return
	 */

	public static final List<Entry<String, Object>> getPrimaryKey(SNFPOJO pojo) {
		List<Field> fields = new ArrayList<>(Arrays.asList(pojo.getClass().getDeclaredFields()));
		List<Entry<String, Object>> list = new LinkedList<>();
		for (final Field f : fields) {
			Primarykey primarykey = f.getAnnotation(Primarykey.class);
			if (primarykey != null) {
				try {
					f.setAccessible(true);
					list.add(primarykey.priority(),
							new cn.yuyizyk.ground.model.entity.Entry<String, Object>(f.getName(), f.get(pojo)));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					log.error("", e);
				}
			}
		}
		Collections.reverse(list);
		return list;
	}

}
