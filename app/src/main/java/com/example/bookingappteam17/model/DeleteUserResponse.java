package com.example.bookingappteam17.model;

import java.io.Serializable;

public class DeleteUserResponse implements Serializable {
    private boolean success;
    private String message;

    public DeleteUserResponse() {
    }

    public DeleteUserResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString(){
        return "DeleteUserResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
