package com.devcamp.Project.controller;

import com.devcamp.Project.entity.Contract;
import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.redisService.ContractRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis/contract")
public class RedisContractController {

    @Autowired
    ContractRedisService contractRedisService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getContract(@PathVariable Long id){
        return contractRedisService.get(id);
    }

    @PostMapping("/")
    public Object createContract(@RequestBody Contract contract){
        return contractRedisService.create(contract);
    }

    @PutMapping("/{id}")
    public Object updateContract(@PathVariable Long id, @RequestBody Contract contract){
        return contractRedisService.update(id, contract);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContract(@PathVariable Long id){
        return contractRedisService.delete(id);
    }

}
