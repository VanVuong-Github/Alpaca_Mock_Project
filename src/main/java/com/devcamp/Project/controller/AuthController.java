package com.devcamp.Project.controller;

import com.devcamp.Project.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.devcamp.Project.dto.UserPrincipal;
import com.devcamp.Project.entity.Token;
import com.devcamp.Project.entity.User;
import com.devcamp.Project.security.JwtUtil;
import com.devcamp.Project.service.TokenService;
import com.devcamp.Project.service.UserService;


@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    TokenService tokenService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/MQ/{routingKey}")
    public void rabbitMQ(@PathVariable String routingKey){
        rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, routingKey, "String");
    }

    // đăng kí user
    @PostMapping("/register")
    public User register(@RequestBody User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.createUser(user);
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
    @PreAuthorize("hasAnyAuthority('USER_DELETE')")
    public ResponseEntity hello(){
        return ResponseEntity.ok("hello");
    }

}
