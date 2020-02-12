package com.drraisingh.narmm.Model.Profile_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 27-Dec-19.
 */
public class CompanyProfileModelData {

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("farmer_form_image")
    @Expose
    private String farmerFormImage;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFarmerFormImage() {
        return farmerFormImage;
    }

    public void setFarmerFormImage(String farmerFormImage) {
        this.farmerFormImage = farmerFormImage;
    }

}
