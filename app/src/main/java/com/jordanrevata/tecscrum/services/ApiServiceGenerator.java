package com.jordanrevata.tecscrum.services;

import android.util.Log;

import com.jordanrevata.tecscrum.repositories.TeamRepository;
import com.jordanrevata.tecscrum.repositories.UserRepository;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceGenerator {

    private final static String TAG = ApiServiceGenerator.class.getSimpleName();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ApiService.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit;

    private ApiServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass) {
        if(retrofit == null) {

            // Retrofit Token: https://futurestud.io/tutorials/retrofit-token-authentication-on-android
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {

                    try {

                        Request originalRequest = chain.request();


                        String token = null;

                        if(!UserRepository.verifyLogeo())
                            token = UserRepository.getUser().getToken();

                        Log.d(TAG, "Loaded Token: " + token);

                        if(token == null){
                            // Firsttime assuming login
                            return chain.proceed(originalRequest);
                        }

                        // Request customization: add request headers
                        Request modifiedRequest = originalRequest.newBuilder()
                                .header("Authorization", token)
                                .build();

                        return chain.proceed(modifiedRequest); // Call request with token


                    }catch (Exception e){
                        Log.e(TAG, e.toString());
                        throw e;
                    }

                }
            });

            retrofit = builder.client(httpClient.build()).build();
        }
        return retrofit.create(serviceClass);
    }



    
}
