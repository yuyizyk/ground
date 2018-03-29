package cn.yuyizyk.ground.mapper.interceptor;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.yuyizyk.ground.core.config.ApplicationDataConfig;
import cn.yuyizyk.ground.model.annotations.AutoMap;
import cn.yuyizyk.ground.model.annotations.Generated;
import cn.yuyizyk.ground.model.pojo.base.POJO;

/**
 * 对DMLSQL 进行AOP
 * 
 * @author yuyi
 *
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class DMLInterceptor implements Interceptor {
	private final static Logger log = LoggerFactory.getLogger(DMLInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		/**
		 * MappedStatement是一个共享的缓存对象，这个对象是存在并发问题的，所以几乎任何情况下都不能去修改这个对象（通用Mapper除外），
		 * 想要对MappedStatement做修改该怎么办呢？ 并不难，Executor中的拦截器方法参数中都有MappedStatement
		 * ms，这个ms就是后续方法执行要真正用到的MappedStatement，这样一来，问题就容易解决了，根据自己的需要，
		 * 深层复制MappedStatement对象中自己需要修改的属性，然后修改这部分属性，之后将修改后的ms通过上面代码中args[0]=ms这种方式替换原有的参数，
		 * 这样就能实现对ms的修改而且不会有并发问题了。
		 */
		Object[] args = invocation.getArgs();
		MappedStatement ms = (MappedStatement) args[0];
		Object parameterObject = args[1];
		// 获取参数中设置的返回值类型
		Class<?> resultType = getResultType(parameterObject);
		if (resultType == null) {
			return invocation.proceed();
		}

		// 复制ms，重设
		args[0] = newMappedStatement(ms, resultType);

		return invocation.proceed();
	}

	private Object generated(MappedStatement ms, Class<?> resultType) {
		return ms;
	}

	private Object newMappedStatement(MappedStatement ms, Class<?> resultType) {
		if (!(POJO.class.isAssignableFrom(resultType))) {
			return ms;
		}
		List<ResultMap> ls = new ArrayList<>();

		List<Field> fields = Arrays.asList(resultType.getDeclaredFields());
		for (Field f : fields) {
			if ((f.getModifiers() & (java.lang.reflect.Modifier.STATIC)) == java.lang.reflect.Modifier.STATIC)
				continue;
			Generated g = f.getAnnotation(Generated.class);
			if (g != null) {
				try {
					// 自动映射
					List<ResultMapping> newLi = new ArrayList<>();
					ls.add(new ResultMap.Builder(ms.getConfiguration(), ms.getId() + "!SelectKey-Inline", f.getType(),
							newLi).build());
					SqlSource sqls = new StaticSqlSource(ms.getConfiguration(), g.value());
					SelectKeyGenerator s = new SelectKeyGenerator(
							new MappedStatement.Builder(ms.getConfiguration(), ms.getId() + "!SelectKey", sqls,
									ms.getSqlCommandType()).keyProperty("userid").resultMaps(ls).build(),
							true);
					return new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), ms.getSqlSource(),
							ms.getSqlCommandType()).keyGenerator(s).build();
				} catch (IllegalArgumentException e) {
					// log.error("", e);
				}
			}
		}
		return ms;
	}

	@SuppressWarnings("unchecked")
	private Class<?> getResultType(Object parameterObject) {
		if (parameterObject instanceof POJO)
			return parameterObject.getClass();
		if (parameterObject instanceof Map) {
			Iterator<Entry<String, Object>> iterator = ((Map<String, Object>) parameterObject).entrySet().iterator();
			Entry<String, Object> entry;
			while (iterator.hasNext()) {
				entry = iterator.next();
				if (entry.getValue() instanceof POJO) {
					return entry.getValue().getClass();
				} else if (entry.getValue() instanceof Class<?>
						&& POJO.class.isAssignableFrom((Class<?>) entry.getValue())) {
					return (Class<?>) entry.getValue();
				}
			}
		}
		return null;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub

	}

}
