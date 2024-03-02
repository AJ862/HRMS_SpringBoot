package com.ust.main.repository;

import com.ust.main.model.Leaves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends JpaRepository<Leaves, Integer> {
}
