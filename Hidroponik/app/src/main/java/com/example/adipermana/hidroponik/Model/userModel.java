package com.example.adipermana.hidroponik.Model;


import com.google.gson.annotations.SerializedName;

public class userModel {
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("alamat")
    private String alamat;

    private String token ;

    public userModel(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.alamat = alamat;
        this.token = token ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
