package com.devcamp.Project.controller;

import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.redisService.CustomerRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping("/redis/customer")
public class RedisCustomerController {

    @Autowired
    CustomerRedisService customerRedisService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable Long id){
        return customerRedisService.get(id);
    }

    @PostMapping("/")
    public Object createCustomer(@RequestBody Customer customer){
        return customerRedisService.create(customer);
    }

    @PutMapping("/{id}")
    public Object updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        return customerRedisService.update(id, customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        return customerRedisService.delete(id);
    }
}
