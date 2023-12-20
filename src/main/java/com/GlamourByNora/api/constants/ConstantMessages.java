package com.GlamourByNora.api.constants;

public enum ConstantMessages {
    SUCCESS("Successful", 1),
    FAILED("Failed", 2),
    CREATED("Created Successfully", 3),
    UNAUTHORIZED("Not eligible", 4),
    NOT_FOUND("Cannot be found",5),
    UPDATED("Successfully Updated",6),
    EXIST("Email already in use",7),
    DELETED("Deleted", 8),
    FOUND("Found",9),
    INCORRECT("Username or Password Incorrect!",10);

    private String message;
    private int status;

    ConstantMessages(String message, int status) {
        this.message=message;
        this.status=status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ApiResponseMessage{" +
                "message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
