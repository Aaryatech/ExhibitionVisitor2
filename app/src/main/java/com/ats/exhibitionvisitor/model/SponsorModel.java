package com.ats.exhibitionvisitor.model;

public class SponsorModel {

    private int sponsorId;
    private int eventId;
    private String name;
    private int companyId;
    private String designation;
    private String photo;
    private String email;
    private String website;
    private String mobile;
    private String remark;
    private int isUsed;
    private String eventName;
    private String companyTypeName;

    public int getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(int sponsorId) {
        this.sponsorId = sponsorId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCompanyTypeName() {
        return companyTypeName;
    }

    public void setCompanyTypeName(String companyTypeName) {
        this.companyTypeName = companyTypeName;
    }

    @Override
    public String toString() {
        return "SponsorModel{" +
                "sponsorId=" + sponsorId +
                ", eventId=" + eventId +
                ", name='" + name + '\'' +
                ", companyId=" + companyId +
                ", designation='" + designation + '\'' +
                ", photo='" + photo + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                ", mobile='" + mobile + '\'' +
                ", remark='" + remark + '\'' +
                ", isUsed=" + isUsed +
                ", eventName='" + eventName + '\'' +
                ", companyTypeName='" + companyTypeName + '\'' +
                '}';
    }
}
