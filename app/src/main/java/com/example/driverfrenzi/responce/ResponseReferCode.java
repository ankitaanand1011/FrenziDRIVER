package com.example.driverfrenzi.responce;

public class ResponseReferCode {
    public Integer status;
    public String message;
    public String response;

    public ResponseReferCode(Integer status, String message,String response) {
        this.status = status;
        this.message = message;
        this.response = response;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
