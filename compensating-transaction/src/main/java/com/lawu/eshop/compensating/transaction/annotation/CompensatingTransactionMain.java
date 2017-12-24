package com.lawu.eshop.compensating.transaction.annotation;

import java.lang.annotation.*;

/**
 * 补偿性事务主逻辑注解
 * @author Leach
 * @date 2017/3/28
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CompensatingTransactionMain {

    byte value();

    int seconds() default 60;

    String topic();

    String tags() default "";
}
