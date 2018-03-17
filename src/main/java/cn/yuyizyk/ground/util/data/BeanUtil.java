package cn.yuyizyk.ground.util.data;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.GsonBuilder;

/**
 * Bean Map转换
 *
 */
public class BeanUtil {
	private final static Logger log = LoggerFactory.getLogger(BeanUtil.class);

	/**
	 * MapToBean
	 * 
	 * @param bean
	 * @param properties
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T> T toBean(T bean, Map<String, Object> properties)
			throws IllegalAccessException, InvocationTargetException {
		ConvertUtils.register(new MyTimestamp(), Timestamp.class); // 时间处理
		ConvertUtils.register(new MyDate(), Date.class);
		BeanUtilsBean.getInstance().populate(bean, properties);
		return bean;
	}

	// public static <T> T toBean(Class<T> cls, String jsonStr) {
	// return new GsonBuilder().create().fromGson(jsonStr, cls);
	// }

	// public static GsonBuilder

	/**
	 * toMap
	 * 
	 * @param obj
	 * @return
	 */
	public static HashMap<String, Object> toMap(Object obj) {
		HashMap<String, Object> params = new HashMap<String, Object>(0);
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!"class".equals(name)) {
					params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
				}
			}
		} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			log.error("异常:", e);
		}
		return params;
	}

	/**
	 * 时间
	 * 
	 */
	static class MyTimestamp implements Converter {

		@SuppressWarnings({ "unchecked", "hiding" })
		@Override
		public <Timestamp> Timestamp convert(Class<Timestamp> paramClass, Object paramObject) {
			LocalDateTime localDateTime = DateUtil.parse(paramObject.toString());
			if (localDateTime == null)
				return null;
			return (Timestamp) DateUtil.toTimestamp(localDateTime);
		}

	}

	static class MyDate implements Converter {

		@SuppressWarnings({ "unchecked", "hiding" })
		@Override
		public <Date> Date convert(Class<Date> paramClass, Object paramObject) {
			LocalDateTime localDateTime = DateUtil.parse(paramObject.toString());
			if (localDateTime == null)
				return null;
			return (Date) DateUtil.toSqlDate(localDateTime);
		}

	}

}
