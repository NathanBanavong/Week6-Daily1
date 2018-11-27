package com.example.consultants.week6_daily1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.consultants.week6_daily1.Retrofit.RemoteDataSource;
import com.example.consultants.week6_daily1.Retrofit.Repository.GoogleMapsPlace;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = MainActivity.class.getSimpleName() + "_TAG";

    Constants constants;
    RemoteDataSource remoteDataSource;
    private Location location;
    private LocationManager locationManager;
    private TextView tvLocation;
    private GoogleApiClient googleApiClient;
    //    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final int PERMISSION_REQUEST_LOCATION = 0;
    public static final int REQUEST_MULTIPLE_PERMISSIONS = 666;
    private RecyclerView rvRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onBindView();

    }

    public void onBindView() {
        rvRestaurants = findViewById(R.id.rvRestaurants);
    }

    public void onDisplayLocation(View view) {
        RequestLocation();

        ShowLocation(view);
    }

    @SuppressLint("MissingPermission")
    public void ShowLocation(View view) {
        List<RestaurantInfo> restaurantInfoList = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Double longitude = location.getLongitude();
            Double latitude = location.getLatitude();
            String longit = Double.toString(longitude);
            String lat = Double.toString(latitude);
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            remoteDataSource.getPlaceInfo(longit, lat, constants.KEY);

            FragmentManager fm = getSupportFragmentManager();

            RestaurantListAdapter contactAdapter = new RestaurantListAdapter(restaurantInfoList, getApplicationContext(), fm);
            rvRestaurants.setLayoutManager(new LinearLayoutManager(this));
            rvRestaurants.setAdapter(contactAdapter);
        }
    }

    //TODO Fix the intent to not pass
//    public void startRestaurants() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            //TODO change to display on
//            //sending intent to display restaurants near location
//            Intent intent = new Intent(this, DisplayRestaurants.class);
//            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            Double longitude = location.getLongitude();
//            Double latitude = location.getLatitude();
//            String longit = Double.toString(longitude);
//            String lat = Double.toString(latitude);
//            intent.putExtra("long", longit);
//            intent.putExtra("lat", lat);
//            startActivity(intent);
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull final String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            // Request for location permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start preview Activity.

                Toast.makeText(this, "Location Granted",
                        Toast.LENGTH_SHORT)
                        .show();
                //go to start Restaurant function
                //onShowLocation();
            } else {
                // Permission request was denied.
                Toast.makeText(this, "Location Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void RequestLocation() {
        Log.d(TAG, "RequestLocation: ");
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("Location");
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //TODO Below is original
//                                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
//                                        REQUEST_MULTIPLE_PERMISSIONS);
                                requestPermissions(permissionsList.toArray(new String[0]),
                                        REQUEST_MULTIPLE_PERMISSIONS);
                            }

                        });
                return;
            }
            //TODO added this to allow information to be clicked and displayed
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toArray(new String[0]),
                        REQUEST_MULTIPLE_PERMISSIONS);
            }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            if (!shouldShowRequestPermissionRationale(permission))
                return !shouldShowRequestPermissionRationale(permission);
//                return false;
        }
        //originally returned true
        return false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


}
