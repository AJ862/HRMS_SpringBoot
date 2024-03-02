package com.ust.main.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ConfirmMessage {

    private String requestStatus;
    private String processingStatus;
    private Set<String> processingMessage;


    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(String processingStatus) {
        this.processingStatus = processingStatus;
    }

    public Set<String> getProcessingMessage() {
        return processingMessage;
    }

    public void setProcessingMessage(Set<String> processingMessage) {
        this.processingMessage = processingMessage;
    }
}
