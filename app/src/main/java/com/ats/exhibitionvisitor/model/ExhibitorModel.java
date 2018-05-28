package com.ats.exhibitionvisitor.model;

public class ExhibitorModel {

    private int exhId;
    private int orgId;
    private String orgName;
    private String exhName;
    private String exhCompany;
    private String logo;
    private String contactPersonName1;
    private String contactPersonName2;
    private String personMob1;
    private String personMob2;
    private String personEmail1;
    private String personEmail2;
    private String address;
    private String compLat;
    private String compLong;
    private String userMob;
    private String password;
    private String aboutCompany;
    private int companyType;
    private int isUsed;
    private int locationId;
    private int companyTypeId;
    private String locationName;
    private String companyTypeName;

    public int getExhId() {
        return exhId;
    }

    public void setExhId(int exhId) {
        this.exhId = exhId;
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

    public String getExhName() {
        return exhName;
    }

    public void setExhName(String exhName) {
        this.exhName = exhName;
    }

    public String getExhCompany() {
        return exhCompany;
    }

    public void setExhCompany(String exhCompany) {
        this.exhCompany = exhCompany;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getPersonMob1() {
        return personMob1;
    }

    public void setPersonMob1(String personMob1) {
        this.personMob1 = personMob1;
    }

    public String getPersonMob2() {
        return personMob2;
    }

    public void setPersonMob2(String personMob2) {
        this.personMob2 = personMob2;
    }

    public String getPersonEmail1() {
        return personEmail1;
    }

    public void setPersonEmail1(String personEmail1) {
        this.personEmail1 = personEmail1;
    }

    public String getPersonEmail2() {
        return personEmail2;
    }

    public void setPersonEmail2(String personEmail2) {
        this.personEmail2 = personEmail2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompLat() {
        return compLat;
    }

    public void setCompLat(String compLat) {
        this.compLat = compLat;
    }

    public String getCompLong() {
        return compLong;
    }

    public void setCompLong(String compLong) {
        this.compLong = compLong;
    }

    public String getUserMob() {
        return userMob;
    }

    public void setUserMob(String userMob) {
        this.userMob = userMob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAboutCompany() {
        return aboutCompany;
    }

    public void setAboutCompany(String aboutCompany) {
        this.aboutCompany = aboutCompany;
    }

    public int getCompanyType() {
        return companyType;
    }

    public void setCompanyType(int companyType) {
        this.companyType = companyType;
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
        return "ExhibitorModel{" +
                "exhId=" + exhId +
                ", orgId=" + orgId +
                ", orgName='" + orgName + '\'' +
                ", exhName='" + exhName + '\'' +
                ", exhCompany='" + exhCompany + '\'' +
                ", logo='" + logo + '\'' +
                ", contactPersonName1='" + contactPersonName1 + '\'' +
                ", contactPersonName2='" + contactPersonName2 + '\'' +
                ", personMob1='" + personMob1 + '\'' +
                ", personMob2='" + personMob2 + '\'' +
                ", personEmail1='" + personEmail1 + '\'' +
                ", personEmail2='" + personEmail2 + '\'' +
                ", address='" + address + '\'' +
                ", compLat='" + compLat + '\'' +
                ", compLong='" + compLong + '\'' +
                ", userMob='" + userMob + '\'' +
                ", password='" + password + '\'' +
                ", aboutCompany='" + aboutCompany + '\'' +
                ", companyType=" + companyType +
                ", isUsed=" + isUsed +
                ", locationId=" + locationId +
                ", companyTypeId=" + companyTypeId +
                ", locationName='" + locationName + '\'' +
                ", companyTypeName='" + companyTypeName + '\'' +
                '}';
    }
}
