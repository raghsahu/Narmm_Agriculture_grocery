package com.drraisingh.narmm.Model.UseableModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghvendra Sahu on 07-Jan-20.
 */
public class Cartlist_Main_model {

    @SerializedName("data")
    List<Cartlist_model> product=null;

    public List<Cartlist_model> getProduct() {
        return product;
    }

    public void setProduct(List<Cartlist_model> product) {
        this.product = product;
    }
}
