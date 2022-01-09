package com.alpaca.Alpaca_Mock_Project;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableJpaRepositories(
		includeFilters = @ComponentScan.Filter(
				type = FilterType.ASSIGNABLE_TYPE, classes = JpaRepository.class))
@EnableElasticsearchRepositories(
		includeFilters = @ComponentScan.Filter(
				type = FilterType.ASSIGNABLE_TYPE, classes = ElasticsearchRepository.class))
public class AlpacaMockProjectApplication {

    @Bean(destroyMethod = "shutdown")
	RedissonClient redissonClient() throws IOException {
		Config config = Config.fromYAML(new File("src/main/resources/redisson-config.yml"));
		return Redisson.create(config);
	}

	@Bean
	CacheManager cacheManager(RedissonClient redissonClient){
		Map<String, CacheConfig> config = new HashMap<>();

		// create "customerMap" spring cache with ttl = 24 minutes and maxIdleTime = 12 minutes
		config.put("customerMap", new CacheConfig(24*60*1000, 12*60*1000));

		// create "claimRequestMap" spring cache with ttl = 24 minutes and maxIdleTime = 12 minutes
		config.put("claimRequestMap", new CacheConfig(24*60*1000, 12*60*1000));
		return new RedissonSpringCacheManager(redissonClient, config);
	}

	public static void main(String[] args) {
		SpringApplication.run(AlpacaMockProjectApplication.class, args);
	}

}
