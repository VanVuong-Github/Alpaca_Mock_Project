package com.devcamp.Project.service;

import com.devcamp.Project.dto.ContractDTO;
import com.devcamp.Project.entity.Contract;
import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.mapped.ContractMapped;
import com.devcamp.Project.repository.ContractRepository;
import com.devcamp.Project.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(ContractService.class);

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    ContractMapped contractMapped;

    // lấy tất cả thông tin hợp đồng
    @Transactional
    public List<ContractDTO> getAllContract(){
        logger.info(String.format("List Contract Had Found!!"));
        return contractRepository.findAll().stream()
                .map(contractMapped::contractToContractDTO).collect(Collectors.toList());
    }

    // lấy thông tin hợp đồng theo id
    @Transactional
    public ResponseEntity<?> getContractById(final Long id){
        Optional<Contract> contract = contractRepository.findById(id);
        if(contract.isPresent()){
            logger.info(String.format("Contract with ID %s had found!",id));
            return new ResponseEntity<>(ContractMapped.INSTANCE.contractToContractDTO(contractRepository.findById(id).orElse(null)), HttpStatus.OK);
        } else {
            logger.info(String.format("Contract do not exist!"));
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
            logger.info(String.format("Contract had saved!!"));
            return new ResponseEntity<>(ContractMapped.INSTANCE.contractToContractDTO(contractRepository.save(inputContract)), HttpStatus.CREATED);
        } else {
            logger.info(String.format("Customer do not exist!"));
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
            logger.info(String.format("Contract with ID %s updated", id));
            return new ResponseEntity<>(ContractMapped.INSTANCE.contractToContractDTO(contractRepository.save(newContract)), HttpStatus.OK) ;
        } else {
            logger.info(String.format("Contract do not exist!"));
            return ResponseEntity.badRequest().body("Contract do not exist!!");
        }
    }

    // xóa hợp đồng
    @Transactional
    public ResponseEntity<?> deleteContractById(Long id) {
        Optional<Contract> contract = contractRepository.findById(id);
        if(contract.isPresent()){
            contractRepository.deleteById(id);
            logger.info(String.format("Contract with ID %s deleted", id));
            return ResponseEntity.ok("Deleted Contract Success!!");
        } else {
            logger.info(String.format("Contract do not exist!"));
            return ResponseEntity.badRequest().body("Contract do not exist!!");
        }
    }
}
