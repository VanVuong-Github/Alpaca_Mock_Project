package com.devcamp.Project.service;

import com.devcamp.Project.dto.CCustomerDTO;
import com.devcamp.Project.entity.CCustomer;
import com.devcamp.Project.mapped.ICustomerMapped;
import com.devcamp.Project.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CCustomerService {

	@Autowired
	private ICustomerRepository iCustomerRepository;

	@Autowired
	private  ICustomerMapped iCustomerMapped;
	
	public List<CCustomerDTO> getAllCustomer() {
		//return iCustomerRepository.findAll();
		return iCustomerRepository.findAll().stream()
				.map(iCustomerMapped::customerToCustomerDTO).collect(Collectors.toList());
	}
	
	public CCustomerDTO getCustomerById(final Long id) {
		//return iCustomerRepository.findById(id).orElse(null);
		return iCustomerMapped.INSTANCE.customerToCustomerDTO(iCustomerRepository.findById(id).get());
	}
	
	public CCustomerDTO createCustomer(CCustomer inputCustomer) {
		//return iCustomerRepository.save(inputCustomer);
		return iCustomerMapped.INSTANCE.customerToCustomerDTO(iCustomerRepository.save(inputCustomer));
	}
	
	public CCustomerDTO updateCustomer(CCustomer inputCustomer, Long customerId) {
		CCustomer customer = iCustomerRepository.findById(customerId).get();
		customer.setName(inputCustomer.getName());
		customer.setGender(inputCustomer.getGender());
		customer.setCardId(inputCustomer.getCardId());
		customer.setPhone(inputCustomer.getPhone());
		customer.setEmail(inputCustomer.getEmail());
		customer.setDateOfBirth(inputCustomer.getDateOfBirth());
		customer.setAddress(inputCustomer.getAddress());
		customer.setOccupation(inputCustomer.getOccupation());

		//return iCustomerRepository.save(customer);
		return iCustomerMapped.INSTANCE.customerToCustomerDTO(iCustomerRepository.save(customer));
	}

	public void deleteCustomerById(final Long id){
		iCustomerRepository.deleteById(id);
	}
}
