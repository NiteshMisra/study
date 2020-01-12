package com.test.study;

import com.google.gson.annotations.SerializedName;

public class StudyModel {

    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("pdf_link")
    private String pdfLink;
    @SerializedName("free_status")
    private String freeStatus;
    @SerializedName("price")
    private String price;
    @SerializedName("purchased_status")
    private String purchasedStatus;

    public StudyModel(String id,String title,String pdfLink,String freeStatus,String price,String purStatus){
        this.id = id;
        this.title = title;
        this.pdfLink = pdfLink;
        this.freeStatus = freeStatus;
        this.price = price;
        this.purchasedStatus = purStatus;
    }

    public String getPurchasedStatus() {
        return purchasedStatus;
    }

    public String getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    String getFreeStatus() {
        return freeStatus;
    }

    String  getPrice() {
        return price;
    }
}
