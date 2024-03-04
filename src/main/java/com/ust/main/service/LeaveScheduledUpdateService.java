package com.ust.main.service;

import com.ust.main.model.LeaveBalance;
import com.ust.main.repository.LeaveBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveScheduledUpdateService {

    @Autowired
    private LeaveBalanceRepository leaveBalanceRepository;

    @Scheduled(fixedDelay = 120000L)
    void updateLeaveBalance() {
        System.out.println("Inside Scheduler");
        List<LeaveBalance> leaveBalanceEntities = leaveBalanceRepository.findLeaveBalanceByLeaveTypeForActiveEmployees("Earned_Leave");

        leaveBalanceEntities = leaveBalanceEntities.stream().filter(leaveBalance -> leaveBalance.getBalance() < 30).map(leaveBalance -> {
             leaveBalance.setBalance(leaveBalance.getBalance() + 1);
             return leaveBalance;
        }).toList();

        System.out.printf("Created modified list with size %s and now saving it into db%n",leaveBalanceEntities.size());
        leaveBalanceRepository.saveAll(leaveBalanceEntities);
    }
}
