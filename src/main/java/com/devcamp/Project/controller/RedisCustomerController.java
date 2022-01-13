package com.devcamp.Project.controller;

import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.redisService.CustomerRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;

@RestController
@RequestMapping("/redis/customer")
public class RedisCustomerController {

    @Autowired
    CustomerRedisService customerRedisService;

    @GetMapping("/")
    public ResponseEntity<?> getAllCustomer(){
        return new ResponseEntity<>(customerRedisService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable Long id){
        return new ResponseEntity<>(customerRedisService.get(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer){
        return new ResponseEntity<>(customerRedisService.create(customer), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@Valid @PathVariable Long id, @RequestBody Customer customer){
        return new ResponseEntity<>(customerRedisService.update(id, customer), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        return new ResponseEntity<>(customerRedisService.delete(id), HttpStatus.NO_CONTENT);
    }
}