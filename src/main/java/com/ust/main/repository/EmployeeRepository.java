package com.ust.main.repository;

import com.ust.main.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface EmployeeRepository extends JpaRepository<Employee, Long> {

//public void saveByEmployeeId(long EmployeeId);

     boolean existsByEmailId(String emailId);

     Employee findByEmailId(String emailId);


}
