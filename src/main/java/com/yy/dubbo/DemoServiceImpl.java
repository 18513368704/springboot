package com.yy.dubbo;


import org.apache.dubbo.config.annotation.Service;

@Service
public class DemoServiceImpl implements  DemoService {
    @Override
    public String sayHello(Sub sub) {
        return  sub.toString();

    }
}
