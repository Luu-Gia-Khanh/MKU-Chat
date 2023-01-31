package com.example.mku_chat.Notification;

public class MyResponse {
    public int success;

    public MyResponse(int success) {
        this.success = success;
    }

    public MyResponse() {
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
