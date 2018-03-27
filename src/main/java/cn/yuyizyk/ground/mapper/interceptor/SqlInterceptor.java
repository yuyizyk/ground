package cn.yuyizyk.ground.mapper.interceptor;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class SqlInterceptor implements Interceptor {

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
		Object parameter = args[1];
		Configuration configuration = ms.getConfiguration();
		Object target = invocation.getTarget();
		StatementHandler handler = configuration.newStatementHandler((Executor) target, ms, parameter,
				RowBounds.DEFAULT, null, null);
		BoundSql boundSql = handler.getBoundSql();

		// 执行真正的方法
		Object result = invocation.proceed();

		// TODO 还可以记录参数，或者单表id操作时，记录数据操作前的状态
		// 获取insertSqlLog方法
		// ms = ms.getConfiguration().getMappedStatement("insertSqlLog");
		// 替换当前的参数为新的ms
		// args[0] = ms;

		// 执行insertSqlLog方法
		// invocation.proceed();
		// 返回真正方法执行的结果
		return result;
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
