package com.devcamp.Project.redisRepository;

import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.redis.User1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CustomerRedisRepository {
    final Logger logger = LoggerFactory.getLogger(CustomerRedisRepository.class);

    private HashOperations hashOperations;

    public CustomerRedisRepository(RedisTemplate redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    @CachePut(value = "customer",key = "#id")
    public void create(Customer customer) {
        hashOperations.put("CUSTOMER", customer.getId(), customer);
        logger.info(String.format("Customer with ID %s saved", customer.getId()));
    }

}
