package com.yue.ume.annotation;

import org.springframework.stereotype.Component;

/**
 * @author yueyue
 */
@Component
public @interface VideoAnnotation {
    String value() default "123";
}
