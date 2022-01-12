package com.devcamp.Project;


import com.devcamp.Project.entity.Contract;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories( basePackages = "com.devcamp.Project.repository")
@EnableElasticsearchRepositories( basePackages = "com.devcamp.Project.EsRepository")
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
