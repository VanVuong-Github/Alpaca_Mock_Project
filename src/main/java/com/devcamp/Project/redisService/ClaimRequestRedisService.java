package com.devcamp.Project.redisService;

import com.devcamp.Project.entity.ClaimRequest;
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
public class ClaimRequestRedisService {
    static final Logger logger = LoggerFactory.getLogger(ClaimRequestRedisService.class);

    private final RedissonClient redissonClient;

    public ClaimRequestRedisService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    private RMap<Object, Object> map(){
        return redissonClient.getMap("claimRequest");
    }

    public List<Object> getAll(){
        return map().values().stream().collect(Collectors.toList());
    }
    public Object get(final Long id) {
        Object claimRequest = map().get(id);
        if ( claimRequest == null){
            logger.error("Claim Request do not exist!");
            return "Claim Request do not exist!";
        } else {
            logger.info("Claim Request had found!!");
            return claimRequest;
        }
    }

    public Object create(ClaimRequest claimRequest){
        map().put(claimRequest.getId(), claimRequest);
        logger.info("Claim Request with ID {} saved",claimRequest.getId());
        return map().get(claimRequest.getId());
    }

    public Object update(Long id, ClaimRequest claimRequest){
        Object oldClaimRequest = map().get(id);
        if ( oldClaimRequest == null){
            logger.error("Claim Request do not exist!");
            return "Claim Request do not exist!";
        } else {
            map().put(id, claimRequest);
            logger.info("Claim Request with ID {} updated", id);
            return map().get(id);
        }
    }

    public Object delete(long id){
        Object claimRequest = map().get(id);
        if ( claimRequest == null){
            logger.error("Claim Request do not exist!");
            return "Claim Request do not exist!";
        } else {
            map().remove(id);
            logger.info("Claim Request with ID %s deleted", id);
            return "Deleted Claim Request Success!!";
        }

    }
}
