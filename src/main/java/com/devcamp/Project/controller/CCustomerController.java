package com.devcamp.Project.controller;

import com.devcamp.Project.entity.CCustomer;
import com.devcamp.Project.service.CCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CCustomerController {

    @Autowired
    CCustomerService cCustomerService;

    @GetMapping("/")
    public List<CCustomer> getAll(){
        return cCustomerService.getAllCustomer();
    }

    @GetMapping("/{id}")
    public CCustomer getCustomerById(@PathVariable Long id){
        return cCustomerService.getCustomerById(id);
    }

    @PostMapping("/")
    public CCustomer createCustomer(@RequestBody CCustomer customer){
        return cCustomerService.createCustomer(customer);
    }

    @PostMapping("/{id}")
    public CCustomer updateCustomer(@PathVariable Long id, @RequestBody CCustomer inputCustomer){
        return cCustomerService.updateCustomer(inputCustomer, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        try {
            cCustomerService.deleteCustomerById(id);

            return ResponseEntity.ok().body("Customer deleted!");
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body("Customer deleted!");
        }
    }
}
