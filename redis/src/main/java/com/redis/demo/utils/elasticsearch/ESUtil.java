package com.redis.demo.utils.elasticsearch;


import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description: es操作工具类 - 高等级客户端
 * @author: YeZhiJian
 * @create: 2021-01-08 14-09-54
 **/
@Component
public class ESUtil {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     *  插入 - 单条
     * @param es_index
     * @param param 如果不存入id字段，则会直接使用es的随机ID作为_id
     * @return
     */
    public boolean addDoc(String es_index, Map<String, Object> param) throws IOException {
        // 判断参数
        if (CollectionUtils.isEmpty(param)) return false;
        Set<String> keySet = param.keySet();
        if (keySet.size() <= 0) return false;
        // 创建连接
        IndexRequest indexRequest = new IndexRequest(es_index.toLowerCase());
        // 设置规则
        indexRequest.timeout("1s");
        // 判断是否存在key - 存在则直接作为es的_id
        if (param.containsKey("id")) indexRequest.id(String.valueOf(param.get("id")));
        // 设置插入数据
        indexRequest.source(param, XContentType.JSON);
        // 执行请求
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        if (indexResponse.getShardInfo().getSuccessful() != 1) return false;
        return true;
    }

    /**
     *  查询 - 获取指定index_id的数据
     * @param es_index
     * @param index_id
     * @return
     */
    public Map<String, Object> findByIndex_id(String es_index, String index_id) throws IOException {
        // 定义返回结果集
        Map<String, Object> resultMap = new HashMap<>();
        if (!StringUtils.hasText(index_id)) return resultMap;
        // 创建连接
        GetRequest getRequest = new GetRequest(es_index.toLowerCase(), index_id);
        // 执行请求
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        // 判断是否存在值
        if (getResponse.isSourceEmpty()) return resultMap;
        resultMap = getResponse.getSourceAsMap();
        resultMap.put("index_id", getResponse.getId()); // 填充es index_id
        return resultMap;
    }

    /**
     *  修改 - 指定index_id的数据
     * @param es_index
     * @param index_id
     * @param param
     * @return
     */
    public boolean updateByIndex_id(String es_index, String index_id, Map<String, Object> param) throws IOException {
        // 校验index_Id
        if (!StringUtils.hasText(index_id)) return false;
        if (!this.existsIndex_id(es_index, index_id)) return false;
        // 校验param
        if (CollectionUtils.isEmpty(param)) return false;
        Set<String> keySet = param.keySet();
        if (keySet.size() <= 0) return false;
        // 创建连接
        UpdateRequest updateRequest = new UpdateRequest(es_index.toLowerCase(), index_id);
        // 设置规则
        updateRequest.timeout("1s");
        // 设置修改数据
        updateRequest.doc(param);
        // 执行请求
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        // 判断结果
        if (updateResponse.getShardInfo().getSuccessful() != 1) return false;
        return true;
    }

    /**
     *  删除 - 指定index_id的数据
     * @param es_index
     * @param index_id
     * @return
     */
    public boolean deleteByIndex_id(String es_index, String index_id) throws IOException {
        // 校验index_Id
        if (!StringUtils.hasText(index_id)) return false;
        // 创建连接
        DeleteRequest deleteRequest = new DeleteRequest(es_index.toLowerCase(), index_id);
        // 设置规则
        deleteRequest.timeout("1s");
        // 执行请求
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        // 判断结果
        if (deleteResponse.getShardInfo().getSuccessful() != 1) return false;
        return true;
    }

    /**
     *  插入 - 批量
     * @param es_index
     * @param paramList
     * @return
     */
    public boolean bulkAddDoc(String es_index, List<Map<String, Object>> paramList) throws IOException {
        // 判断参数
        if (CollectionUtils.isEmpty(paramList)) return false;
        // 创建连接
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout(String.valueOf(paramList.size()) + "s"); // 使用插入量为超时时间
        // 遍历数据 - 设置到请求中
        for (Map<String, Object> map : paramList) {
            IndexRequest indexRequest = new IndexRequest(es_index.toLowerCase());
            if (map.containsKey("id")) indexRequest.id(String.valueOf(map.get("id")));
            indexRequest.source(map, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        // 执行请求
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        // 判断结果
        return !bulkResponse.hasFailures();
    }

    /**
     *  条件查询 - TODO 多条件/高亮/范围条件 业务需求再增加
     * @param es_index
     * @param keywordMap
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Map<String, Object> findListByKeyword(
            String es_index, String keywordKey, String keyword, int pageNo, int pageSize) throws IOException {
        // 定义响应结果集
        Map<String, Object> resultMap = new HashMap<>();

        // 创建连接
        SearchRequest searchRequest = new SearchRequest(es_index.toLowerCase());
        // 构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.timeout(new TimeValue(10, TimeUnit.SECONDS));

        // 分页
        searchSourceBuilder.from(pageNo <= 0 ? 1 : pageNo);
        searchSourceBuilder.size(pageSize <= 0 ? 10 : pageSize);

        // 匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(keywordKey, keyword);
        searchSourceBuilder.query(termQueryBuilder);

        // 执行搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        // 遍历获取数据
        resultMap.put("total", searchResponse.getHits().getTotalHits().value);
        List<Map<String, Object>> sourceMapList = new ArrayList<>();
        for (SearchHit searchHit : searchResponse.getHits().getHits())
            sourceMapList.add(searchHit.getSourceAsMap());
        resultMap.put("source", sourceMapList);
        return resultMap;
    }

    /**
     *  校验 - index_id是否存在
     * @param es_index
     * @param index_id
     * @return
     * @throws IOException
     */
    private boolean existsIndex_id(String es_index, String index_id) throws IOException {
        GetRequest getRequest = new GetRequest(es_index, index_id);
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        boolean exists = restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        if (!exists) return false;
        return true;
    }


}

