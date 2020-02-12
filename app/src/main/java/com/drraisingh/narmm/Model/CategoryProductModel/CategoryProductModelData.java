package com.drraisingh.narmm.Model.CategoryProductModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raghvendra Sahu on 26-Dec-19.
 */
public class CategoryProductModelData {
    @SerializedName("heading")
    @Expose
    private String heading;
    @SerializedName("product")
    @Expose
    private List<CategoryModelDataProduct> product = null;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public List<CategoryModelDataProduct> getProduct() {
        return product;
    }

    public void setProduct(List<CategoryModelDataProduct> product) {
        this.product = product;
    }
}
