package com.devcamp.Project;

import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.redisRepository.CustomerRedisRepository;
import com.devcamp.Project.redisRepository.User1Repository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Date;

import static com.devcamp.Project.redis.Config.redisTemplate;


@SpringBootApplication
@EnableJpaAuditing
public class MockProjectApplication {

	public static void main(String[] args) {

		SpringApplication.run(MockProjectApplication.class, args);

		//User1Repository userRepository = new User1Repository(redisTemplate());
		//CustomerRedisRepository a = new CustomerRedisRepository(redisTemplate());

		//a.create(new Customer(2L,"Lam", "male","cardId", "phone", "email", new Date(1994-12-21), "HCM", "DEV"));
		//userRepository.create(new User1(2, "username2"));
		//User1 user1 = userRepository.get(1);
		//User1 user2 = userRepository.get(2);
		//userRepository.update(user);
		//userRepository.delete(user1.getUserId());
		//userRepository.delete(user2.getUserId());

	}

}
