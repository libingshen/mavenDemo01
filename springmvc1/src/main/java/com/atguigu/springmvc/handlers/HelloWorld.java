package com.atguigu.springmvc.handlers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloWorld {
    /**
     * 1、使用@RequestMapping注解来映射请求的URL
     * 2、返回值会通过视图解析器解析为实际的物理视图，对于InternalResourceViewResolver视图解析器，会做如下解析：
     * prefix+returnVal+suffix这样的方式得到实际的物理视图，然后做转发操作
     * <p>
     * /WEB-INF/views/success.jsp
     *
     * @return
     */
    @RequestMapping(value = "/helloworld", method = RequestMethod.GET)
    public String hello() {
        System.out.println("hello world");
        return "success";
    }

    /*@RequestMapping("/index")
    public String index() {
        System.out.println("index...");
        return "index";
    }*/
}
