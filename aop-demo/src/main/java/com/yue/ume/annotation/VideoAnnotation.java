package com.yue.ume.annotation;

import org.springframework.stereotype.Component;

/**
 * @author YueYue
 */
@Component
public @interface VideoAnnotation {
    String value() default "123";
}
