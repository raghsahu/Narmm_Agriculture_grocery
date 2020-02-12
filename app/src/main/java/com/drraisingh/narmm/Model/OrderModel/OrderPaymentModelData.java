package com.drraisingh.narmm.Model.OrderModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 29-Dec-19.
 */
public class OrderPaymentModelData {

    @SerializedName("sale_id")
    @Expose
    private String saleId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("on_date")
    @Expose
    private String onDate;
    @SerializedName("delivery_time_from")
    @Expose
    private String deliveryTimeFrom;
    @SerializedName("delivery_time_to")
    @Expose
    private String deliveryTimeTo;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("is_paid")
    @Expose
    private String isPaid;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("total_kg")
    @Expose
    private String totalKg;
    @SerializedName("total_items")
    @Expose
    private String totalItems;
    @SerializedName("socity_id")
    @Expose
    private String socityId;
    @SerializedName("delivery_address")
    @Expose
    private String deliveryAddress;
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("happy_code")
    @Expose
    private String happyCode;

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOnDate() {
        return onDate;
    }

    public void setOnDate(String onDate) {
        this.onDate = onDate;
    }

    public String getDeliveryTimeFrom() {
        return deliveryTimeFrom;
    }

    public void setDeliveryTimeFrom(String deliveryTimeFrom) {
        this.deliveryTimeFrom = deliveryTimeFrom;
    }

    public String getDeliveryTimeTo() {
        return deliveryTimeTo;
    }

    public void setDeliveryTimeTo(String deliveryTimeTo) {
        this.deliveryTimeTo = deliveryTimeTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalKg() {
        return totalKg;
    }

    public void setTotalKg(String totalKg) {
        this.totalKg = totalKg;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getSocityId() {
        return socityId;
    }

    public void setSocityId(String socityId) {
        this.socityId = socityId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getHappyCode() {
        return happyCode;
    }

    public void setHappyCode(String happyCode) {
        this.happyCode = happyCode;
    }
}
