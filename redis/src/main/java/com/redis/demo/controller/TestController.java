package com.redis.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping("/testController")
public class TestController {

    @RequestMapping("/demo1")
    @ResponseBody
    public Object demo1(){
        int i = 1 / 0;
        return new Date();
    }

    @ExceptionHandler({RuntimeException.class})
    public ModelAndView fix(Exception ex){
        System.out.println("do This");
        return new ModelAndView("error",new ModelMap("ex",ex.getMessage()));
    }
}