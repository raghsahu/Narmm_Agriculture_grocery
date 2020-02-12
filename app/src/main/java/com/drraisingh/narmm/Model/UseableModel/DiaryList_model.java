package com.drraisingh.narmm.Model.UseableModel;

/**
 * Created by Raghvendra Sahu on 09-Jan-20.
 */
public class DiaryList_model {
    String f_d_id;
    String farmer_id;
    String created_date;
    String content;
    String updated_date;

    public String getF_d_id() {
        return f_d_id;
    }

    public void setF_d_id(String f_d_id) {
        this.f_d_id = f_d_id;
    }

    public String getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(String farmer_id) {
        this.farmer_id = farmer_id;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }
}
