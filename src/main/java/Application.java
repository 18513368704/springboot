package com.yy;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//咱也不知道,咱也不敢问,为啥提交不了,测试能不能打包,再来启动
@EnableDubbo
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Application {

   public static void main(String[] args){

       SpringApplication.run(Application.class,args);
   }
}



