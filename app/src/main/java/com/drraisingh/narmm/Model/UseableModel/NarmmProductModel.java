package com.drraisingh.narmm.Model.UseableModel;

/**
 * Created by Raghvendra Sahu on 05-Jan-20.
 */
public class NarmmProductModel {
    String id; String product_name; String image; String status;

    public NarmmProductModel(String id, String product_name, String image) {
        this.id = id;
        this.product_name = product_name;
        this.image = image;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
