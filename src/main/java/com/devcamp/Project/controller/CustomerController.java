package com.devcamp.Project.controller;

import com.devcamp.Project.dto.CustomerDTO;
import com.devcamp.Project.elasticSearchService.CustomerElasticSearchService;
import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService cCustomerService;
    private final CustomerElasticSearchService customerElasticSearchService;

    public CustomerController(CustomerService cCustomerService, CustomerElasticSearchService customerElasticSearchService) {
        this.cCustomerService = cCustomerService;
        this.customerElasticSearchService = customerElasticSearchService;
    }

    // lấy tất cả thông tin khách hàng thông service
    @GetMapping("")
    public List<CustomerDTO> getAll(){

        return cCustomerService.getAllCustomer();
    }

    // lấy tất cả thông tin khách hàng bằng id thông service
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){
        return new ResponseEntity<>(customerElasticSearchService.getCustomerById(id), HttpStatus.OK) ;
        //return cCustomerService.getCustomerById(id);
    }

    // tạo mới khách hàng
    @PostMapping("")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer){
        return new ResponseEntity<>(customerElasticSearchService.create(customer), HttpStatus.CREATED) ;
        //return cCustomerService.createCustomer(customer);
    }

    // cập nhật khách hàng
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@Valid @PathVariable Long id, @RequestBody Customer inputCustomer){
        return new ResponseEntity<>(customerElasticSearchService.update(id, inputCustomer), HttpStatus.OK);
        //return cCustomerService.updateCustomer(inputCustomer, id);
    }

    // xóa khách hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
       return new ResponseEntity<>(customerElasticSearchService.delete(id), HttpStatus.NO_CONTENT) ;
        //return cCustomerService.deleteCustomerById(id);
    }

}

