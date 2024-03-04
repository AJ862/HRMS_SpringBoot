package com.ust.main.service;

import com.ust.main.model.*;
import com.ust.main.repository.*;
import com.ust.main.response.ConfirmMessage;
import com.ust.main.response.EmployeeResponse;
import com.ust.main.response.LeaveApplicationResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    LeaveRepository leaveRepository;

    @Autowired
    LeaveBalanceRepository leaveBalanceRepository;
    @Autowired
    LeaveMasterRepository leaveMasterRepository;
    @Autowired
    LeaveApplicationRepository leaveApplicationRepository;

//    @Value("${leaves.numOfLeaves}")
//    private int numOfLeaves;

//    @Value("${leaves.privilege}")
//    private int privilegeLeaves;
//
//    @Value("${leaves.sick.leave}")
//    private int sickLeaves;
//
//    @Value("${leaves.bereavement}")
//    private int bereavementLeaves;


    public boolean checkIfEmployeeIsHR(long hrId){
        Optional<Employee> optionalEmployee = employeeRepository.findById(hrId);

        if(optionalEmployee.isPresent() && optionalEmployee.get().isCurrentEmployee() && optionalEmployee.get().getRole() == Role.HR){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean employeeExistsById(long employeeId) {
        if (employeeRepository.existsById(employeeId))
            return true;
        else
            return false;
    }

    public boolean employeeExistsByEmailId(String emailId) {
        if (employeeRepository.existsByEmailId(emailId)) {
            return true;
        } else
            return false;
    }

    public boolean isListEmpty() {
        if (employeeRepository.findAll().isEmpty()) {
            return true;
        } else
            return false;
    }

    public ResponseEntity<EmployeeResponse> getAllEmployees() {
        EmployeeResponse getAllEmployeeResponse = new EmployeeResponse();

        if (isListEmpty()) {
            getAllEmployeeResponse.setResponseMessage("Employee List is Empty!");
            return new ResponseEntity<>(getAllEmployeeResponse, HttpStatus.BAD_REQUEST);
        } else {
            getAllEmployeeResponse.setEmployeeResponseList(employeeRepository.findAll());
            getAllEmployeeResponse.setResponseMessage("Employees Found!");
            return new ResponseEntity<>(getAllEmployeeResponse, HttpStatus.OK);
        }
    }

    public List<Employee> getAllEmployeesList(){
        return employeeRepository.findAll();
    }


    public ResponseEntity<EmployeeResponse> getActiveEmployees() {
        EmployeeResponse getActiveEmployeeResponse = new EmployeeResponse();

        if (isListEmpty()) {
            getActiveEmployeeResponse.setResponseMessage("Employee List is Empty!");
            return new ResponseEntity<>(getActiveEmployeeResponse, HttpStatus.BAD_REQUEST);
        } else {
            List<Employee> employeeList = employeeRepository.findAll().stream().filter(Employee::isCurrentEmployee).collect(Collectors.toList());
            if (employeeList.isEmpty()) {
                getActiveEmployeeResponse.setResponseMessage("There are no active Employees!");
                return new ResponseEntity<>(getActiveEmployeeResponse, HttpStatus.BAD_REQUEST);
            } else {
                getActiveEmployeeResponse.setEmployeeResponseList(employeeList);
                getActiveEmployeeResponse.setResponseMessage("List of Active Employees");
                return new ResponseEntity<>(getActiveEmployeeResponse, HttpStatus.OK);
            }
        }
    }

    public Employee getEmployeeByEmployeeId(long employeeId) {
        Optional<Employee> employeeOptional =  employeeRepository.findById(employeeId);
        return employeeOptional.isPresent() ? employeeOptional.get() : null;

    }

    public ResponseEntity<EmployeeResponse> getEmployeeById(long employeeId) {
        EmployeeResponse getByIdEmployeeResponse = new EmployeeResponse();
        if (employeeExistsById(employeeId)) {
            getByIdEmployeeResponse.setResponseMessage("Employee Details Found!!");
            getByIdEmployeeResponse.setEmployeeResponse(employeeRepository.findById(employeeId).get());
//            getByIdEmployeeResponse.setHttpStatus(HttpStatus.OK);
            return new ResponseEntity<>(getByIdEmployeeResponse, HttpStatus.OK);
        } else {
            getByIdEmployeeResponse.setResponseMessage("Employee does not exist");
            getByIdEmployeeResponse.setEmployeeResponse(null);
//            getByIdEmployeeResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(getByIdEmployeeResponse, HttpStatus.BAD_REQUEST);
        }
    }


    public Employee saveUpdatedEmployee(Employee employee) {
        employeeRepository.findById(employee.getEmployeeId());
       return employeeRepository.saveAndFlush(employee);
    }

    public ResponseEntity<EmployeeResponse> saveEmployee(Employee employee) {
        EmployeeResponse saveEmployeeResponse = new EmployeeResponse();

        System.out.println("Exists By Email Id: " + employeeExistsByEmailId(employee.getEmailId()));

        if (employeeExistsByEmailId(employee.getEmailId())) {
//          saveEmployeeResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
            saveEmployeeResponse.setResponseMessage("Employee Already Exists!");
            return new ResponseEntity<>(saveEmployeeResponse, HttpStatus.CONFLICT);
        } else {

            employee.setCurrentEmployee(true);
            employee.setLastWorkingDay(null);
            employee.setLeaveBalances(new LeavesMaster().getLeaveBalances());


//            LeavesMaster leavesMaster = new LeavesMaster();
//            leaveMaster.setBereavementLeaves(this.bereavementLeaves);
//            leaveMaster.setSickLeaves(this.sickLeaves);
//            leaveMaster.setPrivilegeLeaves(this.privilegeLeaves);
//            employee.setLeaves(leavesMaster);
            Employee savedEmployee =  employeeRepository.save(employee);
            initializeLeaveBalances(savedEmployee);
            saveEmployeeResponse.setResponseMessage("Employee Details Saved Successfully.");

            saveEmployeeResponse.setEmployeeResponse(employeeRepository.findByEmailId(employee.getEmailId()));

            return new ResponseEntity<>(saveEmployeeResponse, HttpStatus.OK);
        }
    }

    private void initializeLeaveBalances(Employee employee) {
        // Fetch all leave types from the LeaveMaster table
        List<LeavesMaster> leaveMasters = leaveMasterRepository.findAll();

        // Create initial leave balances for each leave type
        for (LeavesMaster leaveMaster : leaveMasters) {
            LeaveBalance leaveBalance = new LeaveBalance();
            leaveBalance.setEmployee(employee);
            leaveBalance.setLeavesMaster(leaveMaster);
            leaveBalance.setBalance(leaveMaster.getInitialBalance());

            leaveBalanceRepository.save(leaveBalance);
        }}

    public void deleteEmployeeById(long employeeId) {
        employeeRepository.deleteById(employeeId);
    }


    public ResponseEntity<String> deleteEmployee(long employeeId, LocalDate LWD) {

        if (employeeRepository.existsById(employeeId) && employeeRepository.findById(employeeId).get().isCurrentEmployee()) {

            Employee tempEmployee = employeeRepository.findById(employeeId).get();
            tempEmployee.setCurrentEmployee(false);
            tempEmployee.setLastWorkingDay(LWD);
            tempEmployee.setEmployeeId(employeeId);
            tempEmployee.setDateOfBirth(tempEmployee.getDateOfBirth());
            tempEmployee.setCurrentEmployee(tempEmployee.isCurrentEmployee());
            tempEmployee.setDateOfJoining(tempEmployee.getDateOfJoining());
            tempEmployee.setEmailId(tempEmployee.getEmailId());
            tempEmployee.setFirstName(tempEmployee.getFirstName());
            tempEmployee.setLastName(tempEmployee.getLastName());

            employeeRepository.save(tempEmployee);

            return ResponseEntity.status(HttpStatus.OK).body("Employee Deleted Successfully");

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee does not exist.");
        }

    }

    public ResponseEntity<EmployeeResponse> updateEmployee(Employee updateEmployee, long id) {
        EmployeeResponse updateEmployeeResponse = new EmployeeResponse();
        if (employeeRepository.existsById(id)) {
            Employee tempEmployee = employeeRepository.findById(id).get();
            tempEmployee.setDateOfBirth(updateEmployee.getDateOfBirth());
            tempEmployee.setCurrentEmployee(updateEmployee.isCurrentEmployee());
            tempEmployee.setDateOfJoining(updateEmployee.getDateOfJoining());
            tempEmployee.setEmailId(updateEmployee.getEmailId());
            tempEmployee.setFirstName(updateEmployee.getFirstName());
            tempEmployee.setLastName(updateEmployee.getLastName());
            tempEmployee.setEmployeeId(id);
            employeeRepository.save(tempEmployee);

            updateEmployeeResponse.setEmployeeResponse(employeeRepository.findById(id).get());
            updateEmployeeResponse.setResponseMessage("Employee Data Updated Successfully.");

            return new ResponseEntity<>(updateEmployeeResponse, HttpStatus.OK);
        } else {
            updateEmployeeResponse.setResponseMessage("Employee Does not exist!");
            return new ResponseEntity<>(updateEmployeeResponse, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<EmployeeResponse> applyLeaves(Employee employeeLeaves, long empId) {
//        Optional<Employee> optionalTempEmployee = employeeRepository.findById(empId);
//        EmployeeResponse leaveEmployeeResponse = new EmployeeResponse();
//
//        if(optionalTempEmployee.isPresent() && optionalTempEmployee.get().isCurrentEmployee()){
//            Employee tempEmployee = optionalTempEmployee.get();
//            int requestedLeaves = employeeLeaves.getAppliedLeaves();
//
//            if (requestedLeaves > tempEmployee.getLeaves()){
//                leaveEmployeeResponse.setResponseMessage("Your remaining leaves are "+tempEmployee.getLeaves()+". Please select valid number of leaves to apply.");
//                return new ResponseEntity<>(leaveEmployeeResponse, HttpStatus.BAD_REQUEST);
//            }else{
//                leaveEmployeeResponse.setAppliedLeaves(tempEmployee.getAppliedLeaves() + requestedLeaves);
//                leaveEmployeeResponse.setResponseMessage("Leave request sent successfully.");
//                leaveEmployeeResponse.setLeaves(tempEmployee.getLeaves());
//                tempEmployee.setAppliedLeaves(tempEmployee.getAppliedLeaves() + requestedLeaves);
//                tempEmployee.setEmployeeId(empId);
//                employeeRepository.save(tempEmployee);
//
//
//
//                return new ResponseEntity<>(leaveEmployeeResponse, HttpStatus.OK);
//            }
        return null;
    }

    public ResponseEntity<EmployeeResponse> getMyLeavesData(long empId){
        Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
        EmployeeResponse leaveResponse = new EmployeeResponse();

        if (optionalEmployee.isPresent() && optionalEmployee.get().isCurrentEmployee()){

//            LeavesMaster tempLeavesMaster = optionalEmployee.get().getLeaves();

//            leaveResponse.setLeaves(tempLeavesMaster);
            leaveResponse.setResponseMessage("Here is your leave Data");
            return new ResponseEntity<>(leaveResponse, HttpStatus.OK);
        }else{
            leaveResponse.setResponseMessage("Please enter a valid employee Id.");
            return new ResponseEntity<>(leaveResponse, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<List<LeaveApplicationResponse>> getAllLeavesData(long employeeId) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        List<LeaveApplicationResponse> leaveApplicationResponse = (List<LeaveApplicationResponse>) new LeaveApplicationResponse();

        if(optionalEmployee.isPresent() && optionalEmployee.get().isCurrentEmployee() && optionalEmployee.get().getRole() == Role.HR){

           leaveApplicationResponse.add((LeaveApplicationResponse) leaveApplicationRepository.findAll());

            return new ResponseEntity<>(leaveApplicationResponse, HttpStatus.OK);
        }
return null;
    }

    public ResponseEntity<ConfirmMessage> approveLeave(long hrId, long appId, int action) {
//        Optional<Employee> optionalEmployee = employeeRepository.findById(hrId);
//        Optional<LeaveApplication> optionalLeaveApplication = leaveApplicationRepository.findById(appId);
//        ConfirmMessage confirmMessage = new ConfirmMessage();
//
//        if(optionalEmployee.isPresent() && optionalEmployee.get().isCurrentEmployee() && optionalEmployee.get().getRole() == Role.HR && optionalLeaveApplication.isPresent()){
//
//            LeaveApplication leaveApplication = optionalLeaveApplication.get();
//
//            if(leaveApplication.isLeaveApproved() || leaveApplication.isLeaveRejected()){
//                confirmMessage.setRequestStatus("Leave has already been operated on!");
//                return new ResponseEntity<>(confirmMessage, HttpStatus.BAD_REQUEST);
//            }else if(action == 1){
//                leaveApplication.setLeaveApproved(true);
//                leaveApplication.setApplicationId(appId);
//                Employee employee = leaveApplication.getEmployee();
//                LeavesMaster leavesMaster = employee.getLeaves();
//
//                if(leaveApplication.getLeaveType() == LeaveType.PRIVILEGE_LEAVES){
//                   leavesMaster.setPrivilegeLeaves(leavesMaster.getPrivilegeLeaves() - leaveApplication.getNumerOfDays());
//                } else if (leaveApplication.getLeaveType() == LeaveType.BEREAVEMENT_LEAVES) {
//                    leavesMaster.setBereavementLeaves(leavesMaster.getBereavementLeaves() - leaveApplication.getNumerOfDays());
//                }else {
//                    leavesMaster.setSickLeaves(leavesMaster.getSickLeaves() - leaveApplication.getNumerOfDays());
//                }
//
//                //employee.setLeaves(leaves);
//                updateEmployee(employee, leaveApplication.getEmployee().getEmployeeId());
//
//                employeeRepository.save(employee);
//                leaveApplicationRepository.save(leaveApplication);
//                confirmMessage.setRequestStatus("Leave Approved!");
//
//                return new ResponseEntity<>(confirmMessage, HttpStatus.OK);
//            }else if(action == 0){
//                leaveApplication.setLeaveRejected(true);
//                leaveApplication.setApplicationId(appId);
//                leaveApplicationRepository.save(leaveApplication);
//                confirmMessage.setRequestStatus("Leave Rejected!");
//
//                return new ResponseEntity<>(confirmMessage, HttpStatus.OK);
//            }
//        }
//        else{
//            confirmMessage.setRequestStatus("Oops! Something went wrong...");
//            return new ResponseEntity<>(confirmMessage, HttpStatus.UNAUTHORIZED);
//        }
//
//        return null;
        return null;
    }

}


