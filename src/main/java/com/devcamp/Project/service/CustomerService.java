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
		//return iCustomerRepository.findAll();
		return customerRepository.findAll().stream()
				.map(customerMapped::customerToCustomerDTO).collect(Collectors.toList());
	}

	// lấy tất cả thông tin khách hàng bằng id theo Dto
	@Transactional
	public CustomerDTO getCustomerById(final Long id) {
		//return iCustomerRepository.findById(id).orElse(null);
		return CustomerMapped.INSTANCE.customerToCustomerDTO(customerRepository.findById(id).get());
	}

	// tao thông tin khách hàng
	@Transactional
	public CustomerDTO createCustomer(Customer inputCustomer) {
		//return iCustomerRepository.save(inputCustomer);
		return CustomerMapped.INSTANCE.customerToCustomerDTO(customerRepository.save(inputCustomer));
	}

	// cập nhật thông tin khách hàng
	@Transactional
	public CustomerDTO updateCustomer(Customer inputCustomer, Long customerId) {
		Optional<Customer> customer1 = customerRepository.findById(customerId);
		// validate Optional
		// sonarlint.
		Customer customer = customer1.get();
		customer.setName(inputCustomer.getName());
		customer.setGender(inputCustomer.getGender());
		customer.setCardId(inputCustomer.getCardId());
		customer.setPhone(inputCustomer.getPhone());
		customer.setEmail(inputCustomer.getEmail());
		customer.setDateOfBirth(inputCustomer.getDateOfBirth());
		customer.setAddress(inputCustomer.getAddress());
		customer.setOccupation(inputCustomer.getOccupation());

		//return iCustomerRepository.save(customer);
		return CustomerMapped.INSTANCE.customerToCustomerDTO(customerRepository.save(customer));
	}

	// xóa thông tin khách hàng
	@Transactional
	public ResponseEntity<Object> deleteCustomerById(final Long id){
		// hard delete
		// soft delete
			Optional<Customer> customer = customerRepository.findById(id);
			if(customer.isPresent()){
				if (customer.get().getContract().equals(null)){
					customerRepository.deleteById(id);
					return ResponseEntity.accepted().body("Customer don't have contract! Can delete.");
				} else {
					return ResponseEntity.badRequest().body("Customer have contract! Can't delete.");
				}
			} else {
				return ResponseEntity.badRequest().body("Customer don't exist!");
			}
	}
}
