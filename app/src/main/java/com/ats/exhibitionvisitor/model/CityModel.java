package com.ats.exhibitionvisitor.model;

public class CityModel {

    private Integer locationId;
    private String locationName;
    private String locationLat;
    private String locationLong;
    private String remark;
    private Integer isUsed;
    private boolean checkedStatus;

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationLong() {
        return locationLong;
    }

    public void setLocationLong(String locationLong) {
        this.locationLong = locationLong;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "CityModel{" +
                "locationId=" + locationId +
                ", locationName='" + locationName + '\'' +
                ", locationLat='" + locationLat + '\'' +
                ", locationLong='" + locationLong + '\'' +
                ", remark='" + remark + '\'' +
                ", isUsed=" + isUsed +
                ", checkedStatus=" + checkedStatus +
                '}';
    }
}
