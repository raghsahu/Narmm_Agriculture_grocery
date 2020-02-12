package com.drraisingh.narmm.Model.CategoryWithCompanyModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Raghvendra Sahu on 29-Dec-19.
 */
public class CategoryCompanyModelData {
    @SerializedName("category")
    @Expose
    private CategoryData category;
    @SerializedName("company")
    @Expose
    private List<CompanyData> company = null;

    public CategoryData getCategory() {
        return category;
    }

    public void setCategory(CategoryData category) {
        this.category = category;
    }

    public List<CompanyData> getCompany() {
        return company;
    }

    public void setCompany(List<CompanyData> company) {
        this.company = company;
    }

}
