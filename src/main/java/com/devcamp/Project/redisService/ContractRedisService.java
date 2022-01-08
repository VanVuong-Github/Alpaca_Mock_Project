package com.devcamp.Project.redisService;

import com.devcamp.Project.entity.Contract;
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
public class ContractRedisService {
    final Logger logger = LoggerFactory.getLogger(CustomerRedisService.class);

    RedissonClient redissonClient = Redisson.create();
    RMap map = redissonClient.getMap ("Contract");

    public ResponseEntity<?> get(final Long id) {
        Object contract = map.get(id);
        if ( contract == null){
            logger.info(String.format("Contract do not exist!"));
            return new ResponseEntity<>("Contract do not exist!", HttpStatus.NOT_FOUND);
        } else {
            logger.info(String.format("Contract had found!!"));
            return new ResponseEntity<>(contract, HttpStatus.OK);
        }
    }

    public Object create(Contract contract){
        map.put(contract.getId(), contract);
        logger.info(String.format("Contract with ID %s saved",contract.getId()));
        return map.get(contract.getId());
    }

    public Object update(Long id, Contract contract){
        Object oldContract = map.get(id);
        if ( oldContract == null){
            logger.info(String.format("Contract do not exist!"));
            return new ResponseEntity<>("Contract do not exist!", HttpStatus.NOT_FOUND);
        } else {
            map.put(id, contract);
            logger.info(String.format("Contract with ID %s updated", id));
            return new ResponseEntity<>(map.get(id), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> delete(long id){
        Object contract = map.get(id);
        if ( contract == null){
            logger.info(String.format("Contract do not exist!"));
            return new ResponseEntity<>("Contract do not exist!", HttpStatus.NOT_FOUND);
        } else {
            map.remove(id);
            logger.info(String.format("Contract with ID %s deleted", id));
            return new ResponseEntity<>("Deleted Contract Success!!", HttpStatus.NO_CONTENT);
        }

    }

}
