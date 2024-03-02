package com.ust.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name="leave_application")
public class LeaveApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private long applicationId;

    private LocalDate fromDate;

    private LocalDate toDate;

    private boolean isLeaveApproved;

    private boolean isLeaveRejected;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    private int numerOfDays;

    public int getNumerOfDays() {
        return numerOfDays;
    }

    public void setNumerOfDays(int numerOfDays) {
        this.numerOfDays = numerOfDays;
    }

    public boolean isLeaveRejected() {
        return isLeaveRejected;
    }

    public void setLeaveRejected(boolean leaveRejected) {
        isLeaveRejected = leaveRejected;
    }

    private String leaveReason;

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public boolean isLeaveApproved() {
        return isLeaveApproved;
    }

    public void setLeaveApproved(boolean leaveApproved) {
        isLeaveApproved = leaveApproved;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}


