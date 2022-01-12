package com.alpaca.Alpaca_Mock_Project.elasticsearchService;

import com.alpaca.Alpaca_Mock_Project.dto.CustomerDto;
import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomerElasticsearchService {
    Optional<Customer> getCustomerById(final Long id);
    Page<CustomerDto> findAll(Pageable pageable);
    List<Customer> insertBulk(final List<Customer> customerList);
    void delete(final Long id);
    Customer create(final Customer customer);
    Customer update(final Customer customer, final Long id);
    Page<CustomerDto> search(String textToSearch, Pageable pageable);
}
