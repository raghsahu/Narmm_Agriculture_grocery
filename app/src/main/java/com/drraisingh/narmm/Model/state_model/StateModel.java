package com.drraisingh.narmm.Model.state_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raghvendra Sahu on 28-Jan-20.
 */
public class StateModel {


    @SerializedName("responce")
    @Expose
    private Boolean responce;
    @SerializedName("data")
    @Expose
    private List<StateModelData> data = null;

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }

    public List<StateModelData> getData() {
        return data;
    }

    public void setData(List<StateModelData> data) {
        this.data = data;
    }
}
