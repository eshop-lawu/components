package com.lawu.eshop.compensating.transaction.annotation;

import java.lang.annotation.*;

/**
 * 补偿性事务从逻辑注解
 * @author Leach
 * @date 2017/3/28
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CompensatingTransactionFollow {

    String topic();

    String tags() default "";
}
