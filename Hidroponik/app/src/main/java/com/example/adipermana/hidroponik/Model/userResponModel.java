package com.example.adipermana.hidroponik.Model;

import java.util.List;



public class userResponModel {

    private Integer success;
    private String message,token;

    /**
     * No args constructor for use in serialization
     */
    public userResponModel() {
    }

    /**
     * @param message
     * @param success
     */
    public userResponModel(Integer success, String message,String token) {
        super();
        this.success = success;
        this.message = message;
        this.token = token ;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }
    public String getToken() {
        return token;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
