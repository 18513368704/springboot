package com.yy.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Test {

    @RequestMapping("/index")
    public String test(){
        System.out.println("111111111111111");
        return "/hello";
    }

    @RequestMapping("/index2")
    public String test2(){
        System.out.println("111111111111111");
        return "/hello2";
    }

    @RequestMapping("/index3")
    public String test3(){
        System.out.println("111111111111111");
        return "/hello3";
    }
}
