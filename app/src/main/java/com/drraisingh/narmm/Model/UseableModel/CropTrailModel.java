package com.drraisingh.narmm.Model.UseableModel;

/**
 * Created by Raghvendra Sahu on 05-Jan-20.
 */
public class CropTrailModel {
    String id;
    String crop_name;
    String pdf;

    public CropTrailModel(String id, String crop_name, String pdf) {
        this.id = id;
        this.crop_name = crop_name;
        this.pdf = pdf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCrop_name() {
        return crop_name;
    }

    public void setCrop_name(String crop_name) {
        this.crop_name = crop_name;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
}
