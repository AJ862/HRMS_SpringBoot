package com.ust.main.repository;

import com.ust.main.model.Employee;
import com.ust.main.model.LeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {

        LeaveApplication findByEmployee(Employee employee);
}
