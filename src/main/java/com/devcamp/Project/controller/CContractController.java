package com.devcamp.Project.controller;

import com.devcamp.Project.entity.CContract;
import com.devcamp.Project.service.CContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contract")
public class CContractController {

    @Autowired
    CContractService cContractService;

    @GetMapping("/")
    public ResponseEntity<List<CContract>> getAllContract(){
        try {
            return new  ResponseEntity<>(cContractService.getAllContract(), HttpStatus.OK);
        } catch(Exception e) {
            return new  ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CContract> getContractById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(cContractService.getContractById(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<List<CContract>> getContractByCustomerId(@PathVariable Long id){
        try {
            return new ResponseEntity<>(cContractService.getContractByCustomerId(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/customer/{id}")
    public ResponseEntity<Object> createContract(@PathVariable Long id, @RequestBody CContract inputContract){
        try {
            return new ResponseEntity<>(cContractService.createContract(id, inputContract), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateContract(@PathVariable Long id, @RequestBody CContract inputContract){
        try {
            return new ResponseEntity<>(cContractService.updateContract(id, inputContract), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<Object> deleteContract(@PathVariable Long id){
        try {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
