package wipbasics.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ID = 99;
    public static final int UPDATE_UI_LAST = 0;
    public static final int UPDATE_UI_CURRENT = 1;
    public static final int UPDATE_UI_UPDATE = 2;
    private FusedLocationProviderClient fusedLocationProviderClient;

    TextView tv1_lat, tv1_lon, tv1_address, tv1_time;
    TextView tv2_lat, tv2_lon, tv2_address, tv2_time;
    TextView tv3_lat, tv3_lon, tv3_address, tv3_time;
    Button btn_last, btn_current, btn_update_start, btn_update_stop;
    boolean isFineLocationGranted = false;

    LocationCallback locationCallback;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitialiseViews(); // Initialise Text and Button Views

        // Get Fused Location Provider Client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // 1. Get Last Location
        btn_last.setOnClickListener(view -> {
            if (isFineLocationGranted) {
                fusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(location -> updateUI(UPDATE_UI_LAST, location));
            } else {
                getLocationPermissions();
            }
        });

        // 2. Get Current Location
        btn_current.setOnClickListener(view -> {
            if (isFineLocationGranted) {
                fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
                        .addOnSuccessListener(location -> updateUI(UPDATE_UI_CURRENT, location));
            } else {
                getLocationPermissions();
            }
        });

        // 3. Start Location Updates
        btn_update_start.setOnClickListener(view -> {
            if (isFineLocationGranted) {
                startLocationUpdate();
                btn_update_start.setEnabled(false);
                btn_update_stop.setEnabled(true);
            } else {
                getLocationPermissions();
            }
        });

        // 4. Stop Location Updates
        btn_update_stop.setOnClickListener(view -> {
            stopLocationUpdate();
            btn_update_start.setEnabled(true);
            btn_update_stop.setEnabled(false);
        });
    }

    @SuppressLint("MissingPermission")
    public void startLocationUpdate() {

        // Location Request
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);         // 10 Seconds
        locationRequest.setFastestInterval(5000);   //  5 Seconds
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Define the callback
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    updateUI(UPDATE_UI_UPDATE, location);
                }
            }
        };

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    public void stopLocationUpdate() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void updateUI(int UItoUpdate, Location location) {
        if (location != null) {
            List<Address> address;
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            try {
                address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                // Update Appropriate UI Element
                if (UItoUpdate == UPDATE_UI_LAST) {
                    tv1_lat.setText("Lat: " + address.get(0).getLatitude());
                    tv1_lon.setText("Lon: " + address.get(0).getLongitude());
                    tv1_address.setText(String.valueOf(address.get(0).getAddressLine(0)));
                    tv1_time.setText("Updated: " + DateFormat.getDateTimeInstance().format(new Date()));
                } else if (UItoUpdate == UPDATE_UI_CURRENT) {
                    tv2_lat.setText("Lat: " + address.get(0).getLatitude());
                    tv2_lon.setText("Lon: " + address.get(0).getLongitude());
                    tv2_address.setText(String.valueOf(address.get(0).getAddressLine(0)));
                    tv2_time.setText("Updated: " + DateFormat.getDateTimeInstance().format(new Date()));
                } else if (UItoUpdate == UPDATE_UI_UPDATE) {
                    tv3_lat.setText("Lat: " + address.get(0).getLatitude());
                    tv3_lon.setText("Lon: " + address.get(0).getLongitude());
                    tv3_address.setText(String.valueOf(address.get(0).getAddressLine(0)));
                    tv3_time.setText("Updated: " + DateFormat.getDateTimeInstance().format(new Date()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public void getLocationPermissions() {
        // Must be over SDK 23 - M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ID);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ID:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    isFineLocationGranted = true;
                else {
                    isFineLocationGranted = false;
                    Toast.makeText(this, "Fine Location - Permission Not Granted", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void InitialiseViews() {
        tv1_lat = findViewById(R.id.tv_1_lat);
        tv1_lon = findViewById(R.id.tv_1_lon);
        tv1_address = findViewById(R.id.tv_1_address);
        tv1_time = findViewById(R.id.tv_1_time);

        tv2_lat = findViewById(R.id.tv_2_lat);
        tv2_lon = findViewById(R.id.tv_2_lon);
        tv2_address = findViewById(R.id.tv_2_address);
        tv2_time = findViewById(R.id.tv_2_time);

        tv3_lat = findViewById(R.id.tv_3_lat);
        tv3_lon = findViewById(R.id.tv_3_lon);
        tv3_address = findViewById(R.id.tv_3_address);
        tv3_time = findViewById(R.id.tv_3_time);

        btn_last = findViewById(R.id.btn_last);
        btn_current = findViewById(R.id.btn_current);
        btn_update_start = findViewById(R.id.btn_update_start);
        btn_update_stop = findViewById(R.id.btn_update_stop);
    }
}