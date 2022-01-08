package com.devcamp.Project.redisService;

import com.devcamp.Project.entity.ClaimRequest;
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
public class ClaimRequestRedisService {
    final Logger logger = LoggerFactory.getLogger(ClaimRequestRedisService.class);

    RedissonClient redissonClient = Redisson.create();
    RMap map = redissonClient.getMap ("ClaimRequest");

    public ResponseEntity<?> get(final Long id) {
        Object claimRequest = map.get(id);
        if ( claimRequest == null){
            logger.info(String.format("Claim Request do not exist!"));
            return new ResponseEntity<>("Claim Request do not exist!", HttpStatus.NOT_FOUND);
        } else {
            logger.info(String.format("Claim Request had found!!"));
            return new ResponseEntity<>(claimRequest, HttpStatus.OK);
        }
    }

    public Object create(ClaimRequest claimRequest){
        map.put(claimRequest.getId(), claimRequest);
        logger.info(String.format("Claim Request with ID %s saved",claimRequest.getId()));
        return map.get(claimRequest.getId());
    }

    public Object update(Long id, ClaimRequest claimRequest){
        Object oldClaimRequest = map.get(id);
        if ( oldClaimRequest == null){
            logger.info(String.format("Claim Request do not exist!"));
            return new ResponseEntity<>("Claim Request do not exist!", HttpStatus.NOT_FOUND);
        } else {
            map.put(id, claimRequest);
            logger.info(String.format("Claim Request with ID %s updated", id));
            return new ResponseEntity<>(map.get(id), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> delete(long id){
        Object claimRequest = map.get(id);
        if ( claimRequest == null){
            logger.info(String.format("Claim Request do not exist!"));
            return new ResponseEntity<>("Claim Request do not exist!", HttpStatus.NOT_FOUND);
        } else {
            map.remove(id);
            logger.info(String.format("Claim Request with ID %s deleted", id));
            return new ResponseEntity<>("Deleted Claim Request Success!!", HttpStatus.NO_CONTENT);
        }

    }
}
