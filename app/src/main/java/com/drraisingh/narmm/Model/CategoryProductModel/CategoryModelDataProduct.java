package com.drraisingh.narmm.Model.CategoryProductModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 26-Dec-19.
 */
public class CategoryModelDataProduct {
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("technical_name")
    @Expose
    private String technicalName;
    @SerializedName("product_image")
    @Expose
    private String productImage;

    public CategoryModelDataProduct(String id, String product_name, String technical_name, String product_image) {
        this.productId=id;
        this.productName=product_name;
        this.technicalName=technical_name;
        this.productImage=product_image;

    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
}
