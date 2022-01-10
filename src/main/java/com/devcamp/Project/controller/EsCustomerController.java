package com.devcamp.Project.controller;

import com.devcamp.Project.elasticSearchService.CustomerElasticSearchService;
import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.redisService.CustomerRedisService;
import com.devcamp.Project.repository.CustomerElasticSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/es/customer")
public class EsCustomerController {

    @Autowired
    CustomerElasticSearchService customerElasticSearchService;

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
