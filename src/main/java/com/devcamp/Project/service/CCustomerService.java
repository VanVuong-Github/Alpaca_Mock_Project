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

	// lấy tất cả thông tin khách hàng theo Dto
	public List<CCustomerDTO> getAllCustomer() {
		//return iCustomerRepository.findAll();
		return iCustomerRepository.findAll().stream()
				.map(iCustomerMapped::customerToCustomerDTO).collect(Collectors.toList());
	}

	// lấy tất cả thông tin khách hàng bằng id theo Dto
	public CCustomerDTO getCustomerById(final Long id) {
		//return iCustomerRepository.findById(id).orElse(null);
		return iCustomerMapped.INSTANCE.customerToCustomerDTO(iCustomerRepository.findById(id).get());
	}

	// tao thông tin khách hàng
	public CCustomerDTO createCustomer(CCustomer inputCustomer) {
		//return iCustomerRepository.save(inputCustomer);
		return iCustomerMapped.INSTANCE.customerToCustomerDTO(iCustomerRepository.save(inputCustomer));
	}

	// cập nhật thông tin khách hàng
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

	// xóa thông tin khách hàng
	public void deleteCustomerById(final Long id){
		iCustomerRepository.deleteById(id);
	}
}
