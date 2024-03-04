package com.ust.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "leaves_master")
public class LeavesMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_id")
    private int leaveId;

    @Column(unique = true, nullable = false)
    private String leaveType;

    @Column(name = "initial_balance")
    private int initialBalance;

    @OneToMany
    @JsonIgnore
    private List<LeaveBalance> leaveBalances;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public List<LeaveBalance> getLeaveBalances() {
        return leaveBalances;
    }

    public void setLeaveBalances(List<LeaveBalance> leaveBalances) {
        this.leaveBalances = leaveBalances;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public int getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(int initialBalance) {
        this.initialBalance = initialBalance;
    }

    //    private int privilegeLeaves;
//
//    private int sickLeaves;
//
//    private int bereavementLeaves;
//
////    private int numOfLeaves;
//
//    @OneToOne(mappedBy = "leaves")
//    @JsonIgnore
//    private Employee employee;
//
//    public Employee getEmployee() {
//        return employee;
//    }
//
//    public void setEmployee(Employee employee) {
//        this.employee = employee;
//    }
//
//    public int getPrivilegeLeaves() {
//        return privilegeLeaves;
//    }
//
//    public void setPrivilegeLeaves(int privilegeLeaves) {
//        this.privilegeLeaves = privilegeLeaves;
//    }
//
//    public int getSickLeaves() {
//        return sickLeaves;
//    }
//
//    public void setSickLeaves(int sickLeaves) {
//        this.sickLeaves = sickLeaves;
//    }
//
//    public int getBereavementLeaves() {
//        return bereavementLeaves;
//    }
//
//    public void setBereavementLeaves(int bereavementLeaves) {
//        this.bereavementLeaves = bereavementLeaves;
//    }
}
