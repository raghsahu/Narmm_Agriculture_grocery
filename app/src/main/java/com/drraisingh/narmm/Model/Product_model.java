package com.drraisingh.narmm.Model;

/**
 * Created by Rajesh Dabhi on 26/6/2017.
 */

public class Product_model {

    String product_id;
    String product_name;
    String product_description;
    String product_image;
    String category_id;
    String in_stock;
    String price;
    String technical_name;
    String unit_value;
    String unit;
    String increament;
    String title;

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public String getProduct_image() {
        return product_image;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getIn_stock() {
        return in_stock;
    }

    public String getPrice() {
        return price;
    }

    public String getUnit_value() {
        return unit_value;
    }

    public String getUnit() {
        return unit;
    }

    public String getTechnical_name() {
        return technical_name;
    }

    public void setTechnical_name(String technical_name) {
        this.technical_name = technical_name;
    }

    public String getIncreament() {
        return increament;
    }

    public String getTitle() {
        return title;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public void setIn_stock(String in_stock) {
        this.in_stock = in_stock;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUnit_value(String unit_value) {
        this.unit_value = unit_value;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
