package cn.yuyizyk.ground.model.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;

/**
 * 用于mybatis 生成自动ResultMap
 * @author yuyi
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Column {

	/**
	 * 当前记录中传入参数
	 * @return
	 */
	String fieldName() default "";

	Many many() default @Many;

	One one() default @One;
}
