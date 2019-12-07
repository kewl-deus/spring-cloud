package com.example.customerservice.repository;

import com.example.customerservice.model.entity.CustomerIndexRecord;
import org.springframework.data.repository.CrudRepository;

public interface CustomerIndexRepository extends CrudRepository<CustomerIndexRecord, Long> {
}
