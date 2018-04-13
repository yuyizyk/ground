package cn.yuyizyk.ground.mapper.interceptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import cn.yuyizyk.ground.mapper.parser.PojoDecoratorFactory;
import cn.yuyizyk.ground.model.pojo.base.POJO;

/**
 * 查询sql 拦截器 <br/>
 * 修改select的返回值
 * 
 * @author yuyi
 *
 */
@Intercepts(@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }))
public class ResultTypeInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		final Object[] args = invocation.getArgs();
		/**
		 * MappedStatement是一个共享的缓存对象，这个对象是存在并发问题的，所以几乎任何情况下都不能去修改这个对象（通用Mapper除外），
		 * 想要对MappedStatement做修改该怎么办呢？ 并不难，Executor中的拦截器方法参数中都有MappedStatement
		 * ms，这个ms就是后续方法执行要真正用到的MappedStatement，这样一来，问题就容易解决了，根据自己的需要，
		 * 深层复制MappedStatement对象中自己需要修改的属性，然后修改这部分属性，之后将修改后的ms通过上面代码中args[0]=ms这种方式替换原有的参数，
		 * 这样就能实现对ms的修改而且不会有并发问题了。
		 */
		MappedStatement ms = (MappedStatement) args[0];
		Object parameterObject = args[1];
		// 获取参数中设置的返回值类型
		Class<?> resultType = getResultType(parameterObject);
		if (resultType == null) {
			resultType = ms.getResultMaps().get(0).getType();
			if (!POJO.class.isAssignableFrom(resultType)) {
				return invocation.proceed();
			}
		}
		// 复制ms，重设类型
		args[0] = newMappedStatement(ms, resultType);
		return invocation.proceed();
	}

	@SuppressWarnings("unchecked")
	private Object newMappedStatement(MappedStatement ms, Class<?> resultType) {
//		List<ResultMap> map = ms.getResultMaps(), resultMap = new ArrayList<ResultMap>();
//		map.forEach(a -> {
//			if (POJO.class.isAssignableFrom(a.getType()) && a.getType().isAssignableFrom(resultType)) {
//				resultMap
//						.add(new ResultMap.Builder(
//								ms.getConfiguration(), a.getId(), resultType, PojoDecoratorFactory.operation()
//										.toResultMappingList((Class<? extends POJO>) resultType, ms.getConfiguration()),
//								a.getAutoMapping()).build());
//			} else {
//				resultMap.add(a);
//			}
//		});
//		return new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), ms.getSqlSource(), ms.getSqlCommandType())
//				.resultMaps(resultMap).build();
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
	}

}
