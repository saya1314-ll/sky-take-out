package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j

public class AutoFillAspect {
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")//定义切入点
    public void autoFillPointCut() {

    }
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段自动填充");


        //获取到当前被拦截的方法上的数据库操作类型(是update还是insert)
//        Signature signature = joinPoint.getSignature();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//获得方法上的注解对象
        OperationType operationType = autoFill.value();//获得数据库操作类型(就是AutoFill(value=OperationType.UPDATE)中的update类型)


        //获取到当前被拦截的方法参数(实体对象,是category还是employee)
        Object[] args = joinPoint.getArgs();//获取方法参数;
        if(args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];


        //准备给公共属性赋值的数据
        LocalDateTime now=LocalDateTime.now();
        Long currentId= BaseContext.getCurrentId();//当前登录用户的Id(利用线程实现的)


        //根据当前不同的操作类型,为对应的属性赋值(通过反射)
        if(operationType == OperationType.INSERT) {
            //为四个公共字段赋值
            try {
                Method setCreateTime = entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod("setCreateUser", Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);

                //通过反射为对象属性赋值
                setCreateTime.invoke(entity,now);
                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);
                setCreateUser.invoke(entity,currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if(operationType == OperationType.UPDATE) {
            //update只需要为两个公共字段赋值
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);

                setUpdateTime.invoke(entity,now);
                setUpdateUser.invoke(entity,currentId);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }
}
