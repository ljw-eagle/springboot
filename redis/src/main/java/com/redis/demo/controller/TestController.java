package com.redis.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/testController")
public class TestController {
    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/demo1")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Object demo1() {
        int i = 1 / 0;
        return new Date();
    }

//    @ExceptionHandler({RuntimeException.class})
//    public ModelAndView fix(Exception ex){
//        System.out.println("do This");
//        return new ModelAndView("error",new ModelMap("ex",ex.getMessage()));
//    }

    /**
     * 异常页面控制
     *
     * @param runtimeException
     * @return
     */
//    @ExceptionHandler(RuntimeException.class)
//    public @ResponseBody
//    Map<String, Object> runtimeExceptionHandler(RuntimeException runtimeException) {
//        logger.error(runtimeException.getLocalizedMessage());
//        Map model = new TreeMap();
//        model.put("status", false);
//        return model;
//    }

}