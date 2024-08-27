package com.yue.ume.es.service;

import com.yue.ume.es.model.Post;
import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;

public interface PostService {

    //保存和修改
    void save(Post article);
    //查询id
    Post findById(Integer id);
    //删除指定ID数据
    void   deleteById(Integer id);

    long count();

    boolean existsById(Integer id);

    List<SearchHit<Post>> findByTitleOrContent(String title, String content);
}
