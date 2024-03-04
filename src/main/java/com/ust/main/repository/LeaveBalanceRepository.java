package com.ust.main.repository;

import com.ust.main.model.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Double> {

    @Query("select lb from LeaveBalance lb where lb.leavesMaster.leaveType = ?1 and lb.employee.isCurrentEmployee=true")
    List<LeaveBalance> findLeaveBalanceByLeaveTypeForActiveEmployees(String leaveType);
}
