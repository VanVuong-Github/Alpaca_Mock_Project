package com.devcamp.Project.service;

import com.devcamp.Project.entity.CCustomer;
import com.devcamp.Project.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CCustomerService {

	@Autowired
	private ICustomerRepository iCustomerRepository;
	
	public List<CCustomer> getAllCustomer() {
		return iCustomerRepository.findAll();
	}
	
	public CCustomer getCustomerById(final Long id) {
		return iCustomerRepository.findById(id).orElse(null);
	}
	
	public CCustomer createCustomer(CCustomer inputCustomer) {
		return iCustomerRepository.save(inputCustomer);
	}
	
	public CCustomer updateCustomer(CCustomer inputCustomer, Long customerId) {
		CCustomer customer = iCustomerRepository.findById(customerId).get();
		customer.setName(inputCustomer.getName());
		customer.setGender(inputCustomer.getGender());
		customer.setCardId(inputCustomer.getCardId());
		customer.setPhone(inputCustomer.getPhone());
		customer.setEmail(inputCustomer.getEmail());
		customer.setDateOfBirth(inputCustomer.getDateOfBirth());
		customer.setAddress(inputCustomer.getAddress());
		customer.setOccupation(inputCustomer.getOccupation());

		return iCustomerRepository.save(customer);

	}

	public void deleteCustomerById(final Long id){
		iCustomerRepository.deleteById(id);
	}
}
