package com.drraisingh.narmm.Model.UseableModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Raghvendra Sahu on 07-Jan-20.
 */
public class Cartlist_model implements Parcelable {
    String qty;
    String addtocart_id;
    String product_id;
    String product_name;
    String technical_name;
    String product_image;
    String price;
    String unit_price;
    String unit;
    String unit_value;
    String Mrp;

    public String getProduct_id() {
        return product_id;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getTechnical_name() {
        return technical_name;
    }

    public void setTechnical_name(String technical_name) {
        this.technical_name = technical_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit_value() {
        return unit_value;
    }

    public void setUnit_value(String unit_value) {
        this.unit_value = unit_value;
    }

    public String getMrp() {
        return Mrp;
    }

    public void setMrp(String mrp) {
        Mrp = mrp;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getAddtocart_id() {
        return addtocart_id;
    }

    public void setAddtocart_id(String addtocart_id) {
        this.addtocart_id = addtocart_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
