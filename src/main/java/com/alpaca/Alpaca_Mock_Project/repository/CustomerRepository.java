package com.alpaca.Alpaca_Mock_Project.repository;

import com.alpaca.Alpaca_Mock_Project.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCardId(String cardId);
    List<Customer> findAllByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneContainingIgnoreCase(String name, String email, String phone);

}
