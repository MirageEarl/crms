package com.vifu.crms.controller;/**
 * Created by mirag on 2017/2/16.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author TangBo
 * @create 2017-02-16 17:20
 **/
@Controller
@RequestMapping("/vue")
public class HelloVueController {

    @RequestMapping("/hello")
    public String hello() {
        return "/hello/hello";
    }

    @RequestMapping("/assembly")
    public String assembly() {
        return "/hello/assembly";
    }
}
