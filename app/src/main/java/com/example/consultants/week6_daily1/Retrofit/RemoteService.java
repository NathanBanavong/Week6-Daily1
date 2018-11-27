package com.example.consultants.week6_daily1.Retrofit;

import com.example.GoogleMapsPlace;
import com.example.consultants.week6_daily1.Retrofit.Repository.GoogleMapsPlace;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RemoteService {

    @GET("/nearbysearch/json?location={lat},{lng}&radius=1000&type=restaurant&key={key}")
    Call<GoogleMapsPlace> getPlaceInfo(@Path("lat") String lat, @Path("lng") String lng, @Path("key") String Key);

}
