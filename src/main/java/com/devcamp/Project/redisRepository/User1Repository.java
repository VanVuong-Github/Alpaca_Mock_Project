package com.devcamp.Project.redisRepository;

import com.devcamp.Project.redis.User1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class User1Repository  {
    final Logger logger = LoggerFactory.getLogger(User1Repository.class);
    private HashOperations hashOperations;

    public User1Repository(RedisTemplate redisTemplate) {

        this.hashOperations = redisTemplate.opsForHash();
    }

    @CachePut(value = "user",key = "#id")
    public void create(User1 user) {
        hashOperations.put("USER", user.getUserId(), user);
        logger.info(String.format("User with ID %s saved", user.getUserId()));
    }

    public User1 get(long userId) {
        return (User1) hashOperations.get("USER", userId);
    }

    public Map<String, User1> getAll(){
        return hashOperations.entries("USER1");
    }

    public void update(User1 user) {
        hashOperations.put("USER", user.getUserId(), user);
        logger.info(String.format("User with ID %s updated", user.getUserId()));
    }

    public void delete(long userId) {
        hashOperations.delete("USER", userId);
        logger.info(String.format("User with ID %s deleted", userId));
    }
}
