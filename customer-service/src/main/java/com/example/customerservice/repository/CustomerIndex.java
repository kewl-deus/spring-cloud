package com.example.customerservice.repository;

import com.example.customerservice.model.entity.CustomerIndexRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface CustomerIndex extends JpaRepository<CustomerIndexRecord, Long> {
}
