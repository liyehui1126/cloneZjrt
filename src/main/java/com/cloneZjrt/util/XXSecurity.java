package com.cloneZjrt.util;

import java.lang.annotation.*;

/**
 * 权限验证注解，可以加在类或方法上
 * 方法上的优先
 * 默认值是该方法所有人都可以访问
 */
//类和方法上生效
@Target({ElementType.METHOD,ElementType.TYPE})
//运行时有效
@Retention(RetentionPolicy.RUNTIME)
//在javadoc文档中使用（这个不重要）
@Documented
public @interface XXSecurity {
    long [] value() default {};
}
