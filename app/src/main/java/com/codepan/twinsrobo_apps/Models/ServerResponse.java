package com.codepan.twinsrobo_apps.Models;

import com.google.gson.annotations.SerializedName;

public class
ServerResponse {
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }
}
