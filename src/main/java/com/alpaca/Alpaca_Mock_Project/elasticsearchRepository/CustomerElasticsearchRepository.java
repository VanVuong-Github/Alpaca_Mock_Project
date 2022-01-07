package com.alpaca.Alpaca_Mock_Project.elasticsearchRepository;

import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerElasticsearchRepository extends ElasticsearchRepository<Customer, Long> {
}
