package com.devcamp.Project.controller;

import com.devcamp.Project.dto.CustomerDTO;
import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.repository.CustomerRepository;
import com.devcamp.Project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CustomerService cCustomerService;

    // lấy tất cả thông tin khách hàng thông service
    @GetMapping("")
    public List<CustomerDTO> getAll(){
        return cCustomerService.getAllCustomer();
    }

    // lấy tất cả thông tin khách hàng bằng id thông service
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){
        return cCustomerService.getCustomerById(id);
    }

    // tạo mới khách hàng
    @PostMapping("")
    public CustomerDTO createCustomer(@Valid @RequestBody Customer customer){
        return cCustomerService.createCustomer(customer);
    }

    // cập nhật khách hàng
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer inputCustomer){
        return cCustomerService.updateCustomer(inputCustomer, id);
    }

    // xóa khách hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
       return cCustomerService.deleteCustomerById(id);
    }
}
