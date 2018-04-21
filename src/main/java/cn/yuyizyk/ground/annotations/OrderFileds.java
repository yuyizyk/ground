package cn.yuyizyk.ground.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.yuyizyk.ground.constant.natural.Sort;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface OrderFileds {

	/**
	 * 排序方式
	 * 
	 * @return
	 */
	Sort orderType() default Sort.ASC;

	/**
	 * 优先权
	 * 
	 * @return
	 */
	int priority() default 0;
}
