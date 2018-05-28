package com.ats.exhibitionvisitor.model;

public class EventModel {

    private int eventId;
    private int orgId;
    private String orgName;
    private String eventName;
    private String eventFromDate;
    private String eventToDate;
    private String fromTime;
    private String toTime;
    private String eventLocation;
    private String eventLocLat;
    private String eventLocLong;
    private String aboutEvent;
    private String eventLogo;
    private String contactPersonName1;
    private String contactPersonName2;
    private String person1Mob;
    private String person2Mob;
    private String person1EmailId;
    private String person2EmailId;
    private int isUsed;
    private int locationId;
    private int companyTypeId;
    private String locationName;
    private String companyTypeName;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventFromDate() {
        return eventFromDate;
    }

    public void setEventFromDate(String eventFromDate) {
        this.eventFromDate = eventFromDate;
    }

    public String getEventToDate() {
        return eventToDate;
    }

    public void setEventToDate(String eventToDate) {
        this.eventToDate = eventToDate;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventLocLat() {
        return eventLocLat;
    }

    public void setEventLocLat(String eventLocLat) {
        this.eventLocLat = eventLocLat;
    }

    public String getEventLocLong() {
        return eventLocLong;
    }

    public void setEventLocLong(String eventLocLong) {
        this.eventLocLong = eventLocLong;
    }

    public String getAboutEvent() {
        return aboutEvent;
    }

    public void setAboutEvent(String aboutEvent) {
        this.aboutEvent = aboutEvent;
    }

    public String getEventLogo() {
        return eventLogo;
    }

    public void setEventLogo(String eventLogo) {
        this.eventLogo = eventLogo;
    }

    public String getContactPersonName1() {
        return contactPersonName1;
    }

    public void setContactPersonName1(String contactPersonName1) {
        this.contactPersonName1 = contactPersonName1;
    }

    public String getContactPersonName2() {
        return contactPersonName2;
    }

    public void setContactPersonName2(String contactPersonName2) {
        this.contactPersonName2 = contactPersonName2;
    }

    public String getPerson1Mob() {
        return person1Mob;
    }

    public void setPerson1Mob(String person1Mob) {
        this.person1Mob = person1Mob;
    }

    public String getPerson2Mob() {
        return person2Mob;
    }

    public void setPerson2Mob(String person2Mob) {
        this.person2Mob = person2Mob;
    }

    public String getPerson1EmailId() {
        return person1EmailId;
    }

    public void setPerson1EmailId(String person1EmailId) {
        this.person1EmailId = person1EmailId;
    }

    public String getPerson2EmailId() {
        return person2EmailId;
    }

    public void setPerson2EmailId(String person2EmailId) {
        this.person2EmailId = person2EmailId;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getCompanyTypeId() {
        return companyTypeId;
    }

    public void setCompanyTypeId(int companyTypeId) {
        this.companyTypeId = companyTypeId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCompanyTypeName() {
        return companyTypeName;
    }

    public void setCompanyTypeName(String companyTypeName) {
        this.companyTypeName = companyTypeName;
    }

    @Override
    public String toString() {
        return "EventModel{" +
                "eventId=" + eventId +
                ", orgId=" + orgId +
                ", orgName='" + orgName + '\'' +
                ", eventName='" + eventName + '\'' +
                ", eventFromDate=" + eventFromDate +
                ", eventToDate=" + eventToDate +
                ", fromTime='" + fromTime + '\'' +
                ", toTime='" + toTime + '\'' +
                ", eventLocation='" + eventLocation + '\'' +
                ", eventLocLat='" + eventLocLat + '\'' +
                ", eventLocLong='" + eventLocLong + '\'' +
                ", aboutEvent='" + aboutEvent + '\'' +
                ", eventLogo='" + eventLogo + '\'' +
                ", contactPersonName1='" + contactPersonName1 + '\'' +
                ", contactPersonName2='" + contactPersonName2 + '\'' +
                ", person1Mob='" + person1Mob + '\'' +
                ", person2Mob='" + person2Mob + '\'' +
                ", person1EmailId='" + person1EmailId + '\'' +
                ", person2EmailId='" + person2EmailId + '\'' +
                ", isUsed=" + isUsed +
                ", locationId=" + locationId +
                ", companyTypeId=" + companyTypeId +
                ", locationName='" + locationName + '\'' +
                ", companyTypeName='" + companyTypeName + '\'' +
                '}';
    }
}
