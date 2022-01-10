package com.devcamp.Project.controller;

import com.devcamp.Project.entity.Contract;
import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.redisService.ContractRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/redis/contract")
public class RedisContractController {

    @Autowired
    ContractRedisService contractRedisService;

    @GetMapping("/")
    public ResponseEntity<?> getAllContract(){
        return new ResponseEntity<>(contractRedisService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContract(@PathVariable Long id){
        return new ResponseEntity<>(contractRedisService.get(id), HttpStatus.OK) ;
    }

    @PostMapping("/")
    public ResponseEntity<?> createContract(@Valid @RequestBody Contract contract){
        return new ResponseEntity<>(contractRedisService.create(contract), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContract(@Valid @PathVariable Long id, @RequestBody Contract contract){
        return new ResponseEntity<>(contractRedisService.update(id, contract), HttpStatus.OK) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContract(@PathVariable Long id){
        return new ResponseEntity<>(contractRedisService.delete(id), HttpStatus.NO_CONTENT);
    }

}
