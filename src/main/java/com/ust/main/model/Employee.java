package com.ust.main.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeId;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String emailId;

    private LocalDate dateOfBirth;

    private LocalDate dateOfJoining;

    private boolean isCurrentEmployee;

    private LocalDate lastWorkingDay;

    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "leave_id")
    private Leaves leaves;


    @OneToMany(mappedBy =  "employee")
    private List<LeaveApplication> leaveApplication;


    public Leaves getLeaves() {
        return leaves;
    }

    public void setLeaves(Leaves leaves) {
        this.leaves = leaves;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public long getEmployeeId() {
        return employeeId;
    }


    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(LocalDate dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public boolean isCurrentEmployee() {
        return isCurrentEmployee;
    }

    public void setCurrentEmployee(boolean currentEmployee) {
        isCurrentEmployee = currentEmployee;
    }

    public LocalDate getLastWorkingDay() {
        return lastWorkingDay;
    }

    public void setLastWorkingDay(LocalDate lastWorkingDay) {
        this.lastWorkingDay = lastWorkingDay;
    }

    public List<LeaveApplication> getLeaveApplication() {
        return leaveApplication;
    }

    public void setLeaveApplication(List<LeaveApplication> leaveApplication) {
        this.leaveApplication = leaveApplication;
    }
}
