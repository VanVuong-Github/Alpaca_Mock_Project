package com.alpaca.Alpaca_Mock_Project.controller;

import com.alpaca.Alpaca_Mock_Project.dto.CustomerDto;
import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import com.alpaca.Alpaca_Mock_Project.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<CustomerDto> findAll(){
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") final Long id){
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody final Customer customer){
        return customerService.saveCustomer(customer);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@RequestBody final Customer customer,
                                   @PathVariable("id") final Long id){
        return customerService.updateCustomer(customer, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") final Long id){
        try {
            customerService.deleteCustomerById(id);
            return ResponseEntity.ok().body("Customer Deleted!");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Customer Deleting Failed!");
        }

    }
}
