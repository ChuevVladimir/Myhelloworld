package com.chuev.myhellowworld.remote;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthAPI {
    @GET("./auth")
    Single<AuthResponse> makelogin(@Query("social_user_id") String socialuserid);

}
