package com.alpaca.Alpaca_Mock_Project.elasticsearchService.impl;

import com.alpaca.Alpaca_Mock_Project.cacheService.CustomerCacheService;
import com.alpaca.Alpaca_Mock_Project.dto.CustomerDto;
import com.alpaca.Alpaca_Mock_Project.elasticsearchRepository.CustomerElasticsearchRepository;
import com.alpaca.Alpaca_Mock_Project.elasticsearchService.CustomerElasticsearchService;
import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import com.alpaca.Alpaca_Mock_Project.mapper.CustomerMapper;
import com.alpaca.Alpaca_Mock_Project.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CustomerElasticsearchServiceImpl implements CustomerElasticsearchService {

    private static final Logger logger = Logger.getLogger(CustomerElasticsearchServiceImpl.class.getName());

    private final CustomerElasticsearchRepository customerElasticsearchRepository;

    private final CustomerService customerService;

    private final CustomerCacheService customerCacheService;

    private final CustomerMapper customerMapper;

    public CustomerElasticsearchServiceImpl(CustomerElasticsearchRepository customerElasticsearchRepository, CustomerService customerService, CustomerCacheService customerCacheService, CustomerMapper customerMapper) {
        this.customerElasticsearchRepository = customerElasticsearchRepository;
        this.customerService = customerService;
        this.customerCacheService = customerCacheService;
        this.customerMapper = customerMapper;
    }

    @Override
    @Transactional
    public Optional<Customer> getCustomerById(final Long id){
        logger.log(Level.INFO, "Get elasticsearch customer with id: {0}", id);
        Customer customer = new Customer();
        try {
            customer = customerElasticsearchRepository.findById(id).orElseThrow(NullPointerException::new);
        } catch (NullPointerException e){
            customer = create(customerService.getCustomerById(id));
        }
        // put to cache
        customerCacheService.putCache(customer);
        return Optional.of(customer);
    }

    @Override
    @Transactional
    public Page<CustomerDto> findAll(Pageable pageable){
        logger.log(Level.INFO, "Get all elasticsearch customer");
        List<Customer> customers = customerElasticsearchRepository.findAll();
        // check if it is empty, insert from db
        if (customers.isEmpty()) {
            customers = insertBulk(customerService.findAll());
        }
        putListToRedisMap(customers);
        return new PageImpl<>(mapCustomerToCustomerDto(customers), pageable, customers.size());
    }

    @Override
    @Transactional
    public Page<CustomerDto> search(String textToSearch, Pageable pageable){
        logger.log(Level.INFO, "Search all elasticsearch customer by name, email, phone");
        List<Customer> customers = customerElasticsearchRepository.findAllByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContainingIgnoreCase(textToSearch, textToSearch, textToSearch);
        // check if it is empty, insert from db
        if (customers.isEmpty()) {
            customers = insertBulk(customerService.search(textToSearch));
        }
        putListToRedisMap(customers);
        return new PageImpl<>(mapCustomerToCustomerDto(customers), pageable, customers.size());
    }

    @Override
    @Transactional
    public List<Customer> insertBulk(final List<Customer> customerList){
        logger.log(Level.INFO, "Insert bulk elasticsearch customer");
        return Streamable.of(customerElasticsearchRepository.saveAll(customerList)).toList();
    }

    @Override
    @Transactional
    public void delete(final Long id){
        logger.log(Level.INFO, "Delete elasticsearch customer with id: {0}", id);
        customerService.deleteCustomerById(id);
        customerElasticsearchRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Customer create(final Customer customer){
        logger.log(Level.INFO, "Create new elasticsearch customer");
        customerService.saveCustomer(customer);
        return customerElasticsearchRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer update(final Customer customer, final Long id) {
        logger.log(Level.INFO, "Update elasticsearch customer");
        Customer customerUpdated = customerService.updateCustomer(customer, id);
        return customerElasticsearchRepository.save(customerUpdated);
    }

    private void putListToRedisMap(List<Customer> customers){
        // create customerMap for caching
        Map<Object, Object> customerMap = new HashMap<>();
        for (Customer customer:customers) {
            customerMap.put(customer.getId(), customer);
        }
        // put all map to cache
        customerCacheService.putAllCache(customerMap);
    }

    private List<CustomerDto> mapCustomerToCustomerDto(List<Customer> customerList){
        return customerList.stream().map(customerMapper::customerToCustomerDto).collect(Collectors.toList());
    }

}
