package com.redis.demo.controller.elasticsearch;

import com.redis.demo.utils.elasticsearch.ESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EsController {
    @Autowired
    private ESUtil esUtil;

    @RequestMapping("/addDoc")
    public void addDoc(){
        String es_index="test_es";
        Map<String, Object> param = new HashMap<String,Object>();
        param.put("1","hello world");
        param.put("2","welcome elasticsearch");
        try {
            esUtil.addDoc(es_index,param);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/findByIndex_id")
    public Map<String,Object> findByIndex_id(){
        try {
            return esUtil.findByIndex_id("test_es","S6GBYncBtczrj74L0UW6");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
