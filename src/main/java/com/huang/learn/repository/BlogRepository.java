package com.huang.learn.repository;

import com.huang.learn.domain.Blog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BlogRepository extends MongoRepository<Blog, Long> {
}
