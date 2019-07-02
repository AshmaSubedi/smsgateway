package com.himalayantechies.smsapp.models;

import android.provider.Telephony;

import com.google.gson.annotations.SerializedName;

public class Auth {


    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("token")
    private String token;

    @SerializedName("updatedAt")
    private String updateAt;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("id")
    private Integer id;

    @SerializedName("device_ip")
    private String device_id;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}
