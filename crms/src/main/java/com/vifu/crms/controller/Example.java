package com.vifu.crms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * vue的实例
 *
 * @author TangBo
 * @create 2017-02-20 10:20
 **/
@Controller
@RequestMapping("/vueE")
public class Example {

    @RequestMapping("example")
    public String example1(){
        return "/example/example";
    }
}
