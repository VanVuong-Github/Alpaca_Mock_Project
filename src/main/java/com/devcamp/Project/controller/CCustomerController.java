package com.devcamp.Project.controller;

import com.devcamp.Project.dto.CCustomerDTO;
import com.devcamp.Project.entity.CCustomer;
import com.devcamp.Project.service.CCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CCustomerController {

    @Autowired
    CCustomerService cCustomerService;

    @GetMapping("/")
    public ResponseEntity<List<CCustomerDTO>> getAll(){
        try {
            return new  ResponseEntity<>(cCustomerService.getAllCustomer(), HttpStatus.OK);
        } catch(Exception e) {
            return new  ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CCustomer> getCustomerById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(cCustomerService.getCustomerById(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Object> createCustomer(@RequestBody CCustomer customer){
        try {
            return new ResponseEntity<>(cCustomerService.createCustomer(customer), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody CCustomer inputCustomer){
        try {
            return new ResponseEntity<>(cCustomerService.updateCustomer(inputCustomer, id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id){
        try {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
