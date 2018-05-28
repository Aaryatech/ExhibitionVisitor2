package com.ats.exhibitionvisitor.model;

public class CompanyTypeModel {

    private Integer companyTypeId;
    private String companyTypeName;
    private String companyTypeDesc;
    private Integer isUsed;
    private boolean checkedStatus;

    public Integer getCompanyTypeId() {
        return companyTypeId;
    }

    public void setCompanyTypeId(Integer companyTypeId) {
        this.companyTypeId = companyTypeId;
    }

    public String getCompanyTypeName() {
        return companyTypeName;
    }

    public void setCompanyTypeName(String companyTypeName) {
        this.companyTypeName = companyTypeName;
    }

    public String getCompanyTypeDesc() {
        return companyTypeDesc;
    }

    public void setCompanyTypeDesc(String companyTypeDesc) {
        this.companyTypeDesc = companyTypeDesc;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public boolean isCheckedStatus() {
        return checkedStatus;
    }

    public void setCheckedStatus(boolean checkedStatus) {
        this.checkedStatus = checkedStatus;
    }

    @Override
    public String toString() {
        return "CompanyTypeModel{" +
                "companyTypeId=" + companyTypeId +
                ", companyTypeName='" + companyTypeName + '\'' +
                ", companyTypeDesc='" + companyTypeDesc + '\'' +
                ", isUsed=" + isUsed +
                ", checkedStatus=" + checkedStatus +
                '}';
    }
}
