package com.drraisingh.narmm.Model.CategoryWithCompanyModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raghvendra Sahu on 29-Dec-19.
 */
public class CategoryCompanyModel  {

    @SerializedName("responce")
    @Expose
    private Boolean responce;
    @SerializedName("data")
    @Expose
    private List<CategoryCompanyModelData> data = null;

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }

    public List<CategoryCompanyModelData> getData() {
        return data;
    }

    public void setData(List<CategoryCompanyModelData> data) {
        this.data = data;
    }
}
