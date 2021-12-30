package com.devcamp.Project.repository;

import com.devcamp.Project.entity.CClaimRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClaimRequestRepository extends JpaRepository<CClaimRequest, Long> {
}
