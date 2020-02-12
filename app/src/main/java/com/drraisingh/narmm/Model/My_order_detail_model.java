package com.drraisingh.narmm.Model;

/**
 * Created by Rajesh Dabhi on 30/6/2017.
 */

public class My_order_detail_model {

    String sale_item_id;
    String sale_id;
    String product_id;
    String product_name;
    String qty;
    String unit;
    String unit_value;
    String price;
    String qty_in_kg;
    String product_image;
    String total_price;
    String on_date;

    public String getSale_item_id(){
        return sale_item_id;
    }

    public String getSale_id(){
        return sale_id;
    }

    public String getProduct_id(){
        return product_id;
    }

    public String getProduct_name(){
        return product_name;
    }

    public String getQty(){
        return qty;
    }

    public String getUnit(){
        return unit;
    }

    public String getUnit_value(){
        return unit_value;
    }

    public String getPrice(){
        return price;
    }

    public String getQty_in_kg(){
        return qty_in_kg;
    }

    public String getProduct_image(){
        return product_image;
    }


    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }


    public String getOn_date() {
        return on_date;
    }

    public void setOn_date(String on_date) {
        this.on_date = on_date;
    }
}
