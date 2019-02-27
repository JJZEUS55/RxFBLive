package com.rxjava2.android.samples.retrofit;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

interface InterfaceEndPoints {

    @GET("login/authentication")
    @Headers({"Content-Type: application/json"})
    Call<ObjectUser> getToken(@Body ObjectUser user);

}
