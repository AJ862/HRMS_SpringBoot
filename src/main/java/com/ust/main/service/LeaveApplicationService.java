package com.ust.main.service;

import com.ust.main.model.*;
import com.ust.main.repository.LeaveApplicationRepository;
import com.ust.main.response.ConfirmMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class LeaveApplicationService {

    private static final String LEAVE_ERROR_MESSAGE = "You can only apply only upto %s leaves for type %s";

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    LeaveApplicationRepository leaveApplicationRepository;


    public ConfirmMessage applyEmployeeLeaves(LeaveApplicationResource leaveApplicationResource, long employeeId) {
        Employee employee = employeeService.getEmployeeByEmployeeId(employeeId);
        Set<String> errorMessages = validateLeaveApplication(leaveApplicationResource, employee);
        ConfirmMessage confirmMessage = new ConfirmMessage();
        if(!errorMessages.isEmpty()) {
            confirmMessage.setRequestStatus("FAILED");
            confirmMessage.setProcessingMessage(errorMessages);
        }else {
            List<LeaveApplication> leaveApplications = employee.getLeaveApplication();
            if(leaveApplications == null || leaveApplications.isEmpty()) {
                leaveApplications = new ArrayList<>();
            }
            LeaveApplication leaveApplication = buildLeaveApplicationFromResource(leaveApplicationResource, employee);

            leaveApplications.add(leaveApplication);
            employee.setLeaveApplication(leaveApplications);
            leaveApplicationRepository.saveAll(leaveApplications);
           // employeeService.deleteEmployeeById(employeeId);
//            Employee updatedEmployee = employeeService.saveUpdatedEmployee(employee);

//            if(updatedEmployee != null) {
//                confirmMessage.setRequestStatus("SUCCESSFUL");
//            }
        }
        return confirmMessage;
    }

    private LeaveApplication buildLeaveApplicationFromResource(LeaveApplicationResource leaveApplicationResource, Employee employee) {
        LeaveApplication leaveApplication = new LeaveApplication();

        leaveApplication.setEmployee(employee);
        leaveApplication.setLeaveType(leaveApplicationResource.getLeaveType());
        leaveApplication.setLeaveReason(leaveApplicationResource.getLeaveReason());
        leaveApplication.setFromDate(leaveApplicationResource.getFromDate());
        leaveApplication.setToDate(leaveApplicationResource.getToDate());
        leaveApplication.setNumerOfDays(Period.between(leaveApplicationResource.getFromDate(), leaveApplicationResource.getToDate()).getDays()+1);
        return leaveApplication;
    }


    private Set<String> validateLeaveApplication(LeaveApplicationResource leaveApplicationResource, Employee employee) {
//        Set<String>  errorMessages = new HashSet<>();
//
//        if(employee != null && employee.isCurrentEmployee()) {
//            if(leaveApplicationResource.getFromDate()  == null ) {
//                errorMessages.add("Leave fromDate is empty");
//            }
//            if(leaveApplicationResource.getToDate() == null) {
//                errorMessages.add("Leave toDate is Empty");
//            }
//            if(leaveApplicationResource.getLeaveType() == null) {
//                errorMessages.add("Leave Type is null or empty");
//            }
//            LeaveType leaveType = leaveApplicationResource.getLeaveType();
//
//            int numberOfAppliedDays = 0;
//            if(leaveApplicationResource.getFromDate() != null && leaveApplicationResource.getToDate() != null) {
//                if (leaveApplicationResource.getToDate().isBefore(leaveApplicationResource.getFromDate())) {
//                    errorMessages.add("Leave To Date cannot be before leave from date");
//                } else {
//                    numberOfAppliedDays = Period.between(leaveApplicationResource.getFromDate(), leaveApplicationResource.getToDate()).getDays();
//                    switch (leaveType) {
//                        case SICK_LEAVES -> {
//                            int sickLeaveCount = employee.getLeaves().getSickLeaves();
//                            if (numberOfAppliedDays > 3) {
//                                errorMessages.add("You cannot apply sick leaves for more that 3 days");
//                            }
//                            if (sickLeaveCount < numberOfAppliedDays) {
//                                errorMessages.add(String.format(LEAVE_ERROR_MESSAGE, sickLeaveCount, leaveType));
//                            }
//                        }
//                        case BEREAVEMENT_LEAVES -> {
//                            int bereavementLeaves = employee.getLeaves().getSickLeaves();
//                            if (bereavementLeaves < numberOfAppliedDays) {
//                                errorMessages.add(String.format(LEAVE_ERROR_MESSAGE, bereavementLeaves, leaveType));
//                            }
//                        }
//                        case PRIVILEGE_LEAVES -> {
//                            int privilegeLeaveCount = employee.getLeaves().getPrivilegeLeaves();
//                            if (privilegeLeaveCount < numberOfAppliedDays) {
//                                errorMessages.add(String.format(LEAVE_ERROR_MESSAGE, privilegeLeaveCount, leaveType));
//                            }
//                        }
//                    }
//                }
//            }
//
//        }else {
//            errorMessages.add("Please enter a valid Employee ID.");
//        }

//        return errorMessages;
        return null;
    }

}
