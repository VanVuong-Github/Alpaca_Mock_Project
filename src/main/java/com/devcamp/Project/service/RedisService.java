package com.devcamp.Project.service;

import org.redisson.config.Config;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    public Config config(){
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://localhost:6379")
                .setConnectionPoolSize(10)
                .setConnectionMinimumIdleSize(5)
                .setConnectTimeout(30000);

        return config;
    }
}
