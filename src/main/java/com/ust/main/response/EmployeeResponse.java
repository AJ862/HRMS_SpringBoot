package com.ust.main.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ust.main.model.Employee;
import com.ust.main.model.Leaves;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmployeeResponse {


   private Employee employeeResponse;

   private List<Employee> employeeResponseList;

   private String responseMessage;

   private LocalDate currentlyAppliedLeaveFrom;

   private LocalDate currentlyAppliedLeaveTo;

   private int approvedLeaves;

   public int getApprovedLeaves() {
      return approvedLeaves;
   }

   public void setApprovedLeaves(int approvedLeaves) {
      this.approvedLeaves = approvedLeaves;
   }

   private Leaves leaves;


   public LocalDate getCurrentlyAppliedLeaveTo() {
      return currentlyAppliedLeaveTo;
   }

   public void setCurrentlyAppliedLeaveTo(LocalDate currentlyAppliedLeaveTo) {
      this.currentlyAppliedLeaveTo = currentlyAppliedLeaveTo;
   }

   public Leaves getLeaves() {
      return leaves;
   }

   public LocalDate getCurrentlyAppliedLeaveFrom() {
      return currentlyAppliedLeaveFrom;
   }

   public void setCurrentlyAppliedLeaveFrom(LocalDate currentlyAppliedLeaveFrom) {
      this.currentlyAppliedLeaveFrom = currentlyAppliedLeaveFrom;
   }

   public void setLeaves(Leaves leaves) {
      this.leaves = leaves;
   }


   public Employee getEmployeeResponse() {
      return employeeResponse;
   }

   public void setEmployeeResponse(Employee employeeResponse) {
      this.employeeResponse = employeeResponse;
   }

   public String getResponseMessage() {
      return responseMessage;
   }

   public void setResponseMessage(String responseMessage) {
      this.responseMessage = responseMessage;
   }

   public List<Employee> getEmployeeResponseList() {
      return employeeResponseList;
   }

   public void setEmployeeResponseList(List<Employee> employeeResponseList) {
      this.employeeResponseList = employeeResponseList;
   }
}
