package com.devcamp.Project.service;

import com.devcamp.Project.dto.ContractDTO;
import com.devcamp.Project.entity.Contract;
import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.mapped.ContractMapped;
import com.devcamp.Project.repository.ContractRepository;
import com.devcamp.Project.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractService {
    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    ContractMapped contractMapped;

    // lấy tất cả thông tin hợp đồng
    @Transactional
    public List<ContractDTO> getAllContract(){

        return contractRepository.findAll().stream()
                .map(contractMapped::contractToContractDTO).collect(Collectors.toList());
    }

    // lấy thông tin hợp đồng theo id
    @Transactional
    public ResponseEntity<?> getContractById(final Long id){
        Optional<Contract> contract = contractRepository.findById(id);
        if(contract.isPresent()){
            return new ResponseEntity<>(ContractMapped.INSTANCE.contractToContractDTO(contractRepository.findById(id).orElse(null)), HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body("Contract do not exist!!");
        }

    }

    // lấy thông tin hợp đồng theo id của khách hàng
    @Transactional
    public ResponseEntity<?> getContractByCustomerId(final Long customerId){
        Optional<Customer> oldCustomer = customerRepository.findById(customerId);
        if (oldCustomer.isPresent()){
            return new ResponseEntity<>(contractRepository.findByCustomerId(customerId), HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body("Customer do not exist!!");
    }

    // tạo mới hợp đồng
    @Transactional
    public ResponseEntity<?> createContract(Long customerId, Contract inputContract) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()){
            return new ResponseEntity<>(ContractMapped.INSTANCE.contractToContractDTO(contractRepository.save(inputContract)), HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().body("Customer do not exist!!");
        }
    }

    // cập nhật hợp đồng
    @Transactional
    public ResponseEntity<?> updateContract(Long id, Contract inputContract){
        Optional<Contract> oldContract = contractRepository.findById(id);
        if (oldContract.isPresent()){
            Contract newContract = oldContract.orElse(null);
            newContract.setValidFrom(inputContract.getValidFrom());
            newContract.setValidTo((inputContract.getValidTo()));
            newContract.setMaximumAmount(inputContract.getMaximumAmount());
            newContract.setRemainingAmount(inputContract.getRemainingAmount());
            newContract.setAcceptableAccidents(inputContract.getAcceptableAccidents());
            newContract.setAcceptableHospitals(inputContract.getAcceptableHospitals());
            return new ResponseEntity<>(ContractMapped.INSTANCE.contractToContractDTO(contractRepository.save(newContract)), HttpStatus.OK) ;
        } else {
            return ResponseEntity.badRequest().body("Customer do not exist!!");
        }
    }

    // xóa hợp đồng
    @Transactional
    public ResponseEntity<?> deleteContractById(Long id) {
        Optional<Contract> contract = contractRepository.findById(id);
        if(contract.isPresent()){
            contractRepository.deleteById(id);
            return ResponseEntity.ok("Deleted Contract Success!!");
        } else {
            return ResponseEntity.badRequest().body("Customer do not exist!!");
        }
    }
}
