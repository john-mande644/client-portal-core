package com.owd.web.api;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by stewartbuskirk1 on 5/4/14.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

public @interface Trace {
    public static final String NULL = "";
    String metricName() default NULL;
    boolean dispatcher() default false;
    String tracerFactoryName() default NULL;
}
