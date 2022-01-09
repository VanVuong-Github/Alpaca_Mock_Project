package com.alpaca.Alpaca_Mock_Project.controller;

import com.alpaca.Alpaca_Mock_Project.cacheService.impl.CustomerCacheServiceImpl;
import com.alpaca.Alpaca_Mock_Project.dto.CustomerDto;
import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import com.alpaca.Alpaca_Mock_Project.mapper.CustomerMapper;
import com.alpaca.Alpaca_Mock_Project.service.impl.CustomerServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerServiceImpl customerService;

    private final CustomerCacheServiceImpl customerCacheService;

    private final CustomerMapper customerMapper;

    public CustomerController(CustomerServiceImpl customerService, CustomerCacheServiceImpl customerCacheService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerCacheService = customerCacheService;
        this.customerMapper = customerMapper;
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<Object> customerCacheList = customerCacheService.getAllCache();
        // check cache
        if (!customerCacheList.isEmpty())
            return ResponseEntity.ok().body(customerCacheList);
        return ResponseEntity.ok().body(mapCustomerToCustomerDto(customerService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") final Long id){
        Object customerCache = customerCacheService.getCacheById(id);
        // check cache
        if (customerCache != null)
            return ResponseEntity.ok().body(customerCache);
        return ResponseEntity.ok().body(mapCustomerToCustomerDto(customerService.getCustomerById(id)));
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody final CustomerDto customerDto){
        Customer customer = customerService.saveCustomer(customerDto);
        // put cache
        customerCacheService.putCache(customer);
        return ResponseEntity.ok().body(mapCustomerToCustomerDto(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody final CustomerDto customerDto,
                                   @PathVariable("id") final Long id){
        Customer customer = customerService.updateCustomer(customerDto, id);
        // put cache
        customerCacheService.putCache(customer);
        return ResponseEntity.ok().body(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") final Long id){
        customerService.deleteCustomerById(id);
        // remove cache
        customerCacheService.deleteCache(id);
        return ResponseEntity.ok().body("Customer Deleted!");
    }

    private List<CustomerDto> mapCustomerToCustomerDto(List<Customer> customerList){
        return customerList.stream().map(customerMapper::customerToCustomerDto).collect(Collectors.toList());
    }

    private CustomerDto mapCustomerToCustomerDto(Customer customer){
        return CustomerMapper.INSTANCE.customerToCustomerDto(customer);
    }
}
