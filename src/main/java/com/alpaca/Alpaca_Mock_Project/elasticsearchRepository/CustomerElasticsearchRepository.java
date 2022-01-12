package com.alpaca.Alpaca_Mock_Project.elasticsearchRepository;

import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerElasticsearchRepository extends ElasticsearchRepository<Customer, Long> {
    List<Customer> findAll();
    List<Customer> findAllByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContainingIgnoreCase(String name, String email, String phone);
}
