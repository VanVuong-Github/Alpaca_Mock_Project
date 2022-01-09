package com.alpaca.Alpaca_Mock_Project.service.impl;

import com.alpaca.Alpaca_Mock_Project.cacheService.CustomerCacheService;
import com.alpaca.Alpaca_Mock_Project.dto.CustomerDto;
import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import com.alpaca.Alpaca_Mock_Project.mapper.CustomerMapper;
import com.alpaca.Alpaca_Mock_Project.repository.CustomerRepository;
import com.alpaca.Alpaca_Mock_Project.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = Logger.getLogger(CustomerServiceImpl.class.getName());

    private final CustomerRepository customerRepository;

    private final CustomerCacheService customerCacheService;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerCacheService customerCacheService) {
        this.customerRepository = customerRepository;
        this.customerCacheService = customerCacheService;
    }

    @Override
    @Transactional
    public Customer getCustomerById(final Long id){
        logger.log(Level.INFO, "Get customer with id: {0}", id);
        Customer customer = customerRepository.findById(id).orElseThrow(NullPointerException::new);
        // put to cache
        customerCacheService.putCache(customer);
        return customer;
    }

    @Override
    @Transactional
    public List<Customer> findAll(){
        logger.log(Level.INFO, "Get all customer");
        List<Customer> customerList = customerRepository.findAll();
        // create customerMap for caching
        Map<Object, Object> customerMap = new HashMap<>();
        for (Customer customer:customerList) {
            customerMap.put(customer.getId(), customer);
        }
        // put all map to cache
        customerCacheService.putAllCache(customerMap);
        return customerList;
    }

    @Override
    @Transactional
    public Customer saveCustomer(final CustomerDto customerDto){
        logger.log(Level.INFO, "Save new customer");
        Customer customer = CustomerMapper.INSTANCE.customerDtoToCustomer(customerDto);
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer updateCustomer(final CustomerDto customer, final Long id){
        logger.log(Level.INFO, "Update customer with id: {0}", id);
        Customer oldCustomer = customerRepository.findById(id).orElseThrow(NullPointerException::new); // this is the customer before update
        oldCustomer.setName(customer.getName());
        oldCustomer.setGender(customer.getGender());
        oldCustomer.setCardId(customer.getCardId());
        oldCustomer.setPhone(customer.getPhone());
        oldCustomer.setEmail(customer.getEmail());
        oldCustomer.setDateOfBirth(customer.getDateOfBirth());
        oldCustomer.setAddress(customer.getAddress());
        oldCustomer.setOccupation(customer.getOccupation());
        return customerRepository.save(oldCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomerById(final Long id){
        logger.log(Level.INFO, "Delete customer with id: {0}", id);
        Customer oldCustomer = customerRepository.findById(id).orElseThrow(NullPointerException::new);
        customerRepository.delete(oldCustomer);
    }
}
