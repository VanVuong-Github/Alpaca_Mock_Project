package com.alpaca.Alpaca_Mock_Project.repository;

import com.alpaca.Alpaca_Mock_Project.entity.ClaimRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClaimRequestRepository extends JpaRepository<ClaimRequest, Long> {
    Optional<ClaimRequest> findByCustomerNameContainingIgnoreCaseAndCardIdContainingIgnoreCaseOrderByCustomerNameAsc(String customerName, String cardId);
}
