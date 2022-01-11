package com.devcamp.Project.service;

import com.devcamp.Project.dto.ContractDTO;
import com.devcamp.Project.entity.Contract;
import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.mapped.ContractMapped;
import com.devcamp.Project.repository.ContractRepository;
import com.devcamp.Project.repository.CustomerRepository;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.*;
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

    private final RedissonClient redissonClient;

    public ContractService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    private RBucket<Object> quantity(){
        return redissonClient.getBucket("quantityContract");
    }

//    private RMap<Object, Object> map(){
//        return redissonClient.getMap("contract");
//    }
    //map().put(contract.getId(), contract);
    //map().get(id);

    public Object getQuantity(){
        // set quantity
        //quantity().set(0);
        return quantity().get();
    }


    // lấy tất cả thông tin hợp đồng
    @Transactional
    public List<ContractDTO> getAllContract(){
        logger.info("List Contract Had Found!!");
        return contractRepository.findAll().stream()
                .map(contractMapped::contractToContractDTO).collect(Collectors.toList());
    }

    // lấy thông tin hợp đồng theo id
    @Transactional
    public Object getContractById(final Long id){
        Optional<Contract> contract = contractRepository.findById(id);
        if(contract.isPresent()){
            logger.info("Contract with ID {} had found!",id);
            return ContractMapped.INSTANCE.contractToContractDTO(contractRepository.findById(id).orElse(null));
        } else {
            logger.error("Contract do not exist!");
            return "Contract do not exist!!";
        }

    }

    @Transactional
    public Object getContractByNumContract(String numContract){
        Contract contract = contractRepository.findByNumContract(numContract);
        if (contract.getNumContract().isEmpty()){
            logger.error("Contract do not exist!");
            return "Contract do not exist!";
        } else {
            logger.info("Contract Had Found!!");
            return contract;
        }
    }

    // lấy thông tin hợp đồng theo id của khách hàng
    @Transactional
    public Object getContractByCustomerId(final Long customerId){
        Optional<Customer> oldCustomer = customerRepository.findById(customerId);
        if (oldCustomer.isPresent()){
            logger.info("Contract with customerID {} had found!",customerId);
            return contractRepository.findByCustomerId(customerId);
        } else {
            logger.error("Customer do not exist!");
            return "Customer do not exist!!";
        }
    }

    // tạo mới hợp đồng
    @Transactional
    public Object createContract(Long customerId, Contract inputContract) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()){
            int quantityContract = (int) quantity().get();
            quantity().set(++quantityContract);

            Date date = new Date();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);

            inputContract.setCustomer(customer.get());
            inputContract.setNumContract("HD_"+ year + "_" + String.format("%06d", quantity().get()));

            logger.info("Contract had saved!!");
            return ContractMapped.INSTANCE.contractToContractDTO(contractRepository.save(inputContract));
        } else {
            logger.info("Customer do not exist!");
            return "Customer do not exist!!";
        }
    }

    // cập nhật hợp đồng
    @Transactional
    public Object updateContract(Long id, Contract inputContract){
        Optional<Contract> oldContract = contractRepository.findById(id);
        if (oldContract.isPresent()){
            Contract newContract = oldContract.orElse(null);
            newContract.setValidFrom(inputContract.getValidFrom());
            newContract.setValidTo((inputContract.getValidTo()));
            newContract.setMaximumAmount(inputContract.getMaximumAmount());
            newContract.setRemainingAmount(inputContract.getRemainingAmount());
            newContract.setAcceptableAccidents(inputContract.getAcceptableAccidents());
            newContract.setAcceptableHospitals(inputContract.getAcceptableHospitals());

            logger.info("Contract with ID %s updated", id);
            return ContractMapped.INSTANCE.contractToContractDTO(contractRepository.save(newContract)) ;
        } else {
            logger.info("Contract do not exist!");
            return "Contract do not exist!!";
        }
    }

    // xóa hợp đồng
    @Transactional
    public Object deleteContractById(Long id) {
        Optional<Contract> contract = contractRepository.findById(id);
        if(contract.isPresent()){
            contractRepository.deleteById(id);
            logger.info("Contract with ID %s deleted", id);
            return "Deleted Contract Success!!";
        } else {
            logger.info("Contract do not exist!");
            return "Contract do not exist!!";
        }
    }
}
