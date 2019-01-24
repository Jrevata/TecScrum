package com.jordanrevata.tecscrum.services;

import com.jordanrevata.tecscrum.models.Credential;
import com.jordanrevata.tecscrum.models.Login;
import com.jordanrevata.tecscrum.models.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {


    String API_BASE_URL = "https://agile-projects-jrevata.c9users.io";

    @POST("/api/login")
    Call<Login> login(@Body Credential credential);


    @GET("/api/users/getProjectsByUser/{id}")
    Call<List<Project>> getProjectsByUser(@Path("id") int id);

    @POST("/api/logout")
    Call<Void> logout();


}
