package com.devcamp.Project.service;

import com.devcamp.Project.dto.CustomerDTO;
import com.devcamp.Project.entity.Customer;
import com.devcamp.Project.mapped.CustomerMapped;
import com.devcamp.Project.repository.CustomerRepository;
import com.devcamp.Project.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
	private static Logger logger = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerMapped customerMapped;

	// lấy tất cả thông tin khách hàng theo Dto
	@Transactional
	public List<CustomerDTO> getAllCustomer() {
		logger.info(String.format("List Customer Had Found!!"));
		return customerRepository.findAll().stream()
				.map(customerMapped::customerToCustomerDTO).collect(Collectors.toList());
	}

	// lấy tất cả thông tin khách hàng bằng id theo Dto
	@Transactional
	public ResponseEntity<?> getCustomerById(final Long id) {
		Optional<Customer> oldCustomer = customerRepository.findById(id);
		if (oldCustomer.isPresent()){
			logger.info(String.format("Customer with ID %s had found!",id));
			return new ResponseEntity<>(CustomerMapped.INSTANCE.customerToCustomerDTO(customerRepository.findById(id).orElse(null)), HttpStatus.OK);
		} else {
			logger.info(String.format("Customer do not exist!"));
			return ResponseEntity.badRequest().body("Customer do not exist!");
		}
	}

	// tao thông tin khách hàng
	@Transactional
	public CustomerDTO createCustomer(Customer inputCustomer) {
		logger.info(String.format("Customer had saved!!"));
		return CustomerMapped.INSTANCE.customerToCustomerDTO(customerRepository.save(inputCustomer));
	}

	// cập nhật thông tin khách hàng
	@Transactional
	public ResponseEntity<?> updateCustomer(Customer inputCustomer, Long customerId) {
		Optional<Customer> oldCustomer = customerRepository.findById(customerId);
		if (oldCustomer.isPresent()){
			Customer customer = oldCustomer.orElse(null);
			customer.setName(inputCustomer.getName());
			customer.setGender(inputCustomer.getGender());
			customer.setCardId(inputCustomer.getCardId());
			customer.setPhone(inputCustomer.getPhone());
			customer.setEmail(inputCustomer.getEmail());
			customer.setDateOfBirth(inputCustomer.getDateOfBirth());
			customer.setAddress(inputCustomer.getAddress());
			customer.setOccupation(inputCustomer.getOccupation());
			logger.info(String.format("Customer with ID %s updated", customerId));
			return new ResponseEntity<>(CustomerMapped.INSTANCE.customerToCustomerDTO(customerRepository.save(customer)), HttpStatus.OK);
		} else {
			logger.info(String.format("Customer do not exist!"));
			return ResponseEntity.badRequest().body("Customer do not exist!");
		}

	}

	// xóa thông tin khách hàng
	@Transactional
	public ResponseEntity<?> deleteCustomerById(final Long id){
			Optional<Customer> customer = customerRepository.findById(id);
			if(customer.isPresent()){
				if (customer.get().getContract().isEmpty()){
					customerRepository.deleteById(id);
					logger.info(String.format("Customer with ID %s deleted", id));
					return ResponseEntity.accepted().body("Customer don't have contract! Deleted Customer Success.");
				} else {
					return ResponseEntity.badRequest().body("Customer have contract! Can't delete.");
				}
			} else {
				logger.info(String.format("Customer do not exist!"));
				return ResponseEntity.badRequest().body("Customer don't exist!");
			}
	}
}
