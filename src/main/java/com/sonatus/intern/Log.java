package com.sonatus.intern;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Log {
    @JsonProperty("service_name")
    private String serviceName;
    private Instant timestamp;
    private String message;

    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}