package com.ust.main.model;

import jakarta.persistence.*;

@Entity
public class LeaveBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private double leave_balance_id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "leave_id")
    private LeavesMaster leavesMaster;

    //todo- Change balance form int to double
    private int balance;


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LeavesMaster getLeavesMaster() {
        return leavesMaster;
    }

    public void setLeavesMaster(LeavesMaster leavesMaster) {
        this.leavesMaster = leavesMaster;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
