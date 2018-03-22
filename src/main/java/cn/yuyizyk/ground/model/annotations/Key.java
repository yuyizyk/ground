package cn.yuyizyk.ground.model.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 键（索引）
 * 
 * @author yuyi
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Key {

	/**
	 * 主键
	 */
	static int Primarykey = 0;
	/**
	 * 索引
	 */
	static int Index = 1;

	int value() default Primarykey;

	/**
	 * 优先级 0 为最小优先
	 * 
	 * @return
	 */
	int priority() default 0;

}
