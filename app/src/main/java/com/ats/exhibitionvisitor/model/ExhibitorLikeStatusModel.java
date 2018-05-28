package com.ats.exhibitionvisitor.model;

public class ExhibitorLikeStatusModel {

    private int visitorExhibitorId;
    private int visitorId;
    private int eventId;
    private int exhibitorId;
    private int likeStatus;

    public int getVisitorExhibitorId() {
        return visitorExhibitorId;
    }

    public void setVisitorExhibitorId(int visitorExhibitorId) {
        this.visitorExhibitorId = visitorExhibitorId;
    }

    public int getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(int visitorId) {
        this.visitorId = visitorId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getExhibitorId() {
        return exhibitorId;
    }

    public void setExhibitorId(int exhibitorId) {
        this.exhibitorId = exhibitorId;
    }

    public int getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(int likeStatus) {
        this.likeStatus = likeStatus;
    }

    @Override
    public String toString() {
        return "ExhibitorLikeStatusModel{" +
                "visitorExhibitorId=" + visitorExhibitorId +
                ", visitorId=" + visitorId +
                ", eventId=" + eventId +
                ", exhibitorId=" + exhibitorId +
                ", likeStatus=" + likeStatus +
                '}';
    }
}
