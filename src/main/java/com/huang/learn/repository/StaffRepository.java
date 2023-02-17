package com.huang.learn.repository;

import com.huang.learn.domain.Staff;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StaffRepository extends MongoRepository<Staff, Long> {
}
