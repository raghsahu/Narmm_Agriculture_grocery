package com.drraisingh.narmm.Model.Profile_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 18-Jan-20.
 */
public class UserProfileModelData {

    @SerializedName("farmer_id")
    @Expose
    private String farmerId;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("tehsil")
    @Expose
    private String tehsil;
    @SerializedName("block")
    @Expose
    private String block;
    @SerializedName("village")
    @Expose
    private String village;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("aadhar_no")
    @Expose
    private String aadharNo;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("crop_first")
    @Expose
    private String cropFirst;
    @SerializedName("crop_second")
    @Expose
    private String cropSecond;
    @SerializedName("crop_third")
    @Expose
    private String cropThird;
    @SerializedName("crop_other")
    @Expose
    private String cropOther;
    @SerializedName("crop_first_qty")
    @Expose
    private String cropFirstQty;
    @SerializedName("crop_second_qty")
    @Expose
    private String cropSecondQty;
    @SerializedName("crop_third_qty")
    @Expose
    private String cropThirdQty;
    @SerializedName("crop_other_qty")
    @Expose
    private String cropOtherQty;
    @SerializedName("total_acres")
    @Expose
    private String totalAcres;
    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getCropFirst() {
        return cropFirst;
    }

    public void setCropFirst(String cropFirst) {
        this.cropFirst = cropFirst;
    }

    public String getCropSecond() {
        return cropSecond;
    }

    public void setCropSecond(String cropSecond) {
        this.cropSecond = cropSecond;
    }

    public String getCropThird() {
        return cropThird;
    }

    public void setCropThird(String cropThird) {
        this.cropThird = cropThird;
    }

    public String getCropOther() {
        return cropOther;
    }

    public void setCropOther(String cropOther) {
        this.cropOther = cropOther;
    }

    public String getCropFirstQty() {
        return cropFirstQty;
    }

    public void setCropFirstQty(String cropFirstQty) {
        this.cropFirstQty = cropFirstQty;
    }

    public String getCropSecondQty() {
        return cropSecondQty;
    }

    public void setCropSecondQty(String cropSecondQty) {
        this.cropSecondQty = cropSecondQty;
    }

    public String getCropThirdQty() {
        return cropThirdQty;
    }

    public void setCropThirdQty(String cropThirdQty) {
        this.cropThirdQty = cropThirdQty;
    }

    public String getCropOtherQty() {
        return cropOtherQty;
    }

    public void setCropOtherQty(String cropOtherQty) {
        this.cropOtherQty = cropOtherQty;
    }

    public String getTotalAcres() {
        return totalAcres;
    }

    public void setTotalAcres(String totalAcres) {
        this.totalAcres = totalAcres;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
