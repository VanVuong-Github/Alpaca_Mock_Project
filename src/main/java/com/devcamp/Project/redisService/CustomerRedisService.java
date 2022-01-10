package com.devcamp.Project.redisService;

import com.devcamp.Project.entity.Customer;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CustomerRedisService {
    static final Logger logger = LoggerFactory.getLogger(CustomerRedisService.class);

    private final RedissonClient redissonClient;

    public CustomerRedisService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    private RMap<Object, Object> map(){
        return redissonClient.getMap("customer");
    }

    public List<Object> getAll(){
        logger.info("ListCustomer had found!!");
        return map().values().stream().collect(Collectors.toList());
    }

    public Object get(final Long id) {
        Object customer = map().get(id);
        if ( customer == null){
            logger.error("Customer do not exist!");
            return "Customer do not exist!";
        } else {
            logger.info(String.format("Customer had found!!"));
            return customer;
        }
    }

    public Object create(Customer customer){
        map().put(customer.getId(), customer);
        logger.info("Customer with ID {} saved",customer.getId());
        return map().get(customer.getId());
    }

    //data type

    public Object update(Long id, Customer customer){
        Object oldCustomer = map().get(id);
        if ( oldCustomer == null){
            logger.error("Customer do not exist!");
            return "Customer do not exist!";
        } else {
            map().put(id, customer);
            logger.info("Customer with ID {} updated",customer.getId());
            return map().get(id);
        }
    }

    public Object delete(long id){
        Object customer = map().get(id);
        if ( customer == null){
            logger.error("Customer do not exist!");
            return "Customer do not exist!";
        } else {
            map().remove(id);
            logger.info("Customer with ID {} deleted", id);
            return "Deleted Customer Success!!";
        }

    }
}
