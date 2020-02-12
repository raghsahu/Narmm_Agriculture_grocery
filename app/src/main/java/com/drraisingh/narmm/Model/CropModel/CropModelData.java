package com.drraisingh.narmm.Model.CropModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 27-Dec-19.
 */
public class CropModelData {


    @SerializedName("m_id")
    @Expose
    private String mId;
    @SerializedName("m_key")
    @Expose
    private String mKey;
    @SerializedName("m_value")
    @Expose
    private String mValue;

    public String getMId() {
        return mId;
    }

    public void setMId(String mId) {
        this.mId = mId;
    }

    public String getMKey() {
        return mKey;
    }

    public void setMKey(String mKey) {
        this.mKey = mKey;
    }

    public String getMValue() {
        return mValue;
    }

    public void setMValue(String mValue) {
        this.mValue = mValue;
    }
}
