package com.ats.exhibitionvisitor.model;

public class EventSubscribeModel {


    private int visitorEventId;
    private int visitorId;
    private int eventId;
    private int subscribeStatus;

    public int getVisitorEventId() {
        return visitorEventId;
    }

    public void setVisitorEventId(int visitorEventId) {
        this.visitorEventId = visitorEventId;
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

    public int getSubscribeStatus() {
        return subscribeStatus;
    }

    public void setSubscribeStatus(int subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }

    @Override
    public String toString() {
        return "EventSubscribeModel{" +
                "visitorEventId=" + visitorEventId +
                ", visitorId=" + visitorId +
                ", eventId=" + eventId +
                ", subscribeStatus=" + subscribeStatus +
                '}';
    }
}
