package com.ust.main.controller;


import com.ust.main.model.Employee;
import com.ust.main.model.Role;
import com.ust.main.response.ConfirmMessage;
import com.ust.main.response.EmployeeResponse;
import com.ust.main.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @GetMapping(path = "/getallemployees")
    public ResponseEntity<EmployeeResponse> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping(path = "/getemployeebyid/{empId}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable long empId){
        return employeeService.getEmployeeById(empId);
    }

    @GetMapping(path = "/getactiveemployees")
    public ResponseEntity<EmployeeResponse> getActiveEmployees(){
        return employeeService.getActiveEmployees();
    }

    @PostMapping(path = "/saveemployee")
    public ResponseEntity<EmployeeResponse> saveEmployee(@RequestBody Employee employee){
       return  employeeService.saveEmployee(employee);
    }

    @PutMapping(path = "/updateemployee/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@RequestBody Employee updateEmployee, @PathVariable long id){
        return employeeService.updateEmployee(updateEmployee, id);
    }

    @DeleteMapping(path = "/deleteemployee/{employeeId}/{LWD}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long employeeId, @PathVariable LocalDate LWD){
        return employeeService.deleteEmployee(employeeId, LWD);
    }

    @PostMapping(path = "/applyleave/{empId}")
    public ResponseEntity<EmployeeResponse> applyLeaves( @PathVariable long empId, @RequestBody Employee employeeLeaves){
       return  employeeService.applyLeaves(employeeLeaves, empId);
    }

    @GetMapping(path = "/getmyleavesdata/{empId}") //send self empId
    public ResponseEntity<EmployeeResponse> getMyLeavesData(@PathVariable long empId){
        return employeeService.getMyLeavesData(empId);
    }

    @GetMapping(path = "/getallleavesdata/{empId}") //selfempId
    public void getAllLeavesData(@PathVariable long empId){
        employeeService.getAllLeavesData(empId);
    }

    @PostMapping(path = "/take-action-on-leave/hrid/{hrId}/applicationId/{appId}/action/{action}") //action can be approve or reject
    public ResponseEntity<ConfirmMessage> approveLeave(@PathVariable long hrId, @PathVariable long appId, @PathVariable int action){
       return employeeService.approveLeave(hrId, appId, action);
    }

    @DeleteMapping
    public void deleteLeaveApplication(){

    }
}
