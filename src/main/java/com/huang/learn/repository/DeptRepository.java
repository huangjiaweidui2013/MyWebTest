package com.huang.learn.repository;

import com.huang.learn.domain.Dept;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeptRepository extends MongoRepository<Dept, Long> {
}
