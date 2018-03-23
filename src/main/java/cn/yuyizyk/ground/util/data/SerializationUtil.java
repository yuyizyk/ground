package cn.yuyizyk.ground.util.data;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import cn.yuyizyk.ground.model.pojo.RoleInfo;
import cn.yuyizyk.ground.model.pojo.UserInfo;

/**
 * 序列化工具
 *
 */
public class SerializationUtil implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static Logger log = LoggerFactory.getLogger(SerializationUtil.class);
	private final static Gson gson = getGsonBuilder().create();

	public static final GsonBuilder getGsonBuilder() {
		return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")// 时间转化为特定格式
				.enableComplexMapKeySerialization()// 支持Map的key为复杂对象的形式
				.setLenient()// json宽松
				// .setPrettyPrinting()// 对json结果格式化.
				// .excludeFieldsWithModifiers(java.lang.reflect.Modifier.PRIVATE)//
				//////////// 配置Gson以排除所有具有指定修饰符的类字段(序列化和反序列化都排除) 默认TRANSIENT STATIC
				// .registerTypeAdapter(Type type, Object typeAdapter)//配置Gson以进行自定义序列化或反序列化
				// .registerTypeAdapterFactory(TypeAdapterFactory factory//)为类型适配器注册一个工厂。
				// .registerTypeHierarchyAdapter(baseType,typeAdapter)//将Gson配置为继承类型层次结构的自定义序列化或反序列化。
				// .setExclusionStrategies(strategies)//配置Gson在序列化和反序列化过程中应用一组排除策略。
				// .disableInnerClassSerialization()// 配置Gson在序列化过程中排除内部类。
				// .addSerializationExclusionStrategy(strategy)//配置Gson在序列化过程中应用传入的排除策略
				// .addDeserializationExclusionStrategy(strategy)// 在反序列化过程中应用的排除策略
				// .disableHtmlEscaping()//默认情况下，Gson转义HTML字符，例如<>等。使用此选项将Gson配置为按原样传递HTML字符。
				.serializeNulls()// 配置Gson以序列化空字段。
		;
	}

	/**
	 * 将json字符串格式化
	 * 
	 * @param jsStr
	 * @param cls
	 *            格式化对象类型
	 */
	public static final <T> T toBeanByJson(String jsStr, Class<T> cls) throws JsonParseException {
		return toBeanByJson(jsStr, TypeToken.get(cls));
	}

	/**
	 * 将xml字符串格式化
	 * 
	 * @param xml
	 * @param cls
	 * @return
	 */
	public static final <T> T toBeanByXml(String xml, Class<T> cls) {
		return null;
	}

	/**
	 * 将对象xml化
	 * 
	 * @param obj
	 * @return
	 */
	public static final String toXmlStr(Object obj) {
		return null;
	}

	/**
	 * 将json字符串格式化
	 * 
	 * @param jsStr
	 * @param type
	 *            可处理泛型
	 */
	public static final <T> T toBeanByJson(String jsStr, TypeToken<T> type) throws JsonParseException {
		return gson.fromJson(toJson(jsStr), type.getType());
	}

	/**
	 * 
	 * @param jsStr
	 * @return
	 */
	public static final JsonElement toJson(String jsStr) throws JsonParseException {
		try {
			return new JsonParser().parse(jsStr);
		} catch (JsonParseException e) {
			log.error("json解析异常:jsonStr:[{}]", jsStr, e);
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 
	 * @param jsStr
	 * @return
	 */
	public static final boolean isJson(String jsStr) {
		if (StringUtils.isBlank(jsStr)) {
			return false;
		}
		try {
			JsonElement je = toJson(jsStr);
			return je.isJsonNull();
		} catch (JsonParseException e) {
			log.info("该json解析异常:[{}]", jsStr, e);
			return false;
		}
	}

	public static void main(String[] args) {
		// JsonElement je = toJson("[{\"userid\":\"123\"},{\"userid\":\"23\"1}]");
		// System.out.println(isJson("[{\"userid\":\"123\"},{\\\"userid\\\":\\\"23\\\"1}]"));
		// System.out.println(je);
		// List<UserInfo> list = toBean(new ArrayList<UserInfo>(),
		// "[{\"userid\":\"123\"},{\"userid\":\"23\"}]");
		// System.out.println(list);
		UserInfo u = new UserInfo();
		u.setUserid("123");
		Set<RoleInfo> roles = new HashSet<>();
		RoleInfo e = new RoleInfo();
		e.setRolename("角色1");
		roles.add(e);
		u.setRoles(roles);
		System.out.println(u.toJsonStr());

		getGsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategyUtil.RegularOutput()).create();
		// getGsonBuilder().registerTypeHierarchyAdapter(POJO.class, new
		// TypeAdapter<POJO>() {
		//
		// @Override
		// public POJO read(JsonReader in) throws IOException {
		// return null;
		// }
		//
		// @Override
		// public void write(JsonWriter out, POJO value) throws IOException {
		// }
		//
		// }).create().fromJson(json, classOfT);
		// getGsonBuilder().registerTypeAdapterFactory(new TypeAdapterFactory() {
		// @Override
		// public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		// return null;
		// }
		// });

		/*
		 * u = gson.fromJson(toJson("{\"userid\":\"123\"}"), new TypeToken<UserInfo>() {
		 * }.getType());
		 */
		// u = toBean("{\"userid\":\"123\"}", UserInfo.class);
		// System.out.println(u.toJsonStr());
		//
		// List<Map<Integer, UserInfo>> li = new ArrayList<Map<Integer, UserInfo>>();
		// Map<Integer, UserInfo> m = new HashMap<>();
		// m.put(123, u);
		// System.out.println(toJsonStr(m));
		// li.add(m);
		// System.out.println(toJsonStr(li));
		// System.out.println(12312);
		//
		// toBean("[{\"123\":{\"userid\":\"123\"}}]", new TypeToken<List<Map<Integer,
		// UserInfo>>>());
		// System.out.println(li.size());
		// System.out.println(li);
		// u = li.get(0).entrySet().iterator().next().getValue();
		// System.out.println(u.toJsonStr());
		// System.out.println(toJsonStr(li));

	}

	/**
	 * 序列化为json字符串 <br/>
	 * 默认使用gson，内部类gson不能解析，使用fastjson代替
	 * 
	 * @param bean
	 * @return
	 */
	public static final String toJsonStr(Object bean) {
		Class<?> cls = bean.getClass();
		if (Objects.isNull(cls.getCanonicalName())) {
			/**
			 * 目标返回基础类的规范名称 <br/>
			 * 由Java语言规范定义。 如果返回null: 基础类没有规范的名称<br/>
			 * （即，它是一个本地或匿名类或其组件的数组类型没有规范名称）
			 */
			return JSONObject.toJSONString(bean);
		}
		return gson.toJson(bean);
	}

	/**
	 * toMap
	 * 
	 * @param obj
	 * @return
	 */
	public static final HashMap<String, Object> toMap(Object obj) {
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
			log.error("toMap异常:obj:[{}]", obj, e);
			e.printStackTrace();
		}
		return params;
	}

	/**
	 * MapToBean
	 * 
	 * @param bean
	 * @param properties
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static final <T> T toBean(T bean, Map<String, Object> properties)
			throws IllegalAccessException, InvocationTargetException {
		BeanUtilsBean.getInstance().populate(bean, properties);
		return bean;
	}

	/**
	 * 
	 * @author yuyi
	 *
	 */
	static class BeanUtils {

		static {
			/**
			 * Timestamp
			 */
			ConvertUtils.register(new Converter() {
				@SuppressWarnings({ "unchecked", "hiding" })
				@Override
				public <Timestamp> Timestamp convert(Class<Timestamp> paramClass, Object paramObject) {
					LocalDateTime localDateTime = DateUtil.parse(paramObject.toString());
					if (localDateTime == null)
						return null;
					return (Timestamp) DateUtil.toTimestamp(localDateTime);
				}

			}, Timestamp.class); // 时间处理
			/**
			 * Date
			 */
			ConvertUtils.register(new Converter() {
				@SuppressWarnings({ "unchecked", "hiding" })
				@Override
				public <Date> Date convert(Class<Date> paramClass, Object paramObject) {
					LocalDateTime localDateTime = DateUtil.parse(paramObject.toString());
					if (localDateTime == null)
						return null;
					return (Date) DateUtil.toSqlDate(localDateTime);
				}

			}, Date.class);
		}
	}

}
