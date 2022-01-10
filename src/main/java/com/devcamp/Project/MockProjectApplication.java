package com.devcamp.Project;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



@SpringBootApplication
@EnableJpaAuditing
public class MockProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockProjectApplication.class, args);
	}

	@Bean(destroyMethod = "shutdown")
	public RedissonClient redissonClient(){
		Config config = new Config();
		config.useSingleServer()
				.setAddress("redis://localhost:6379")
				.setConnectionPoolSize(10)
				.setConnectionMinimumIdleSize(5)
				.setConnectTimeout(30000);
		return Redisson.create(config);
	}


}
