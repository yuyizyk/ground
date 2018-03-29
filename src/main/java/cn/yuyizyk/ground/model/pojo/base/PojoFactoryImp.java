package cn.yuyizyk.ground.model.pojo.base;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.yuyizyk.ground.model.annotations.AutoMap;
import cn.yuyizyk.ground.model.annotations.Generated;
import cn.yuyizyk.ground.model.annotations.PrimaryKey;
import cn.yuyizyk.ground.model.annotations.Table;
import cn.yuyizyk.ground.model.exception.NotUniquePrimaryKeyException;
import cn.yuyizyk.ground.model.exception.PojoDefinitionException;
import cn.yuyizyk.ground.util.cls.LoaderUtil;

/**
 * pojo工厂
 * 
 * @author yuyi
 *
 */
public class PojoFactoryImp implements PojoFactory {
	private final static Logger log = LoggerFactory.getLogger(PojoFactoryImp.class);
	private final static PojoFactoryImp pojoFactory = new PojoFactoryImp();

	private PojoFactoryImp() {
		init();
	}

	public static final PojoFactoryImp newInstance() {
		return pojoFactory;
	}

	private Map<String, Class<? extends POJO>> pojoNameToCls = new HashMap<>();
	private Map<String, Set<Class<? extends POJO>>> tNameToCls = new HashMap<>();
	private Map<Class<? extends POJO>, List<PrimaryKey>> snfMap = new HashMap<>();
	private Map<Class<? extends POJO>, PojoInfo> pojoMap = new HashMap<>();

	@SuppressWarnings("unchecked")
	public void init() {
		Set<Class<?>> set = LoaderUtil.getClzFromPkg("cn.yuyizyk.ground.model.pojo");
		set.forEach((c) -> {
			if (POJO.class.isAssignableFrom(c) && !Modifier.isAbstract(c.getModifiers())) {
				// 接收实体类
				new PojoInfo((Class<? extends POJO>) c);
			}
		});
	}

	/**
	 * 获得SNFPOJO 的主键
	 * 
	 * @param t
	 * @return
	 */
	public <T extends SNFPOJO> List<Entry<String, Object>> primaryKey(T t) {
		assert t != null;
		if (snfMap.containsKey(t.getClass())) {
			return pojoMap.get(t.getClass()).toPrimaryKey(t);
		} else {
			if (pojoMap.containsKey(t.getClass())) {
				throw new NotUniquePrimaryKeyException(
						String.format("POJO [%s]  An entity that is not the only primary key", t.getClass().getName()));
			}
			if (Modifier.isAbstract(t.getClass().getModifiers())) {
				throw new NotUniquePrimaryKeyException(
						String.format("POJO [%s]  An entity that is not the only primary key", t.getClass().getName()));
			}
			// 接收实体类
			return new PojoInfo(t.getClass()).toPrimaryKey(t);
		}
	}

	/**
	 * 获得POJO对象映射的数据库实体名称
	 * 
	 * @param class1
	 * @return
	 */
	public String tableName(Class<? extends POJO> cls) {
		assert cls != null;
		if (!pojoMap.containsKey(cls)) {
			new PojoInfo(cls);
		}
		PojoInfo p = pojoMap.get(cls);
		Objects.requireNonNull(p.tableInfo, String.format("该[%s]没有定义@Table注解", cls.getName()));
		return p.tableInfo.value();
	}

	class PojoInfo {

		private Map<AutoMap, Field> autoMaps;
		private List<PrimaryKey> primaryKeys;
		private Map<PrimaryKey, Field> primaryKeysMap;
		private Map<Generated, Field> generateds;
		private Map<String, Field> fieldMap;
		private List<Field> fields;
		private Class<? extends POJO> cls;
		private Table tableInfo = null;

		public PojoInfo(Class<? extends POJO> c) {
			this.cls = c;
			pojoMap.put(this.cls, this);
			this.tableInfo = c.getAnnotation(Table.class);
			pojoNameToCls.put(c.getSimpleName(), this.cls);
			if (this.tableInfo != null) {
				Set<Class<? extends POJO>> s = tNameToCls.getOrDefault(this.tableInfo.value(),
						new HashSet<Class<? extends POJO>>());
				s.add(this.cls);
				tNameToCls.put(this.tableInfo.value(), s);
			}

			this.fields = Arrays.asList(c.getDeclaredFields());
			this.primaryKeys = new LinkedList<>();
			this.primaryKeysMap = new HashMap<>();
			// List<IndexKey> list2 = new LinkedList<>();
			this.generateds = new HashMap<>();
			this.autoMaps = new HashMap<>();
			this.fieldMap = new HashMap<>();
			this.fields.forEach(f -> {
				fieldMap.put(f.getName(), f);
				if (f.getAnnotations().length == 0)
					return;
				PrimaryKey primarykey = f.getAnnotation(PrimaryKey.class);
				// 主键
				if (primarykey != null) {
					this.primaryKeys.add(primarykey.priority(), primarykey);
					primaryKeysMap.put(primarykey, f);
				}

				Generated generated = f.getAnnotation(Generated.class);
				// 主键
				if (generated != null) {
					this.generateds.put(generated, f);
				}

				AutoMap auto = f.getAnnotation(AutoMap.class);
				// 主键
				if (auto != null) {

					if (StringUtils.isBlank(auto.one().select()) && StringUtils.isBlank(auto.many().select())) {
						throw new PojoDefinitionException(c, String.format("该[%s]的 AutoMap注解有误.", f.getName()));
					}

					this.autoMaps.put(auto, f);
				}

				// IndexKey indexKey = f.getAnnotation(IndexKey.class);
				// if (indexKey != null) {
				// list2.add(indexKey.priority(), indexKey);
				// }

			});
			Collections.reverse(this.primaryKeys);
			// Collections.reverse(list2);

			if (tableInfo == null)
				if ((primaryKeys.size() | generateds.size() | autoMaps.size()) != 0) {
					throw new PojoDefinitionException(c, "使用primaryKeys、generateds、autoMaps缺少@Table");
				}

			if (SNFPOJO.class.isAssignableFrom(c)) {
				snfMap.put(this.cls, primaryKeys);
				if (primaryKeys.size() == 0) {
					throw new PojoDefinitionException(c,
							"该 SNFPOJO 缺少  PrimaryKey注解.SNFPOJO 为满足第二范式数据库实体对象的java pojo 因此必须声明PrimaryKey 用于使用涉及主键的操作.");
				}

				if (primaryKeys.size() == 1 && primaryKeys.get(0).value() != PrimaryKey.alone) {
					throw new PojoDefinitionException(c, "该 PrimaryKey注解有误.");
				} else {
					int union = 1;
					for (PrimaryKey k : primaryKeys) {
						union &= k.value();
					}
					if (union != 1)
						throw new PojoDefinitionException(c, "该 PrimaryKey注解有误.");
				}

			}

			log.info("注册POJO :[{}]成功!", c.getName());
		}

		/**
		 * 取得主键
		 * 
		 * @param t
		 * @return
		 */
		public <T extends SNFPOJO> List<Entry<String, Object>> toPrimaryKey(T t) {
			Objects.requireNonNull(primaryKeys, "");
			List<Entry<String, Object>> li = new ArrayList<>(primaryKeys.size());
			primaryKeys.forEach(a -> {
				try {
					Field f = primaryKeysMap.get(a);
					f.setAccessible(true);
					li.add(new cn.yuyizyk.ground.model.entity.Entry<String, Object>(f.getName(), f.get(t)));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					log.error("", e);
				}
			});
			return li;
		}
	}

	public static void main(String[] args) {
		new PojoFactoryImp();
	}
}
