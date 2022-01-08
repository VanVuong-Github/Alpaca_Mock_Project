package com.devcamp.Project.redisService;

import com.devcamp.Project.entity.Customer;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class CustomerRedisService {
    final Logger logger = LoggerFactory.getLogger(CustomerRedisService.class);

    RedissonClient redissonClient = Redisson.create();
    RMap map = redissonClient.getMap ("Customer");

    public ResponseEntity<?> get(final Long id) {
        Object customer = map.get(id);
        if ( customer == null){
            logger.info(String.format("Customer do not exist!"));
            return new ResponseEntity<>("Customer do not exist!", HttpStatus.NOT_FOUND);
        } else {
            logger.info(String.format("Customer had found!!"));
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
    }

    public Object create(Customer customer){
        map.put(customer.getId(), customer);
        logger.info(String.format("Customer with ID %s saved",customer.getId()));
        return map.get(customer.getId());
    }

    public Object update(Long id, Customer customer){
        Object oldCustomer = map.get(id);
        if ( oldCustomer == null){
            logger.info(String.format("Customer do not exist!"));
            return new ResponseEntity<>("Customer do not exist!", HttpStatus.NOT_FOUND);
        } else {
            map.put(id, customer);
            logger.info(String.format("Customer with ID %s updated", id));
            return new ResponseEntity<>(map.get(id), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> delete(long id){
        Object customer = map.get(id);
        if ( customer == null){
            logger.info(String.format("Customer do not exist!"));
            return new ResponseEntity<>("Customer do not exist!", HttpStatus.NOT_FOUND);
        } else {
            map.remove(id);
            logger.info(String.format("Customer with ID %s deleted", id));
            return new ResponseEntity<>("Deleted Customer Success!!", HttpStatus.NO_CONTENT);
        }

    }
}
