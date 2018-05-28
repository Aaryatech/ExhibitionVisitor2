package com.ats.exhibitionvisitor.model;

public class MaterialModel {

    private int trId;
    private int exhId;
    private String exhName;
    private String matName;
    private String matLink;
    private int isUsed;

    public int getTrId() {
        return trId;
    }

    public void setTrId(int trId) {
        this.trId = trId;
    }

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

    public String getMatName() {
        return matName;
    }

    public void setMatName(String matName) {
        this.matName = matName;
    }

    public String getMatLink() {
        return matLink;
    }

    public void setMatLink(String matLink) {
        this.matLink = matLink;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    @Override
    public String toString() {
        return "MaterialModel{" +
                "trId=" + trId +
                ", exhId=" + exhId +
                ", exhName='" + exhName + '\'' +
                ", matName='" + matName + '\'' +
                ", matLink='" + matLink + '\'' +
                ", isUsed=" + isUsed +
                '}';
    }
}
