package cn.yuyizyk.ground.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主键（主索引）
 * 
 * @author yuyi
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface PrimaryKey {

	/**
	 * 联合字段主键
	 */
	static int union = 1;
	/**
	 * 单字段主键
	 */
	static int alone = 0;

	/**
	 * 主键类型
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
