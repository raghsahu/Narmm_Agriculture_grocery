package com.drraisingh.narmm.Model.UseableModel;

/**
 * Created by Raghvendra Sahu on 06-Jan-20.
 */
public class ComplainList_model {

    String complaint_id;
    String farmer_id;
    String subject;
    String message;
    String type;
    String responce_msg;
    String created_date;
    String responce_date;

    public String getComplaint_id() {
        return complaint_id;
    }

    public void setComplaint_id(String complaint_id) {
        this.complaint_id = complaint_id;
    }

    public String getFarmer_id() {
        return farmer_id;
    }

    public void setFarmer_id(String farmer_id) {
        this.farmer_id = farmer_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getResponce_date() {
        return responce_date;
    }

    public void setResponce_date(String responce_date) {
        this.responce_date = responce_date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResponce_msg() {
        return responce_msg;
    }

    public void setResponce_msg(String responce_msg) {
        this.responce_msg = responce_msg;
    }
}
