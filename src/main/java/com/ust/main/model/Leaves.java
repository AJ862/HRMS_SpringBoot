package com.ust.main.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity(name = "employee_leaves")
@Table(name = "leaves")
public class Leaves {

//    EARNED_LEAVES,
//    SICK_LEAVES,
//    BEREAVEMENT_LEAVES


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_id")
    private int leaveId;

    private int privilegeLeaves;

    private int sickLeaves;

    private int bereavementLeaves;

//    private int numOfLeaves;

    @OneToOne(mappedBy = "leaves")
    @JsonIgnore
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getPrivilegeLeaves() {
        return privilegeLeaves;
    }

    public void setPrivilegeLeaves(int privilegeLeaves) {
        this.privilegeLeaves = privilegeLeaves;
    }

    public int getSickLeaves() {
        return sickLeaves;
    }

    public void setSickLeaves(int sickLeaves) {
        this.sickLeaves = sickLeaves;
    }

    public int getBereavementLeaves() {
        return bereavementLeaves;
    }

    public void setBereavementLeaves(int bereavementLeaves) {
        this.bereavementLeaves = bereavementLeaves;
    }
}
