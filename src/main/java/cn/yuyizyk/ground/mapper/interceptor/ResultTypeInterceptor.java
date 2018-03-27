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
		MappedStatement ms = (MappedStatement) args[0];

		Object parameterObject = args[1];
		// 获取参数中设置的返回值类型
		Class<?> resultType = getResultType(parameterObject);
		if (resultType == null) {
			return invocation.proceed();
		}
		// 复制ms，重设类型
		args[0] = newMappedStatement(ms, resultType);
		return invocation.proceed();
	}

	private Object newMappedStatement(MappedStatement ms, Class<?> resultType) {
		List<ResultMap> map = ms.getResultMaps(), resultMap = new ArrayList<ResultMap>();
		map.get(0).getType();
		map.forEach(a -> {
			if (POJO.class.equals(a.getType()) && a.getType().isAssignableFrom(resultType)) {
				resultMap.add(new ResultMap.Builder(ms.getConfiguration(), a.getId(), resultType, a.getResultMappings(),
						a.getAutoMapping()).build());
			} else {
				resultMap.add(a);
			}
		});
		return new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), ms.getSqlSource(), ms.getSqlCommandType())
				.resultMaps(resultMap).build();
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
