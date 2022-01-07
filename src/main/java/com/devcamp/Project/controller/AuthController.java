package com.devcamp.Project.controller;

import com.devcamp.Project.dto.CustomerDTO;
import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.redis.User1;
import com.devcamp.Project.service.RedisService;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.devcamp.Project.dto.UserPrincipal;
import com.devcamp.Project.entity.Token;
import com.devcamp.Project.entity.User;
import com.devcamp.Project.security.JwtUtil;
import com.devcamp.Project.service.TokenService;
import com.devcamp.Project.service.UserService;

import java.util.Date;


@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    TokenService tokenService;

    @Autowired
    RedisService redisService;

    // đăng kí user
    @PostMapping("/register")
    public User register(@RequestBody User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.createUser(user);
    }

    // lưu vào redis
    @GetMapping("/redis")
    public Object redis(){

        RedissonClient redissonClient = Redisson.create(redisService.config());

        RMap map = redissonClient.getMap ("myMap");
        //map.put(1, new CustomerDTO("Lam", "male","cardId", "phone", "email", new Date(1994-12-21), "HCM", "DEV"));
        map.put ("a", new Customer("Lam", "male","cardId", "phone", "email", new Date(1994-12-21), "HCM", "DEV"));
        map.put ("b", new Customer("Lam", "male","cardId", "phone", "email", new Date(1994-12-21), "HCM", "DEV"));
        map.put ("c", new Customer("Lam", "male","cardId", "phone", "email", new Date(1994-12-21), "HCM", "DEV"));

        //redissonClient.getBucket("CUSTOMER").set(new Customer(2L,"Lam", "male","cardId", "phone", "email", new Date(1994-12-21), "HCM", "DEV"));
        return map.get("a");           //redissonClient.getBucket("CUSTOMER").get();
    }

    // đăng nhập
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        UserPrincipal userPrincipal = userService.findByUsername(user.getUsername());
        if (null == user || !new BCryptPasswordEncoder().matches(user.getPassword(), userPrincipal.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tài khoản hoặc mật khẩu không chính xác");
        }
        Token token = new Token();
        token.setToken(jwtUtil.generateToken(userPrincipal));
        token.setTokenExpDate(jwtUtil.generateExpirationDate());
        token.setCreatedBy(userPrincipal.getUserId());
        tokenService.createToken(token);
        return ResponseEntity.ok(token.getToken());
    }

    // phân quyền
    @GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('USER_READ')")
    public ResponseEntity hello(){
        return ResponseEntity.ok("hello");
    }
}
