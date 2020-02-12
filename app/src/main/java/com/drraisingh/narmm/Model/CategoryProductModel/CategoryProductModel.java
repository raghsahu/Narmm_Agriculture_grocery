package com.drraisingh.narmm.Model.CategoryProductModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raghvendra Sahu on 26-Dec-19.
 */
public class CategoryProductModel {


    @SerializedName("responce")
    @Expose
    private Boolean responce;
    @SerializedName("data")
    @Expose
    private List<CategoryProductModelData> data = null;

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }

    public List<CategoryProductModelData> getData() {
        return data;
    }

    public void setData(List<CategoryProductModelData> data) {
        this.data = data;
    }
}
