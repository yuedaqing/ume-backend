package com.yue.ume.es.service.impl;

import cn.hutool.json.JSONUtil;
import com.yue.ume.es.model.Post;
import com.yue.ume.es.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class PostServiceImplTest {
    @Resource
    private PostService postService;

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @AfterEach
    void tearDown() {
    }

    @Test
    void save() {
        Post post = new Post();
        post.setId(1);
        post.setTitle("SpringData Post");
        post.setContent("Spring Data Post 基于 spring data API 简化 elasticSearch操作，将原始操作elasticSearch的客户端API 进行封装 \n" +
                "    Spring Data为Elasticsearch Elasticsearch项目提供集成搜索引擎");
        postService.save(post);
    }

    @Test
    void findById() {
        Post byId = postService.findById(1);
        System.out.println(JSONUtil.toJsonStr(byId));
    }

    @Test
    void deleteById() {
        postService.deleteById(100);

    }

    @Test
    void count() {
        long count = postService.count();
        System.out.println(count);
    }

    @Test
    void existsById() {
        boolean b = postService.existsById(102);

        System.out.println(b);
    }

    @Test
    void findByTitleOrContent() {
        List<SearchHit<Post>> byTitleOrContent = postService.findByTitleOrContent("xxxxxxSpringData", "elasticSearch");
        for (SearchHit<Post> postService : byTitleOrContent) {
            List<String> title = postService.getHighlightField("title");
            System.out.println(title);
            List<String> content = postService.getHighlightField("content");
            System.out.println(content);

        }
    }
}