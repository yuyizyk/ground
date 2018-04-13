package cn.yuyizyk.ground.mapper.parser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.yuyizyk.ground.model.annotations.AutoMap;
import cn.yuyizyk.ground.model.annotations.Generated;
import cn.yuyizyk.ground.model.annotations.IndexKey;
import cn.yuyizyk.ground.model.annotations.PrimaryKey;
import cn.yuyizyk.ground.model.annotations.Table;
import cn.yuyizyk.ground.model.entity.Entitry;
import cn.yuyizyk.ground.model.exception.PojoDefinitionException;
import cn.yuyizyk.ground.model.pojo.base.POJO;
import cn.yuyizyk.ground.model.pojo.base.SNFPOJO;

/**
 * POJO实体类型信息对象
 * 
 * @author yuyi
 *
 */
public class PojoType extends Entitry<PojoType> {
	private static final transient long serialVersionUID = 1L;
	private final static transient Logger log = LoggerFactory.getLogger(PojoType.class);

	private Set<Columns> columns = new HashSet<>();
	private Map<AutoMap, Columns> autoMaps = new HashMap<>();
	private Map<PrimaryKey, Columns> primaryKeys = new HashMap<>();
	private Map<IndexKey, Columns> IndexKeys = new HashMap<>();
	private Map<Generated, Columns> generateds = new HashMap<>();
	private Map<Field, Columns> fields = new HashMap<>();
	private Class<? extends POJO> cls;
	private Table tableInfo = null;

	public PojoType(Class<? extends POJO> c) {
		this.cls = c;
		this.tableInfo = c.getAnnotation(Table.class);
		int isunion = 1;
		PrimaryKey primaryKey = null;
		Generated generated = null;
		AutoMap autoMap = null;
		IndexKey index = null;
		List<Field> fs = Arrays.asList(c.getDeclaredFields());
		Iterator<Field> it = fs.iterator();
		for (Field f = it.next(); it.hasNext(); f = it.next()) {
			if (f.getAnnotations().length != 0) {
				primaryKey = f.getAnnotation(PrimaryKey.class);
				generated = f.getAnnotation(Generated.class);
				autoMap = f.getAnnotation(AutoMap.class);
				index = f.getAnnotation(IndexKey.class);
			}
			Columns col = new Columns(f, autoMap, primaryKey, generated);
			columns.add(col);
			fields.put(f, col);
			// 主键
			if (primaryKey != null) {
				isunion &= primaryKey.value();
				primaryKeys.put(primaryKey, col);
			}
			if (generated != null) {
				this.generateds.put(generated, col);
			}
			if (autoMap == null) {
			} else if (StringUtils.isBlank(autoMap.one().select()) && StringUtils.isBlank(autoMap.many().select())) {
				throw new PojoDefinitionException(c, String.format("该[%s]的 AutoMap注解有误.", f.getName()));
			}
			this.autoMaps.put(autoMap, col);
			if (index != null) {
				IndexKeys.put(index, col);
			}
		}
		if (tableInfo == null && (primaryKeys.size() | generateds.size() | autoMaps.size()) != 0) {
			throw new PojoDefinitionException(c, "使用primaryKeys、generateds、autoMaps缺少@Table");
		}
		if (SNFPOJO.class.isAssignableFrom(c)) {
			if (primaryKeys.size() == 0) {
				throw new PojoDefinitionException(c,
						"该 SNFPOJO 缺少  PrimaryKey注解.SNFPOJO 为满足第二范式数据库实体对象的java pojo 因此必须声明PrimaryKey 用于使用涉及主键的操作.");
			} else if (primaryKeys.size() == 1) {
				if (isunion == PrimaryKey.union) {
					// 联合主键
					throw new PojoDefinitionException(c, "该 PrimaryKey注解有误.");
				}
			} else if (isunion != 1) {
				throw new PojoDefinitionException(c, "该 PrimaryKey注解有误.");
			}
		}

		log.info("注册POJO :[{}]成功!", c.getName());
	}

	public MappedStatement toGeneratedMappedStatement(MappedStatement ms, Object... args)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, InstantiationException {

		String sqq = null;
		Iterator<Entry<Generated, Columns>> iterator = generateds.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Generated, Columns> entry = iterator.next();
			String methods = entry.getKey().value();
			String action = methods.substring(0, methods.lastIndexOf("."));
			String method = methods.substring(methods.lastIndexOf(".") + 1);
			Class<?> a = Thread.currentThread().getContextClassLoader().loadClass(action);
			Method[] mss = a.getMethods();
			for (Method m : mss) {
				if (m.getName().equals(method)) {
					Class<?>[] s = m.getParameterTypes();
					if (s.length == args.length) {
						if (s[0].equals(args[0].getClass())) {
							sqq = (String) m.invoke(a.newInstance(), args);
							break;
						}
					}
				}
			}
		}

		List<ResultMapping> newLi = new ArrayList<>();
		List<ResultMap> ls = new ArrayList<>();
		ls.add(new ResultMap.Builder(ms.getConfiguration(), ms.getId() + "!SelectKey-Inline", Map.class, newLi)
				.build());
		SqlNode sn = new StaticTextSqlNode(sqq);
		SqlSource sqls = new DynamicSqlSource(ms.getConfiguration(), sn);
		SelectKeyGenerator s = new SelectKeyGenerator(
				new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), sqls, ms.getSqlCommandType())
						.keyProperty("userid,account").resultMaps(ls).build(),
				true);
		return new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), ms.getSqlSource(), ms.getSqlCommandType())
				.keyGenerator(s).build();
	}

	/**
	 * 转化为mybatis所用的ResultMappingList
	 * 
	 * @param configuration
	 * @return
	 */
	public List<ResultMapping> toResultMappingList(Configuration configuration) {
		List<ResultMapping> list = new ArrayList<>();

		columns.forEach(c -> {
			ResultMapping.Builder b = new ResultMapping.Builder(configuration,
					c.autoMap != null && StringUtils.isNotBlank(c.autoMap.property()) ? c.autoMap.property()
							: c.field.getName(),
					c.autoMap != null && StringUtils.isNotBlank(c.autoMap.column()) ? c.autoMap.column()
							: c.field.getName(),
					c.field.getType());
			if (c.autoMap != null) {
				if (StringUtils.isNotBlank(c.autoMap.many().select())) {
					list.add(b.nestedQueryId(c.autoMap.many().select())
							.lazy(c.autoMap.many().fetchType().equals(FetchType.LAZY)).build());
					return;
				} else if (StringUtils.isNotBlank(c.autoMap.one().select())) {
					list.add(b.nestedQueryId(c.autoMap.one().select())
							.lazy(c.autoMap.one().fetchType().equals(FetchType.LAZY)).build());
					return;
				} else {
					throw new NullPointerException("  @Column is erro :" + c.field.getName());
				}
			}
		});

		return list;
	}

	/**
	 * 取得主键
	 * 
	 * @param t
	 * @return
	 */
	public <T extends SNFPOJO> List<Entry<String, Object>> toPrimaryKey(T t) {
		Objects.requireNonNull(t, "");
		List<Entry<String, Object>> li = new ArrayList<>(primaryKeys.size());
		primaryKeys.forEach((a, v) -> {
			try {
				Field f = v.field;
				f.setAccessible(true);
				li.add(new cn.yuyizyk.ground.model.entity.Entry<String, Object>(f.getName(), f.get(t)));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				log.error("", e);
			}
		});
		return li;
	}

	public static class Columns {
		final Field field;
		final AutoMap autoMap;
		final PrimaryKey primaryKey;
		final Generated generated;

		public Columns(Field field, AutoMap autoMap, PrimaryKey primaryKey, Generated generated) {
			this.field = field;
			this.autoMap = autoMap;
			this.primaryKey = primaryKey;
			this.generated = generated;
		}
	}

	public Set<Columns> getColumns() {
		return columns;
	}

	public void setColumns(Set<Columns> columns) {
		this.columns = columns;
	}

	public Map<AutoMap, Columns> getAutoMaps() {
		return autoMaps;
	}

	public void setAutoMaps(Map<AutoMap, Columns> autoMaps) {
		this.autoMaps = autoMaps;
	}

	public Map<PrimaryKey, Columns> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(Map<PrimaryKey, Columns> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public Map<IndexKey, Columns> getIndexKeys() {
		return IndexKeys;
	}

	public void setIndexKeys(Map<IndexKey, Columns> indexKeys) {
		IndexKeys = indexKeys;
	}

	public Map<Generated, Columns> getGenerateds() {
		return generateds;
	}

	public void setGenerateds(Map<Generated, Columns> generateds) {
		this.generateds = generateds;
	}

	public Map<Field, Columns> getFields() {
		return fields;
	}

	public void setFields(Map<Field, Columns> fields) {
		this.fields = fields;
	}

	public Class<? extends POJO> getType() {
		return cls;
	}

	public void setType(Class<? extends POJO> cls) {
		this.cls = cls;
	}

	public Table getTableInfo() {
		return tableInfo;
	}

	public void setTableInfo(Table tableInfo) {
		this.tableInfo = tableInfo;
	}

}
