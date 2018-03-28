package cn.yuyizyk.ground.util.data;

import java.util.function.Function;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * 过滤
 * 
 * @author yuyi
 *
 */
public class ExclusionStrategyUtil {

	/**
	 * 常规过滤 <br/>
	 * 去除static 及transient 修饰 <br/>
	 * 
	 * @author yuyi
	 *
	 */
	public static class RegularOutput implements ExclusionStrategy {
		@Override
		public boolean shouldSkipField(FieldAttributes f) {
			return f.hasModifier(java.lang.reflect.Modifier.STATIC | java.lang.reflect.Modifier.TRANSIENT) ? true
					: false;
		}

		@Override
		public boolean shouldSkipClass(Class<?> clazz) {
			return org.apache.ibatis.javassist.util.proxy.MethodHandler.class.isAssignableFrom(clazz) ? true : false;
		}
	}

	/**
	 * 在常规输出的基础上过滤
	 * 
	 * @author yuyi
	 *
	 */
	public static final ExclusionStrategy filterByField(Function<FieldAttributes, Boolean> action) {
		return new RegularOutput() {
			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				return action.apply(f) && super.shouldSkipField(f);
			}
		};
	}
}
