package com.example.growup.data.api.auth;

import com.google.gson.annotations.SerializedName;

public class RefreshRequest {
    @SerializedName("refresh")
    private String refresh;

    public RefreshRequest(String refresh) {
        this.refresh = refresh;
    }

    public String getRefresh() {
        return refresh;
    }
//
//    public void setRefresh(String refresh) {
//        this.refresh = refresh;
//    }

}
