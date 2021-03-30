package com.redis.demo.config.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: es配置 - 其它配置可以自行百度
 * @author: YeZhiJian
 * @create: 2021-01-08 10-14-25
 **/
@Configuration
public class EsConfig {

    /**
     *  重新注入RestHighLevelClient
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.2.102", 9200, "http")
                )
        );
        return client;
    }

}


