package com.alpaca.Alpaca_Mock_Project.elasticsearchService;

import com.alpaca.Alpaca_Mock_Project.elasticsearchRepository.CustomerElasticsearchRepository;
import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerElasticsearchService {

    private final CustomerElasticsearchRepository customerElasticsearchRepository;

    public CustomerElasticsearchService(CustomerElasticsearchRepository customerElasticsearchRepository) {
        this.customerElasticsearchRepository = customerElasticsearchRepository;
    }

    public Optional<Customer> getCustomerById(final Long id){
        return customerElasticsearchRepository.findById(id);
    }

    public Iterable<Customer> findAll(){
        return customerElasticsearchRepository.findAll();
    }

    public Iterable<Customer> insertBulk(final List<Customer> customerList){
        return customerElasticsearchRepository.saveAll(customerList);
    }

    public void delete(final Long id){
        customerElasticsearchRepository.deleteById(id);
    }

    public Customer create(final Customer customer){
        return customerElasticsearchRepository.save(customer);
    }
}
