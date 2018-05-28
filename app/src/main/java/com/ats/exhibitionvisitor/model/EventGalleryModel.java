package com.ats.exhibitionvisitor.model;

public class EventGalleryModel {

    private int photoId;
    private int eventId;
    private String eventName;
    private String photoLink;
    private int isUsed;

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    @Override
    public String toString() {
        return "EventGalleryModel{" +
                "photoId=" + photoId +
                ", eventId=" + eventId +
                ", eventName='" + eventName + '\'' +
                ", photoLink='" + photoLink + '\'' +
                ", isUsed=" + isUsed +
                '}';
    }
}
