package com.ust.main.controller;

import com.ust.main.response.ConfirmMessage;
import com.ust.main.model.LeaveApplicationResource;
import com.ust.main.service.LeaveApplicationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
public class LeaveApplicationController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;


    @PostMapping("/apply-leaves/employeeId/{employeeId}")
    public ResponseEntity<ConfirmMessage> applyLeaves(@PathVariable long employeeId, @RequestBody LeaveApplicationResource leaveApplicationResource) {

        ConfirmMessage confirmMessage = leaveApplicationService.applyEmployeeLeaves(leaveApplicationResource, employeeId);

        return ResponseEntity.status(HttpStatus.OK).body(confirmMessage);
    }




}
