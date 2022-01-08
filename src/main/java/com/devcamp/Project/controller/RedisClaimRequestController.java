package com.devcamp.Project.controller;

import com.devcamp.Project.entity.ClaimRequest;
import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.redisService.ClaimRequestRedisService;
import com.devcamp.Project.redisService.CustomerRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis/claimrequest")
public class RedisClaimRequestController {

    @Autowired
    ClaimRequestRedisService claimRequestRedisService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getClaimRequest(@PathVariable Long id){
        return claimRequestRedisService.get(id);
    }

    @PostMapping("/")
    public Object createClaimRequest(@RequestBody ClaimRequest claimRequest){
        return claimRequestRedisService.create(claimRequest);
    }

    @PutMapping("/{id}")
    public Object updateClaimRequest(@PathVariable Long id, @RequestBody ClaimRequest claimRequest){
        return claimRequestRedisService.update(id, claimRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClaimRequest(@PathVariable Long id){
        return claimRequestRedisService.delete(id);
    }

}
