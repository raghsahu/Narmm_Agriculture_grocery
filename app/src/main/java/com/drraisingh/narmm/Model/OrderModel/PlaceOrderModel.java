package com.drraisingh.narmm.Model.OrderModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 29-Dec-19.
 */
public class PlaceOrderModel {

    @SerializedName("responce")
    @Expose
    private Boolean responce;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("is_paid")
    @Expose
    private Integer isPaid;
    @SerializedName("data")
    @Expose
    private String data;

    public Boolean getResponce() {
        return responce;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Integer isPaid) {
        this.isPaid = isPaid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
