package cn.yuyizyk.ground.model.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.yuyizyk.ground.model.constant.Sort;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface OrderFilde {

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
