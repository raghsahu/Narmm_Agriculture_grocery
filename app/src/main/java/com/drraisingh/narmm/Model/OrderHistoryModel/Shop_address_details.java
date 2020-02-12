package com.drraisingh.narmm.Model.OrderHistoryModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 05-Jan-20.
 */
public class Shop_address_details {

    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("prapriter_name")
    @Expose
    private String prapriterName;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("tehsil")
    @Expose
    private String tehsil;
    @SerializedName("block")
    @Expose
    private String block;
    @SerializedName("place_market")
    @Expose
    private String placeMarket;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPrapriterName() {
        return prapriterName;
    }

    public void setPrapriterName(String prapriterName) {
        this.prapriterName = prapriterName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getPlaceMarket() {
        return placeMarket;
    }

    public void setPlaceMarket(String placeMarket) {
        this.placeMarket = placeMarket;
    }
}
