package com.GlamourByNora.api.response;

public class ApiResponseMessages<G>{
    private String message;
    private G data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public G getData() {
        return data;
    }

    public void setData(G data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
