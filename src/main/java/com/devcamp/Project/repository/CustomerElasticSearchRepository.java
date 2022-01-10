package com.devcamp.Project.repository;

import com.devcamp.Project.entity.Customer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerElasticSearchRepository extends ElasticsearchRepository<Customer, Long> {
}
