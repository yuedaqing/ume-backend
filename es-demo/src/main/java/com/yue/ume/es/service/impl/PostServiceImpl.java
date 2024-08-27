package com.yue.ume.es.service.impl;

import com.yue.ume.es.datasource.PostDataSource;
import com.yue.ume.es.model.Post;
import com.yue.ume.es.service.PostService;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YueYue
 */
@Service
public class PostServiceImpl implements PostService {


    @Resource
    private PostDataSource postDataSource;


    @Override
    public void save(Post post) {
        postDataSource.save(post);
    }

    @Override
    public Post findById(Integer id) {
        return postDataSource.findById(id).orElse(new Post());
    }

    @Override
    public void deleteById(Integer id) {
        postDataSource.deleteById(id);
    }

    @Override
    public long count() {
        return postDataSource.count();
    }

    @Override
    public boolean existsById(Integer id) {
        return postDataSource.existsById(id);
    }

    @Override
    public List<SearchHit<Post>> findByTitleOrContent(String title, String content) {
        return postDataSource.findByTitleOrContent(title, content);
    }
}
