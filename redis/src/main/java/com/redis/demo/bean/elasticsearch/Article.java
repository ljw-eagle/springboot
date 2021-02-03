package com.redis.demo.bean.elasticsearch;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@Document(indexName = "test-search",indexStoreType = "article",shards = 1,replicas = 0, refreshInterval = "-1")
public class Article implements Serializable {
    @Id
    private Long id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title; //标题

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;// 缩略内容

    public Article(long id, String title, String content) {
    }

}


