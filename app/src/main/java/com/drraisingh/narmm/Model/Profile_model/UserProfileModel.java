package com.drraisingh.narmm.Model.Profile_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 18-Jan-20.
 */
public class UserProfileModel {
    @SerializedName("responce")
    @Expose
    private Boolean responce;
    @SerializedName("data")
    @Expose
    private UserProfileModelData data;

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }

    public UserProfileModelData getData() {
        return data;
    }

    public void setData(UserProfileModelData data) {
        this.data = data;
    }

}
