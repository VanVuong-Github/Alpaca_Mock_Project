package com.alpaca.Alpaca_Mock_Project.cacheService.impl;

import com.alpaca.Alpaca_Mock_Project.cacheService.CustomerCacheService;
import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CustomerCacheServiceImpl implements CustomerCacheService {

    private static final String CUSTOMER_MAP = "customerMap";

    private static final Logger logger = Logger.getLogger(CustomerCacheServiceImpl.class.getName());

    private final RedissonClient redissonClient;

    public CustomerCacheServiceImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    private RMap<Object, Object> getMap(){
        return redissonClient.getMap(CUSTOMER_MAP);
    }

    @Override
    public Customer getCacheById(final Long id) {
        logger.log(Level.INFO, "Get customer cache with id: {0}", id);
        return Customer.class.cast(getMap().get(id));
    }

    @Override
    public Page<Customer> getAllCache(Pageable pageable){
        logger.log(Level.INFO, "Get all customer cache");
        List<Customer> customerList = new ArrayList<>();
        List<Object> objectList = getMap().values().stream().collect(Collectors.toList());
        for (Object obj:objectList) {
            customerList.add(Customer.class.cast(obj));
        }
        return new PageImpl<>(customerList, pageable, customerList.size());
    }

    @Override
    public void putCache(final Customer customer){
        logger.log(Level.INFO, "Put new customer cache");
        getMap().put(customer.getId(), customer);
    }

    @Override
    public void putAllCache(Map<Object, Object> customerMap) {
        logger.log(Level.INFO, "Put all customer cache");
        getMap().putAll(customerMap);
    }

    @Override
    public void deleteCache(final Long id){
        logger.log(Level.INFO, "Delete customer cache with id: {0}", id);
        getMap().remove(id);
    }
}
