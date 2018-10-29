package com.my.advice;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface SystemLogger {
    
    // String level() default "";
    
    LoggerType type();
    
    LoggerOptType operation();
    
    String desc();
    
}
