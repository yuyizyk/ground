package cn.yuyizyk.ground.util.apidoc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.yuyizyk.ground.util.apidoc.http.ContentType;
import cn.yuyizyk.ground.util.apidoc.http.Method;

/**
 * 备注
 * 
 * @author yuyi
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE, ElementType.METHOD })
public @interface Remark {
	String value() default "";

	String path() default "";

	ContentType contentType() default ContentType.application_x_www_form_urlencoded;

	int method() default Method.POST & Method.GET;

	Reply reply() default @Reply;

	Ask ask() default @Ask;
}
