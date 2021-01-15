package com.redis.demo.controller;

import com.redis.demo.bean.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * springmvc接收json参数的四种方法
 */
@Controller
public class RequestParamController {
    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     *    1、以RequestParam接收
     *     前端传来的是json数据不多时：[id:id],可以直接用@RequestParam来获取值
     */
    @RequestMapping(value = "/updateId")
    @ResponseBody
    public String updateAttr(@RequestParam("id") int id) {
        logger.info(id+"");
        return id+"";
    }

    /**
     * 2、以实体类方式接收
     * 前端传来的是一个json对象时：{【id，name】},可以用实体类直接进行自动绑定
     * @param employee
     * @return
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object addObj(@RequestBody Employee employee) {
        return employee;
    }

    /**
     * 3、以Map接收
     * 前端传来的是一个json对象时：{【id，name】},可以用Map来获取
     * @param map
     * @return
     */
    @RequestMapping(value = "/updateMap")
    @ResponseBody
    public String updateAttr(@RequestBody Map<String, String> map) {
        if(map.containsKey("id")){
            Integer id = Integer.parseInt(map.get("id"));
        };
        if(map.containsKey("name")){
            String objname = map.get("name").toString();
        }
        return map.get("id")+"  "+map.get("name");
    }

    /**
     * 4、以List接收
     * 当前端传来这样一个json数组：[{id,name},{id,name},{id,name},...]时，用List<E>接收
     * @param list
     * @return
     */
    @RequestMapping(value = "/updateList")
    @ResponseBody
    public String updateAttr(@RequestBody List<Employee> list) {
        for(Employee employee:list){
            System.out.println(employee.toString());
        }
        return "success";
    }

}
