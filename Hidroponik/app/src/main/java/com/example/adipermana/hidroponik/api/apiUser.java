package com.example.adipermana.hidroponik.api;



import com.example.adipermana.hidroponik.Model.userModel;
import com.example.adipermana.hidroponik.Model.userResponModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;


public interface apiUser {

    @POST("authenticate")
    Observable<userResponModel> login();
    @POST("users")
    Observable<userResponModel> register(@Body userModel user);

    @POST("insert.php")
    Call<userResponModel> sendUser(@Field("nama") String nama,
                                      @Field("usia") String usia,
                                      @Field("domisili") String domisili);

    @GET("read.php")
    Call<userResponModel> getUser();

    @FormUrlEncoded
    @POST("update.php")
    Call<userResponModel> updateUser(@Field("id") String id,
                                  @Field("nama") String nama,
                                  @Field("usia") String usia,
                                  @Field("domisili") String domisili);

    @FormUrlEncoded
    @POST("delete.php")
    Call<userResponModel> deleteUser(@Field("id") String id);
}
