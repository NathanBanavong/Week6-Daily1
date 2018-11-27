package com.example.consultants.week6_daily1.Retrofit;

import android.util.Log;

import com.example.consultants.week6_daily1.Retrofit.Repository.GoogleMapsPlace;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSource {
    public static final String TAG = RemoteDataSource.class.getSimpleName() + "_TAG";

    private Retrofit createInstance(){
        Log.d(TAG, "createInstance: " + this.toString());

        return new Retrofit.Builder()
                .baseUrl(NetworkHelper.BASE_URL)
//                use for converting the response using Gson
                .addConverterFactory(GsonConverterFactory.create())
                //using rxjava adapter
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private  RemoteService getRemoteService() {
        Log.d(TAG, "getRemoteService: " + Thread.currentThread().getName());
        return createInstance().create(RemoteService.class);
    }

    public Call<GoogleMapsPlace> getPlaceInfo(String lat, String lng, String key) {
        Log.d(TAG, "getPlaceInfo: " + lat + ", " + lng);
        Log.d(TAG, "getUserProfile: " + Thread.currentThread().getName());
        Retrofit retrofit = createInstance();
        RemoteService service = retrofit.create(RemoteService.class);

        //TODO test
        return service.getPlaceInfo(lat, lng, key);
//        return getRemoteService().getUserProfile(login);
    }
}
