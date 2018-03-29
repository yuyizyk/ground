package cn.yuyizyk.ground.util.apidoc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import cn.yuyizyk.ground.util.apidoc.http.ContentType;

/**
 * 响应
 * 
 * @author yuyi
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Reply {
	Parameter[] cookies() default {};

	Parameter[] parameters() default {};

	ContentType contentType() default ContentType.application_json;

}
