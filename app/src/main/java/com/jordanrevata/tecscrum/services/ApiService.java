package com.jordanrevata.tecscrum.services;

import com.jordanrevata.tecscrum.models.Credential;
import com.jordanrevata.tecscrum.models.Daily;
import com.jordanrevata.tecscrum.models.Forum;
import com.jordanrevata.tecscrum.models.Login;
import com.jordanrevata.tecscrum.models.Project;
import com.jordanrevata.tecscrum.models.ResponseMessage;
import com.jordanrevata.tecscrum.models.Sprint;
import com.jordanrevata.tecscrum.models.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {


    String API_BASE_URL = "https://agile-projects-jrevata.c9users.io";

    @POST("/api/login")
    Call<Login> login(@Body Credential credential);

    @GET("/api/users/getProjectsByUser/{id}")
    Call<List<Project>> getProjectsByUser(@Path("id") int id);

    @POST("/api/logout")
    Call<Void> logout();

    @GET("/api/sprints/getAllbyProject/{id}")
    Call<List<Sprint>> getSprintsByProject(@Path("id") int id);

    @GET("/api/projects/getUsersByProject/{id}")
    Call<List<User>> getUsersByProject(@Path("id") int id);

    @GET("api/comments/getCommentsBySprint/{id}")
    Call<List<Forum>> getCommentsBySprint(@Path("id") int id);

    @POST("api/comments/store")
    Call<ResponseMessage> createComment(@Body Forum forum);

    @FormUrlEncoded
    @POST("api/users/update/{id}")
    Call<ResponseMessage> updateUserWithoutImage(@Path("id") int id,
                                     @Field("givenName") String givenName,
                                     @Field("familyName") String familyName,
                                     @Field("phone") String phone);

    @Multipart
    @POST("api/users/update/{id}")
    Call<ResponseMessage> updateUserWithImage(@Path("id") int id,
                                              @Part("givenName") RequestBody givenName,
                                              @Part("familyName") RequestBody familyName,
                                              @Part("phone") RequestBody phone,
                                              @Part MultipartBody.Part image);

    @GET("api/dailies/listDailies/{idsprint}/{iduser}")
    Call<List<Daily>> getDailies(@Path("idsprint") int idsprint,
                           @Path("iduser") int iduser);


}
