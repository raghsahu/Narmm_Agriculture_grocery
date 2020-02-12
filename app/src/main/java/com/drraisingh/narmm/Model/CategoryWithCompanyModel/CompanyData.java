package com.drraisingh.narmm.Model.CategoryWithCompanyModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Raghvendra Sahu on 29-Dec-19.
 */
public class CompanyData {
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("company_name")
    @Expose
    private String companyName;

    private String cate_id;


    public CompanyData(String companyId, String companyName, String cate_id) {
        this.companyId=companyId;
        this.companyName=companyName;
        this.cate_id=cate_id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }
}
