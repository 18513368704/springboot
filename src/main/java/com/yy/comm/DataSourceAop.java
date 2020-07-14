package com.yy.comm;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


@Aspect
@Component
public class DataSourceAop {
    private static Logger log = LoggerFactory.getLogger(DataSourceAop.class);
    /**
     * if...else...  判断哪些需要读从数据库，其余的走主数据库
     */
    @Before("execution(* com.yy.service.impl.*.*(..))")
    public void before(JoinPoint jp) {
        //获得当前访问的class
        Class<?> tagetClass = jp.getTarget().getClass();
        //获得访问的方法名
        String methodName = jp.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature)jp.getSignature()).getParameterTypes();
        // 得到访问的方法对象
        Method method = null;
        String dataSource = CommonConst.DB_MASTER;
        try {
            method = tagetClass.getMethod(methodName, argClass);
            if (method.isAnnotationPresent(DataSource.class)) {
                DataSource annotation = method.getAnnotation(DataSource.class);
                // 取出注解中的数据源名
                dataSource = annotation.value();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if(dataSource == null){
            if (StringUtils.startsWithAny(methodName, "get", "select", "find")) {
                DataSourceContextHolder.set(CommonConst.DB_SLAVE);
                System.out.println("切换到slave1");
            }else {
                DataSourceContextHolder.set(CommonConst.DB_MASTER);
                System.out.println("切换到master");
            }
        }else{
            DataSourceContextHolder.set(CommonConst.DB_MASTER);
            System.out.println("切换到master");
        }
    }

    @After("execution(* com.yy.service.impl.*.*(..))")
    public void after(JoinPoint jp) {
        DataSourceContextHolder.clearDataSource();
    }
}