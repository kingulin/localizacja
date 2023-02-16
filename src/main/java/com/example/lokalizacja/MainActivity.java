package com.example.lokalizacja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    int REQUST_LOCATION_PERMITION = 0;
    FusedLocationProviderClient fusedLocationProviderClient;
   // Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.button);
        textView = findViewById(R.id.textview2);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sprawdzlicencje();
            }
        }) ;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            sprawdzlicencje();

        }else Toast.makeText(this, "Nie wyrażono zgody na kozystanie z lokalizacji ", Toast.LENGTH_SHORT).show();
    }
    private void sprawdzlicencje(){
        if(ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUST_LOCATION_PERMITION);
        }
        else{
            Log.d("Lokaizacja","wyrazono zgode na lokalizacje ");
      fusedLocationProviderClient.getLastLocation().addOnSuccessListener(
              new OnSuccessListener<Location>() {
                  @Override
                  public void onSuccess(Location location) {
                      if(location != null){
                          double szerokoscGeogaficzna = location.getLatitude();
                          double dlugoscGeogaficzna = location.getLongitude();
                          String opis = "Dlugosc geograficzna "+ dlugoscGeogaficzna +"szerokosc geograficznaa "+szerokoscGeogaficzna+ "czas "+location.getTime();
                        textView.setText(opis);
                      }
                  }
              }
      );
    }}
}