package com.drraisingh.narmm.Model.OrderHistoryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raghvendra Sahu on 03-Jan-20.
 */
public class OrderHistory_model {

    @SerializedName("responce")
    @Expose
    private Boolean responce;
    @SerializedName("data")
    @Expose
    private List<OrderHistory_modelData> data = null;

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }

    public List<OrderHistory_modelData> getData() {
        return data;
    }

    public void setData(List<OrderHistory_modelData> data) {
        this.data = data;
    }
}
