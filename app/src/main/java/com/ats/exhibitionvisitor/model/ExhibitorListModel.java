package com.ats.exhibitionvisitor.model;

public class ExhibitorListModel {

    private int exhId;
    private String exhName;
    private String eventName;
    private int likeStatus;

    public int getExhId() {
        return exhId;
    }

    public void setExhId(int exhId) {
        this.exhId = exhId;
    }

    public String getExhName() {
        return exhName;
    }

    public void setExhName(String exhName) {
        this.exhName = exhName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(int likeStatus) {
        this.likeStatus = likeStatus;
    }

    @Override
    public String toString() {
        return "ExhibitorListModel{" +
                "exhId=" + exhId +
                ", exhName='" + exhName + '\'' +
                ", eventName='" + eventName + '\'' +
                ", likeStatus=" + likeStatus +
                '}';
    }
}
