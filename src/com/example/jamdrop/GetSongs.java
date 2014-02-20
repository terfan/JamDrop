package com.example.jamdrop;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
 
import android.text.Layout;
import android.util.Log;
 
public class GetSongs extends Activity implements LocationListener{
protected LocationManager locationManager;
protected LocationListener locationListener;
protected Context context;
TextView txtLat;
String lat;
String provider;
double latitude;
protected double longitude; 
protected boolean gps_enabled,network_enabled;
private final double quarter_mile = 0.0035714285;

 
@Override
protected void onCreate(Bundle savedInstanceState) {

super.onCreate(savedInstanceState);

setContentView(R.layout.get_songs_activity);

txtLat = (TextView) findViewById(R.id.test);

locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

List<String> providers = locationManager.getProviders(true);
locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

Location location = locationManager.getLastKnownLocation(providers.get(0));

//Toast.makeText(this, "lat: "+ String.valueOf(location.getLatitude()), Toast.LENGTH_LONG).show();
//Toast.makeText(this, "long: "+ String.valueOf(location.getLongitude()), Toast.LENGTH_LONG).show();


}


public void getRange(Location location) {

	/*double min_lat = 32 - quarter_mile;
	double min_long = 32 - quarter_mile;
	double max_lat = -71 + quarter_mile;
	double max_long = -71 + quarter_mile;*/
	
	double min_lat = location.getLatitude() - quarter_mile;
	double min_long = location.getLongitude() - quarter_mile;
	double max_lat = location.getLatitude() + quarter_mile;
	double max_long = location.getLongitude()+ quarter_mile;
	
	System.out.println("getting database");
	getDatabase data = new getDatabase();
	System.out.println("got ittt");
	
	DBCollection locations = data.getLocationCollection();
	
	BasicDBObject query = new BasicDBObject("latitude", new BasicDBObject("$gt", min_lat)).append("latitude", 
			new BasicDBObject("$lt", max_lat)).append("longitude", new BasicDBObject("$gt", min_long)).append("longitude",
					 new BasicDBObject("$lt", max_long));
	
	
	//find all locations within this lat/long range
	DBCursor cursor = locations.find(query);		
	
	displaySongs(cursor);
			
	}
//traverse cursor
public void displaySongs(DBCursor cursor) {
	RelativeLayout rel = (RelativeLayout) findViewById(R.id.ListView01);
	//makes a new text view for every song in the cursor
	while(cursor.hasNext()) {	
		TextView song = new TextView(this);
        song.setId((int)System.currentTimeMillis());
        LayoutParams lay = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lay.addRule(RelativeLayout.BELOW, rel.getId());
        song.setText("Title: "+ cursor.next().get("song_title"));
        rel.addView(song, lay);
	}
}



@Override
public void onLocationChanged(Location location) {
	/*Toast.makeText(this, "HEEYGUUUUUUUUURL whattttttttttttttttttttaaa", Toast.LENGTH_LONG).show();
txtLat = (TextView) findViewById(R.id.test);
txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
Toast.makeText(this, "lat: "+location.getLatitude(), Toast.LENGTH_LONG).show();*/
	latitude = location.getLatitude();
	longitude = location.getLongitude();
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