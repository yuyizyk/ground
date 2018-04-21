package cn.yuyizyk.ground.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;

/**
 * 用于mybatis 生成自动ResultMap
 * 
 * @author yuyi
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface AutoMap {

	/**
	 * pojo存值字段-一般为当前字段
	 * 
	 * @return
	 */
	String property() default "";

	/**
	 * 当前记录中传入参数字段
	 * 
	 * @return
	 */
	String column() default "";

	Many many() default @Many;

	One one() default @One;
}
