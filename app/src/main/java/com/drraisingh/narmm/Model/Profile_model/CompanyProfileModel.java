package com.drraisingh.narmm.Model.Profile_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 27-Dec-19.
 */
public class CompanyProfileModel {

    @SerializedName("responce")
    @Expose
    private Boolean responce;
    @SerializedName("data")
    @Expose
    private CompanyProfileModelData data;

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }

    public CompanyProfileModelData getData() {
        return data;
    }

    public void setData(CompanyProfileModelData data) {
        this.data = data;
    }
}
