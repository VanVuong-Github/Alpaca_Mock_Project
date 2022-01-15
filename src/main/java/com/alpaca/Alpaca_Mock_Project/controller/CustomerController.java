package com.alpaca.Alpaca_Mock_Project.controller;

import com.alpaca.Alpaca_Mock_Project.cacheService.impl.CustomerCacheServiceImpl;
import com.alpaca.Alpaca_Mock_Project.dto.CustomerDto;
import com.alpaca.Alpaca_Mock_Project.elasticsearchService.CustomerElasticsearchService;
import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import com.alpaca.Alpaca_Mock_Project.mapper.CustomerMapper;
import com.alpaca.Alpaca_Mock_Project.service.CustomerService;
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

    private final CustomerService customerService;

    private final CustomerCacheServiceImpl customerCacheService;

    public CustomerController(CustomerElasticsearchService customerElasticsearchService, CustomerService customerService, CustomerCacheServiceImpl customerCacheService) {
        this.customerElasticsearchService = customerElasticsearchService;
        this.customerService = customerService;
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

    /**
     * search API, search customer by name, email, phone with an input text
     * @param textToSearch
     * @param pageable
     * @return
     */
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
        Customer customer = customerService.saveCustomer(mapCustomerDtoToCustomer(customerDto));
        // put cache
        customerCacheService.putCache(customer);
        return ResponseEntity.ok().body(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody @Valid final CustomerDto customerDto,
                                   @PathVariable("id") @Min(1) final Long id){
        Customer customer = customerService.updateCustomer(mapCustomerDtoToCustomer(customerDto), id);
        // put cache
        customerCacheService.putCache(customer);
        return ResponseEntity.ok().body(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") @Min(1) final Long id){
        customerService.deleteCustomerById(id);
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
