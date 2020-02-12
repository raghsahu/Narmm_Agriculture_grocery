package com.drraisingh.narmm.Model.ProductDetailsModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 26-Dec-19.
 */
public class ProductDetailsData {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("technical_name")
    @Expose
    private String technicalName;
    @SerializedName("product_description")
    @Expose
    private String productDescription;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("in_stock")
    @Expose
    private String inStock;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("unit_value")
    @Expose
    private String unitValue;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("Mrp")
    @Expose
    private String mrp;
    @SerializedName("addedby")
    @Expose
    private String addedby;
    @SerializedName("features")
    @Expose
    private String features;
    @SerializedName("dosage")
    @Expose
    private String dosage;
    @SerializedName("insect")
    @Expose
    private String insect;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("other")
    @Expose
    private String other;
    @SerializedName("disease")
    @Expose
    private String disease;
    @SerializedName("deficiency")
    @Expose
    private String deficiency;
    @SerializedName("weed")
    @Expose
    private String weed;

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getDeficiency() {
        return deficiency;
    }

    public void setDeficiency(String deficiency) {
        this.deficiency = deficiency;
    }

    public String getWeed() {
        return weed;
    }

    public void setWeed(String weed) {
        this.weed = weed;
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

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getInStock() {
        return inStock;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getInsect() {
        return insect;
    }

    public void setInsect(String insect) {
        this.insect = insect;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
