package com.drraisingh.narmm.Model.ProductDetailsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 26-Dec-19.
 */
public class ProductDetailsModel {

    @SerializedName("responce")
    @Expose
    private Boolean responce;
    @SerializedName("data")
    @Expose
    private ProductDetailsData data;

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }

    public ProductDetailsData getData() {
        return data;
    }

    public void setData(ProductDetailsData data) {
        this.data = data;
    }
}
