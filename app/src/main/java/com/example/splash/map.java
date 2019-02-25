package com.example.splash;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class map extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleApiClient.ConnectionCallbacks {    //private LocationRequest mLocationRequest;
    // flag for GPS status
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    boolean isGPSEnabled = false;
    private FusedLocationProviderClient mFusedLocationClient;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    // flag for network status
    boolean isNetworkEnabled = false;
    // flag for GPS status
    boolean canGetLocation = false;
    private GoogleMap Mmap;
    Button btn;
    Location location, mLastLocation;
    // LocationRequest mLocationRequest;
    TextView latLongTV;
    Context context;
    //LocationRequest locationRequest;
    double latitude; // latitude
    double longitude; // longitude
    LocationManager locationManager;
    EditText txt;
    Marker mCurrLocationMarker;
    int LOCATION_REFRESH_TIME = 10;
    int LOCATION_REFRESH_DISTANCE = 100;
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        setContentView(R.layout.activity_maps);
        btn = (Button) findViewById(R.id.btn);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // context=getApplicationContext();
        mapFragment.getMapAsync(this);
        txt = (EditText) findViewById(R.id.txt);
        latLongTV = (TextView) findViewById(R.id.latLongTV);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap mMap) {
        Mmap = mMap;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                EditText editText = (EditText) findViewById(R.id.txt);
                String address = editText.getText().toString();
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    Context mContext = getApplicationContext();
                    locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

                    // getting GPS status
                    isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                    // getting network status
                    isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                    if(address!=null)
                        System.out.print("");
                    List addressList = geocoder.getFromLocationName(address, 1);
                    Address address1 = (Address) addressList.get(0);
                    LatLng loc = new LatLng(address1.getLatitude(), address1.getLongitude());
                    Mmap.addMarker(new MarkerOptions().position(loc).title("" + txt.getText()));
                    Mmap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return ;
                    }
                    Mmap.setMyLocationEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onLocationChanged(Location location) {
        latLongTV = (TextView) findViewById(R.id.latLongTV);
        latLongTV.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }


    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }
    @Override
    public void onProviderEnabled(String s) {
    }
    @Override
    public void onProviderDisabled(String s) {
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    void asdf()
    {
        gps = new GPSTracker(map.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }
    }
}