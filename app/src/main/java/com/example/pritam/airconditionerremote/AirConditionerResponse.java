package com.example.pritam.airconditionerremote;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class AirConditionerResponse implements Serializable {

    @SerializedName ("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
