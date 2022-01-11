package com.devcamp.Project.controller;

import com.devcamp.Project.elasticSearchService.CustomerElasticSearchService;
import com.devcamp.Project.entity.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/es/customer")
public class EsCustomerController {

    private final CustomerElasticSearchService customerElasticSearchService;

    public EsCustomerController(CustomerElasticSearchService customerElasticSearchService) {
        this.customerElasticSearchService = customerElasticSearchService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCustomer(){
        return new ResponseEntity<>(customerElasticSearchService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){
        return new ResponseEntity<>(customerElasticSearchService.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer){
        return new ResponseEntity<>(customerElasticSearchService.create(customer),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@Valid @PathVariable Long id, @RequestBody Customer customer){
        return new ResponseEntity<>(customerElasticSearchService.update(id, customer), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        return new ResponseEntity<>(customerElasticSearchService.delete(id), HttpStatus.NO_CONTENT);
    }
}
