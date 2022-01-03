package com.devcamp.Project.repository;

import com.devcamp.Project.entity.ClaimRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRequestRepository extends JpaRepository<ClaimRequest, Long> {
}
