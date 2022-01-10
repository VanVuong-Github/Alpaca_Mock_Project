package com.devcamp.Project.redisService;

import com.devcamp.Project.entity.Contract;
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
public class ContractRedisService {
    private final Logger logger = LoggerFactory.getLogger(ContractRedisService.class);

    private final RedissonClient redissonClient;

    public ContractRedisService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    private RMap<Object, Object> map(){
        return redissonClient.getMap("contract");
    }

    public List<Object> getAll(){
        logger.info("ListCustomer had found!!");
        return map().values().stream().collect(Collectors.toList());

    }
    public Object get(final Long id) {
        Object contract = map().get(id);
        if ( contract == null){
            logger.error("Contract do not exist!");
            return "Contract do not exist!";
        } else {
            logger.info("Contract had found!!");
            return contract;
        }
    }

    public Object create(Contract contract){
        map().put(contract.getId(), contract);
        logger.info("Contract with ID {} saved",contract.getId());
        return map().get(contract.getId());
    }

    public Object update(Long id, Contract contract){
        Object oldContract = map().get(id);
        if ( oldContract == null){
            logger.error("Contract do not exist!");
            return "Contract do not exist!";
        } else {
            map().put(id, contract);
            logger.info("Contract with ID {} updated", id);
            return map().get(id);
        }
    }

    public Object delete(long id){
        Object contract = map().get(id);
        if ( contract == null){
            logger.info(String.format("Contract do not exist!"));
            return "Contract do not exist!";
        } else {
            map().remove(id);
            logger.info("Contract with ID {} deleted", id);
            return String.format("Contract with ID %s deleted", id);
        }

    }

}
