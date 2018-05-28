package com.ats.exhibitionvisitor.model;

public class VisitorProduct {

    private int visitorProductId;
    private int visitorId;
    private int eventId;
    private int exhibitorId;
    private int productId;
    private int likeStatus;

    public int getVisitorProductId() {
        return visitorProductId;
    }

    public void setVisitorProductId(int visitorProductId) {
        this.visitorProductId = visitorProductId;
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(int likeStatus) {
        this.likeStatus = likeStatus;
    }

    @Override
    public String toString() {
        return "VisitorProduct{" +
                "visitorProductId=" + visitorProductId +
                ", visitorId=" + visitorId +
                ", eventId=" + eventId +
                ", exhibitorId=" + exhibitorId +
                ", productId=" + productId +
                ", likeStatus=" + likeStatus +
                '}';
    }
}
