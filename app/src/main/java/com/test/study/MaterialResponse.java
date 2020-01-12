package com.test.study;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MaterialResponse {
    @SerializedName("status")
    private Integer status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<StudyModel> data;

    @SerializedName("total")
    private String total;

    public MaterialResponse(Integer sta,String msg,List<StudyModel> list,String totl){
        this.status = sta;
        this.message = msg;
        this.data = list;
        this.total = totl;
    }

    public Integer getError() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    List<StudyModel> getData() {
        return data;
    }
}
