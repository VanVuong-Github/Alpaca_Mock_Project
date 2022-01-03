package com.devcamp.Project.controller;

import com.devcamp.Project.dto.CustomerDTO;
import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService cCustomerService;

    // lấy tất cả thông tin khách hàng thông service
    @GetMapping("/")
    public ResponseEntity<List<CustomerDTO>> getAll(){
        try {
            return new  ResponseEntity<>(cCustomerService.getAllCustomer(), HttpStatus.OK);
        } catch(Exception e) {
            return new  ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // lấy tất cả thông tin khách hàng bằng id thông service
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(cCustomerService.getCustomerById(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // tạo mới khách hàng
    @PostMapping("/")
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer){
        try {
            return new ResponseEntity<>(cCustomerService.createCustomer(customer), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // cập nhật khách hàng
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer inputCustomer){
        try {
            return new ResponseEntity<>(cCustomerService.updateCustomer(inputCustomer, id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // xóa khách hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id){
        try {
            cCustomerService.deleteCustomerById(id);
            return ResponseEntity.ok().body(String.format("Delete Customer Successfully!"));
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(String.format("Delete Customer Failed!"));
        }
    }
}
