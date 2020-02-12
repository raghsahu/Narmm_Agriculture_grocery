package com.drraisingh.narmm.Model.RegistraionModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 24-Dec-19.
 */
public class FarmerRegistrationModel {
    @SerializedName("responce")
    @Expose
    private Boolean responce;
    @SerializedName("data")
    @Expose
    private FarmerRegistrationModelData data;

    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("gst_amount")
    @Expose
    private String gst_amount;

    @SerializedName("net_amount")
    @Expose
    private String net_amount;

    @SerializedName("user")
    @Expose
    private FarmerRegistrationModelData user;

    public Boolean getResponce() {
        return responce;
    }

    public FarmerRegistrationModelData getUser() {
        return user;
    }

    public void setUser(FarmerRegistrationModelData user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setResponce(Boolean responce) {
        this.responce = responce;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getGst_amount() {
        return gst_amount;
    }

    public void setGst_amount(String gst_amount) {
        this.gst_amount = gst_amount;
    }

    public String getNet_amount() {
        return net_amount;
    }

    public void setNet_amount(String net_amount) {
        this.net_amount = net_amount;
    }

    public FarmerRegistrationModelData getData() {
        return data;
    }

    public void setData(FarmerRegistrationModelData data) {
        this.data = data;
    }
}
