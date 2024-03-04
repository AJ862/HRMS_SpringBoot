package com.ust.main.repository;

import com.ust.main.model.LeavesMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends JpaRepository<LeavesMaster, Integer> {
}
