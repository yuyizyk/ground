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
public @interface IndexKey {

	/**
	 * 联合字段索引
	 */
	static int union = 1;
	/**
	 * 单字段索引
	 */
	static int alone = 0;

	/**
	 * 索引类型
	 * 
	 * @return
	 */
	int value() default alone;

	/**
	 * 优先级 0 为最小优先
	 * 
	 * @return
	 */
	int priority() default 0;

}
