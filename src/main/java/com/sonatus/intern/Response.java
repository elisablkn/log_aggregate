package com.sonatus.intern;

import java.time.Instant;

public class Response {
    private Instant timestamp;
    private String message;

    public Response(Instant timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
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
