package com.chuev.myhellowworld.remote;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MoneyAPI {
    @GET("./items")
    Single<List<MoneyRemoteItem>> getmoneyitems (@Query("type") String type, @Query("auth-token") String authToken);
    @POST("./items/add")
    @FormUrlEncoded
    Completable postMoney (@Field("price") int price, @Field("name") String name, @Field("type") String type, @Query("auth-token") String authToken);

    @POST("./items/remove")

    Completable removeItem (@Query("id") String id, @Query("auth-token") String authToken);
}
