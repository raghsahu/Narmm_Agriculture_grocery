package com.drraisingh.narmm.Model.OrderModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 29-Dec-19.
 */
public class OrderPaymentModel {

    @SerializedName("responce")
    @Expose
    private Boolean responce;
    @SerializedName("data")
    @Expose
    private OrderPaymentModelData data;

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }

    public OrderPaymentModelData getData() {
        return data;
    }

    public void setData(OrderPaymentModelData data) {
        this.data = data;
    }
}
