package com.ust.main.repository;

import com.ust.main.model.LeavesMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveMasterRepository extends JpaRepository<LeavesMaster, Integer> {

//
//    @Query("select lb from LeaveBalance lb where lb.leaveMaster.leaveType = :1")
//    LeaveBalance getLeaveBalanceByLeaveType(String leaveType);
}
