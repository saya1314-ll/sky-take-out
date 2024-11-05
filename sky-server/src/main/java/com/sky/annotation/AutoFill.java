package com.sky.annotation;


import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//自定义注解,用于标识某个方法需要自动填充
@Target(ElementType.METHOD)//该注释定义在方法上
@Retention(RetentionPolicy.RUNTIME)//在运行时执行
public @interface AutoFill {
    OperationType value();

}
