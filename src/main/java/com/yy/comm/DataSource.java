package com.yy.comm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)      //SOURCE:在源文件中有效（即源文件保留）CLASS:在class文件中有效（即class保留）RUNTIME:在运行时有效（即运行时保留）
public @interface DataSource {
     String value() default CommonConst.DB_MASTER;
}
