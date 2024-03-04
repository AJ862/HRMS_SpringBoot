package com.ust.main.service;

import com.ust.main.model.Employee;
import com.ust.main.model.LeaveBalance;
import com.ust.main.model.LeavesMaster;
import com.ust.main.repository.LeaveBalanceRepository;
import com.ust.main.repository.LeaveMasterRepository;
import com.ust.main.response.LeaveMasterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveMasterService {

    @Autowired
    LeaveMasterRepository leaveMasterRepository;

    @Autowired
    LeaveBalanceRepository leaveBalanceRepository;

    @Autowired
    EmployeeService employeeService;

    public List<LeavesMaster> getAllLeavesList() {
        return leaveMasterRepository.findAll();
    }

    public ResponseEntity<LeaveMasterResponse> addNewLeaveType(long hrId, LeavesMaster newLeave) {
        LeaveMasterResponse leaveMasterResponse = new LeaveMasterResponse();
        if(employeeService.checkIfEmployeeIsHR(hrId)) {
            LeavesMaster savedLeaveMaster = leaveMasterRepository.save(newLeave);
            updateLeaveBalancesForAllEmployees(savedLeaveMaster);
            leaveMasterResponse.setLeavesMasterRecord(savedLeaveMaster);
            leaveMasterResponse.setResponseMessage("Record of new leave type saved successfully.");
            return new ResponseEntity<>(leaveMasterResponse, HttpStatus.OK);
        }else {
            leaveMasterResponse.setResponseMessage("You are not authorized to perform this task.");
            return new ResponseEntity<>(leaveMasterResponse, HttpStatus.UNAUTHORIZED);
        }

    }

    private void updateLeaveBalancesForAllEmployees(LeavesMaster newLeaveMaster) {
        List<Employee> employees = employeeService.getAllEmployeesList();

        // Update leave balances for each employee with the new leave type
        for (Employee employee : employees) {
            LeaveBalance leaveBalance = new LeaveBalance();
            leaveBalance.setEmployee(employee);
            leaveBalance.setLeavesMaster(newLeaveMaster);
            leaveBalance.setBalance(newLeaveMaster.getInitialBalance());

            leaveBalanceRepository.save(leaveBalance);
        }
    }


    public ResponseEntity<LeaveMasterResponse> deleteLeaveType(long hrId ,int leaveId) {
        LeaveMasterResponse leaveMasterResponse = new LeaveMasterResponse();
        if(employeeService.checkIfEmployeeIsHR(hrId)) {
            leaveMasterRepository.deleteById(leaveId);
            leaveMasterResponse.setResponseMessage("Leave Type deleted successfully");
            return new ResponseEntity<>(leaveMasterResponse, HttpStatus.OK);
        }else{
            leaveMasterResponse.setResponseMessage("You are not authorized to perform this task.");
            return new ResponseEntity<>(leaveMasterResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    public ResponseEntity<LeaveMasterResponse> updateLeaveType(long hrId, int leaveId, LeavesMaster updateLeave) {
        LeaveMasterResponse leaveMasterResponse = new LeaveMasterResponse();
        Optional<LeavesMaster> optionalTempLeave = leaveMasterRepository.findById(leaveId);

        if(employeeService.checkIfEmployeeIsHR(hrId) && optionalTempLeave.isPresent()){
            LeavesMaster tempLeave = optionalTempLeave.get();
            tempLeave.setLeaveType(updateLeave.getLeaveType());
            tempLeave.setInitialBalance(updateLeave.getInitialBalance());
            leaveMasterRepository.save(tempLeave);
            leaveMasterResponse.setResponseMessage("Leave updated Successfully");
            leaveMasterResponse.setLeavesMasterRecord(leaveMasterRepository.findById(leaveId).get());

            return new ResponseEntity<>(leaveMasterResponse, HttpStatus.OK);
        }else{
            leaveMasterResponse.setResponseMessage("Something went wrong!");
            return new ResponseEntity<>(leaveMasterResponse, HttpStatus.BAD_REQUEST);
        }

    }

}
