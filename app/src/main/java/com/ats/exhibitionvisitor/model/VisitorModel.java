package com.ats.exhibitionvisitor.model;

public class VisitorModel {

    private Integer visitorId;
    private Integer eventId;
    private Integer orgId;
    private String visitorName;
    private String visitorEmail;
    private String visitorMobile;
    private String visitorRepresent;
    private Integer isActive;
    private Integer isUsed;
    private Integer locationId;
    private Integer companyTypeId;
    private String token;

    public VisitorModel() {
    }

    public VisitorModel(Integer eventId, Integer orgId, String visitorName, String visitorEmail, String visitorMobile, String visitorRepresent, Integer isActive, Integer isUsed, Integer locationId, Integer companyTypeId, String token) {
        this.eventId = eventId;
        this.orgId = orgId;
        this.visitorName = visitorName;
        this.visitorEmail = visitorEmail;
        this.visitorMobile = visitorMobile;
        this.visitorRepresent = visitorRepresent;
        this.isActive = isActive;
        this.isUsed = isUsed;
        this.locationId = locationId;
        this.companyTypeId = companyTypeId;
        this.token = token;
    }

    public Integer getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Integer visitorId) {
        this.visitorId = visitorId;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorEmail() {
        return visitorEmail;
    }

    public void setVisitorEmail(String visitorEmail) {
        this.visitorEmail = visitorEmail;
    }

    public String getVisitorMobile() {
        return visitorMobile;
    }

    public void setVisitorMobile(String visitorMobile) {
        this.visitorMobile = visitorMobile;
    }

    public String getVisitorRepresent() {
        return visitorRepresent;
    }

    public void setVisitorRepresent(String visitorRepresent) {
        this.visitorRepresent = visitorRepresent;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getCompanyTypeId() {
        return companyTypeId;
    }

    public void setCompanyTypeId(Integer companyTypeId) {
        this.companyTypeId = companyTypeId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "VisitorModel{" +
                "visitorId=" + visitorId +
                ", eventId=" + eventId +
                ", orgId=" + orgId +
                ", visitorName='" + visitorName + '\'' +
                ", visitorEmail='" + visitorEmail + '\'' +
                ", visitorMobile='" + visitorMobile + '\'' +
                ", visitorRepresent='" + visitorRepresent + '\'' +
                ", isActive=" + isActive +
                ", isUsed=" + isUsed +
                ", locationId=" + locationId +
                ", companyTypeId=" + companyTypeId +
                ", token='" + token + '\'' +
                '}';
    }
}
