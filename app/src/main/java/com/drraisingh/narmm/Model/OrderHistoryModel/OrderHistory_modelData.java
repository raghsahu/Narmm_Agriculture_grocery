package com.drraisingh.narmm.Model.OrderHistoryModel;

import com.drraisingh.narmm.Model.UseableModel.Shop_address_model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raghvendra Sahu on 03-Jan-20.
 */
public class OrderHistory_modelData {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("happy_code")
    @Expose
    private String happyCode;
    @SerializedName("order_date")
    @Expose
    private String order_date;
    @SerializedName("item")
    @Expose
    private List<OrderHistory_modelData_Item> item = null;

    @SerializedName("shop_detail")
    @Expose
    private Shop_address_details shopDetail;

    public Shop_address_details getShopDetail() {
        return shopDetail;
    }

    public void setShopDetail(Shop_address_details shopDetail) {
        this.shopDetail = shopDetail;
    }
    public String getOrderId() {
        return orderId;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getHappyCode() {
        return happyCode;
    }

    public void setHappyCode(String happyCode) {
        this.happyCode = happyCode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderHistory_modelData_Item> getItem() {
        return item;
    }

    public void setItem(List<OrderHistory_modelData_Item> item) {
        this.item = item;
    }

}
