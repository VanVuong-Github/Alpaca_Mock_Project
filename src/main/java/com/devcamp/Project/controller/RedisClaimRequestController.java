package com.devcamp.Project.controller;

import com.devcamp.Project.entity.ClaimRequest;
import com.devcamp.Project.redisService.ClaimRequestRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/redis/claimrequest")
public class RedisClaimRequestController {

    @Autowired
    ClaimRequestRedisService claimRequestRedisService;

    @GetMapping("/")
    public ResponseEntity<?> getAllClaimRequest(){
        return new ResponseEntity<>(claimRequestRedisService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClaimRequest(@PathVariable Long id){
        return new ResponseEntity<>(claimRequestRedisService.get(id), HttpStatus.OK) ;
    }

    @PostMapping("/")
    public ResponseEntity<?> createClaimRequest(@Valid @RequestBody ClaimRequest claimRequest){
        return new ResponseEntity<>(claimRequestRedisService.create(claimRequest), HttpStatus.CREATED) ;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateClaimRequest(@Valid @PathVariable Long id, @RequestBody ClaimRequest claimRequest){
        return new ResponseEntity<>(claimRequestRedisService.update(id, claimRequest), HttpStatus.OK) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClaimRequest(@PathVariable Long id){
        return new ResponseEntity<>(claimRequestRedisService.delete(id), HttpStatus.NO_CONTENT) ;
    }

}
