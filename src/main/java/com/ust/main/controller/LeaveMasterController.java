package com.ust.main.controller;

import com.ust.main.model.LeavesMaster;
import com.ust.main.response.LeaveMasterResponse;
import com.ust.main.service.LeaveMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LeaveMasterController {

    @Autowired
    LeaveMasterService leaveMasterService;

    @GetMapping(path = "/get-all-leaves")
    public List<LeavesMaster> getAllLeavesList(){
        return leaveMasterService.getAllLeavesList();
    }

    @PostMapping(path = "/add-new-leave-type/hrid/{hrId}")
    public ResponseEntity<LeaveMasterResponse> addNewLeaveType(@PathVariable long hrId, @RequestBody LeavesMaster newLeave){
        return leaveMasterService.addNewLeaveType(hrId, newLeave);
    }

    @DeleteMapping(path = "delete-leave-type/hrid/{hrId}/leaveId/{leaveId}")
    public ResponseEntity<LeaveMasterResponse> deleteLeaveType(@PathVariable long hrId ,@PathVariable int leaveId){
        return leaveMasterService.deleteLeaveType(hrId, leaveId);
    }

    @PutMapping(path = "update-leave-type/hrid/{hrId}/leaveid/{leaveId}")
    public ResponseEntity<LeaveMasterResponse> updateLeaveType(@PathVariable long hrId, @PathVariable int leaveId, @RequestBody LeavesMaster leave){
        return leaveMasterService.updateLeaveType(hrId, leaveId, leave);
    }
}
