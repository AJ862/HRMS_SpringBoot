package com.ust.main.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ust.main.model.LeavesMaster;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LeaveMasterResponse {

    private LeavesMaster leavesMasterRecord;

    private String responseMessage;

    public LeavesMaster getLeavesMasterRecord() {
        return leavesMasterRecord;
    }

    public void setLeavesMasterRecord(LeavesMaster leavesMasterRecord) {
        this.leavesMasterRecord = leavesMasterRecord;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
