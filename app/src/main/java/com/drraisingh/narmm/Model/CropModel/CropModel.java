package com.drraisingh.narmm.Model.CropModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raghvendra Sahu on 27-Dec-19.
 */
public class CropModel {


    @SerializedName("responce")
    @Expose
    private Boolean responce;
    @SerializedName("data")
    @Expose
    private List<CropModelData> data = null;

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }

    public List<CropModelData> getData() {
        return data;
    }

    public void setData(List<CropModelData> data) {
        this.data = data;
    }

}
