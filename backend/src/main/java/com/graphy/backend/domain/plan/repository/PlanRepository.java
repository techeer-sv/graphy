package com.graphy.backend.domain.plan.repository;

import com.graphy.backend.domain.plan.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
