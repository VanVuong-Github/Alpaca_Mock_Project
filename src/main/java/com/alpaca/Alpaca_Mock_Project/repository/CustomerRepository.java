package com.alpaca.Alpaca_Mock_Project.repository;

import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
