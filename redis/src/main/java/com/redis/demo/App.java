package com.redis.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan(basePackages = "com.redis.demo.mapper")
@EnableJms  //启动消息队列
public class App {

    public static void main( String[] args ){

        SpringApplication.run(App.class, args);

    }
}
