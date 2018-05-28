package com.ats.exhibitionvisitor.model;

public class ProductByLikedStatus {

    private int prodId;
    private int exhId;
    private String prodName;
    private String prodDesc;
    private String prodSpecification;
    private String prodExperty;
    private String prodImage1;
    private String prodImage2;
    private String prodImage3;
    private int isUsed;

    private int likeStatus;


    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public int getExhId() {
        return exhId;
    }

    public void setExhId(int exhId) {
        this.exhId = exhId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public String getProdSpecification() {
        return prodSpecification;
    }

    public void setProdSpecification(String prodSpecification) {
        this.prodSpecification = prodSpecification;
    }

    public String getProdExperty() {
        return prodExperty;
    }

    public void setProdExperty(String prodExperty) {
        this.prodExperty = prodExperty;
    }

    public String getProdImage1() {
        return prodImage1;
    }

    public void setProdImage1(String prodImage1) {
        this.prodImage1 = prodImage1;
    }

    public String getProdImage2() {
        return prodImage2;
    }

    public void setProdImage2(String prodImage2) {
        this.prodImage2 = prodImage2;
    }

    public String getProdImage3() {
        return prodImage3;
    }

    public void setProdImage3(String prodImage3) {
        this.prodImage3 = prodImage3;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public int getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(int likeStatus) {
        this.likeStatus = likeStatus;
    }

    @Override
    public String toString() {
        return "ProductByLikedStatus{" +
                "prodId=" + prodId +
                ", exhId=" + exhId +
                ", prodName='" + prodName + '\'' +
                ", prodDesc='" + prodDesc + '\'' +
                ", prodSpecification='" + prodSpecification + '\'' +
                ", prodExperty='" + prodExperty + '\'' +
                ", prodImage1='" + prodImage1 + '\'' +
                ", prodImage2='" + prodImage2 + '\'' +
                ", prodImage3='" + prodImage3 + '\'' +
                ", isUsed=" + isUsed +
                ", likeStatus=" + likeStatus +
                '}';
    }
}
