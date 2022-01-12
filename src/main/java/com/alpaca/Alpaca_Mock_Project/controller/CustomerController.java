package com.alpaca.Alpaca_Mock_Project.controller;

import com.alpaca.Alpaca_Mock_Project.cacheService.impl.CustomerCacheServiceImpl;
import com.alpaca.Alpaca_Mock_Project.dto.CustomerDto;
import com.alpaca.Alpaca_Mock_Project.elasticsearchService.CustomerElasticsearchService;
import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import com.alpaca.Alpaca_Mock_Project.mapper.CustomerMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerElasticsearchService customerElasticsearchService;

    private final CustomerCacheServiceImpl customerCacheService;

    public CustomerController(CustomerElasticsearchService customerElasticsearchService, CustomerCacheServiceImpl customerCacheService) {
        this.customerElasticsearchService = customerElasticsearchService;
        this.customerCacheService = customerCacheService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(Pageable pageable){
        Page<Customer> customerCacheList = customerCacheService.getAllCache(pageable);
        // check cache
        if (!customerCacheList.getContent().isEmpty())
            return ResponseEntity.ok().body(customerCacheList);
        return ResponseEntity.ok().body(customerElasticsearchService.findAll(pageable));
    }

    @GetMapping("/searchBy")
    public ResponseEntity<?> search(@RequestParam("textToSearch") @NotBlank String textToSearch,
                                    Pageable pageable){
        return ResponseEntity.ok().body(customerElasticsearchService.search(textToSearch, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") @Min(1) final Long id){
        Customer customerCache = customerCacheService.getCacheById(id);
        // check cache
        if (customerCache != null)
            return ResponseEntity.ok().body(customerCache);
        Customer customer = customerElasticsearchService.getCustomerById(id).orElseThrow(NullPointerException::new);
        return ResponseEntity.ok().body(mapCustomerToCustomerDto(customer));
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody @Valid final CustomerDto customerDto){
        Customer customer = customerElasticsearchService.create(mapCustomerDtoToCustomer(customerDto));
        // put cache
        customerCacheService.putCache(customer);
        return ResponseEntity.ok().body(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody @Valid final CustomerDto customerDto,
                                   @PathVariable("id") @Min(1) final Long id){
        Customer customer = customerElasticsearchService.update(mapCustomerDtoToCustomer(customerDto), id);
        // put cache
        customerCacheService.putCache(customer);
        return ResponseEntity.ok().body(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") @Min(1) final Long id){
        customerElasticsearchService.delete(id);
        // remove cache
        customerCacheService.deleteCache(id);
        return ResponseEntity.ok().body("Customer Deleted!");
    }

    private CustomerDto mapCustomerToCustomerDto(Customer customer){
        return CustomerMapper.INSTANCE.customerToCustomerDto(customer);
    }

    private Customer mapCustomerDtoToCustomer(CustomerDto customerDto){
        return CustomerMapper.INSTANCE.customerDtoToCustomer(customerDto);
    }
}
