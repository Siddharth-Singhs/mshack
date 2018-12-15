package com.example.android.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;

import com.example.android.myapplication.LoginWork.SignInActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mmi.util.GeoPoint;
import com.nabinbhandari.android.permissions.PermissionHandler;

import java.security.Permissions;
import java.util.ArrayList;

public class SafeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private ProgressDialog progressDialog;
    private LocationManager locationManager;
    private String provider;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private  String latitude;
    private  String longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe);
        mAuth = FirebaseAuth.getInstance();
        //Check whether user is already login in or not
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        Button Yes = (Button) findViewById(R.id.btn_yes);
        Button No = (Button) findViewById(R.id.btn_no);
        progressDialog = new ProgressDialog(this);
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        callPermission();
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setZone("Yes");
                maps("Yes");
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setZone("No");
                maps("No");
            }
        });

    }
    public void setZone(String status)
    {
        FirebaseUser user =mAuth.getCurrentUser();
        progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.safe_loading));
        progressDialog.show();
        mDatabaseReference.getDatabase().getReference(status).child(user.getUid()).child("Latitude").setValue(latitude).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                }
                else
                {
                    progressDialog.hide();
                }
            }
        });
        progressDialog.show();
        mDatabaseReference.getDatabase().getReference(status).child(user.getUid()).child("Longitude").setValue(longitude).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                }
                else
                {
                    progressDialog.hide();
                }
            }
        });
    }
    public void maps(String Status) {
        FirebaseUser user = mAuth.getCurrentUser();
        mDatabaseReference.getDatabase().getReference("User").child(user.getUid()).child("Status").setValue(Status);
        progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.safe_loading));
        progressDialog.show();
        mDatabaseReference.getDatabase().getReference("User").child(user.getUid()).child("Status").setValue(Status).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("Latitude", latitude);
                    intent.putExtra("Longitude",longitude);
                    startActivity(intent);

                    finish();
                } else {
                    progressDialog.hide();
                    Toast.makeText(SafeActivity.this, getApplicationContext().getResources().getString(R.string.safe_unsucessful), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void requestLocation() {
        // Get the location manager

        fusedLocationProviderClient = new FusedLocationProviderClient(this);
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //locationRequest.setFastestInterval(2000);
        locationRequest.setSmallestDisplacement(100);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                setLocation(locationResult);
            }
        }, getMainLooper());
    }
    public void callPermission()
    {
        String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        com.nabinbhandari.android.permissions.Permissions.check(this, permissions, "Location is necesaary for tracking"/*rationale*/, null/*options*/, new PermissionHandler() {
            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                callPermission();
            }

            @Override
            public void onGranted() {
                requestLocation();
                // do your task.
            }
        });
    }
    public void setLocation(LocationResult location)
    {
        longitude=Double.toString(location.getLastLocation().getLongitude());
        latitude=Double.toString(location.getLastLocation().getLatitude());
        FirebaseUser user=mAuth.getCurrentUser();
        progressDialog.setMessage(getApplicationContext().getResources().getString(R.string.safe_loading));
        progressDialog.show();
        mDatabaseReference.getDatabase().getReference("User").child(user.getUid()).child("Longitude").setValue(longitude).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                }
            }
        });
        progressDialog.show();
        mDatabaseReference.getDatabase().getReference("User").child(user.getUid()).child("Latitude").setValue(latitude).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                }

            }
        });

    }
}

