package cn.yuyizyk.ground.util.apidoc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 错误应答
 * 
 * @author yuyi
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Error {

	int state() default 400;

	String value();

	String remark() default "";
}
