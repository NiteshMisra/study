package com.test.study;

import java.util.Map;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface Api {

    @FormUrlEncoded
    @POST("post/purchaseapi")
    retrofit2.Call<PaymentResponse> postPurchase(
            @HeaderMap Map<String,String> headers,
            @Field("userid")Integer userId,
            @Field("itemid")Integer itemId,
            @Field("transactionid")String transId,
            @Field("itemtype") Integer itemType
    );


    @GET("get/studymaterialbytype")
    retrofit2.Call<MaterialResponse> getStudyMaterials(
            @HeaderMap Map<String,String> headers,
            @QueryMap Map<String,String> params
            );

}
