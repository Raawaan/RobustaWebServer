package com.rawan.robusta.request.mapping;

import com.rawan.robusta.request.data.Method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RequestHandler {
   Method method() default Method.GET;
   String url() default "";
}
