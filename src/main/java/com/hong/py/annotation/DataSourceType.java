package com.hong.py.annotation;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.annotation.*;

/**
 * 文件描述
 *
 * @ProductName: HONGPY
 * @ProjectName: springbootdemo
 * @Package: com.hong.py.annotation
 * @Description: note
 * @Author: hongpy21691
 * @CreateDate: 2019/11/8 14:01
 * @UpdateUser: hongpy21691
 * @UpdateDate: 2019/11/8 14:01
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>
 * Copyright © 2019 hongpy Technologies Inc. All Rights Reserved
 **/
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceType {
    String value() default "first";
}
