package com.example.jamdrop;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;
import android.widget.Toast;
 
import android.util.Log;
 
public class GetSongs extends Activity implements LocationListener{
protected LocationManager locationManager;
protected LocationListener locationListener;
protected Context context;
TextView txtLat;
String lat;
String provider;
protected String latitude,longitude; 
protected boolean gps_enabled,network_enabled;
 
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.get_songs_activity);
txtLat = (TextView) findViewById(R.id.test);
//Toast.makeText(this, "HEEYGUUUUUUUUURLasdf;lkadjsf", Toast.LENGTH_LONG).show();
locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
List<String> providers = locationManager.getProviders(true);
locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
Location location = locationManager.getLastKnownLocation(providers.get(0));
Toast.makeText(this, "lat: "+location.getLatitude(), Toast.LENGTH_LONG).show();
}

@Override
public void onLocationChanged(Location location) {
	Toast.makeText(this, "HEEYGUUUUUUUUURL whattttttttttttttttttttaaa", Toast.LENGTH_LONG).show();
txtLat = (TextView) findViewById(R.id.test);
txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
Toast.makeText(this, "lat: "+location.getLatitude(), Toast.LENGTH_LONG).show();
}
 
@Override
public void onProviderDisabled(String provider) {
Log.d("Latitude","disable");
}
 
@Override
public void onProviderEnabled(String provider) {
Log.d("Latitude","enable");
}
 
@Override
public void onStatusChanged(String provider, int status, Bundle extras) {
Log.d("Latitude","status");
}
}