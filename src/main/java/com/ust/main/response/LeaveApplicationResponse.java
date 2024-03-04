package com.ust.main.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ust.main.model.LeaveApplication;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LeaveApplicationResponse {

    private LeaveApplication leaveApplication;

    private String responseMessage;

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public LeaveApplication getLeaveApplication() {
        return leaveApplication;
    }

    public void setLeaveApplication(LeaveApplication leaveApplication) {
        this.leaveApplication = leaveApplication;
    }
}
