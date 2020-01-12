package com.test.study;

import com.google.gson.annotations.SerializedName;

public class PaymentResponse {
    @SerializedName("status")
    private Integer status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Integer data;

    public PaymentResponse(Integer sta, String msg, Integer id){
        this.status = sta;
        this.message = msg;
        this.data = id;
    }

}
