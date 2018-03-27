package cn.yuyizyk.ground.mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author yuyi
 *
 */
public interface BaseMapper {

	public List<Map<String, Object>> page(String talble, int page, int size, Map<String, Object> filter,
			Map<String, Object> sort);

	public List<Map<String, Object>> page(String talble, int page, int size, Map<String, Object> filter);

	public List<Map<String, Object>> list(String talble, Map<String, Object> filter, Map<String, Object> sort);

	public List<Map<String, Object>> list(String talble, Map<String, Object> filter);

	public long total(String talble, Map<String, Object> filter);
}
