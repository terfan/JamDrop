package com.example.jamdrop;


import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
 
import android.text.Layout;
import android.util.Log;
 
public class DropSong extends Activity implements LocationListener{
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
MainActivity activity;
DB db;
DBCollection locations;
BasicDBObject query;
DBCursor cursor;

 
@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
//activity = (MainActivity) this.getParent();
setContentView(R.layout.drop_song_activity);
//txtLat = (TextView) findViewById(R.id.test);

locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
List<String> providers = locationManager.getProviders(true);
locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
Location location = locationManager.getLastKnownLocation(providers.get(0));
//Toast.makeText(this, "lat: "+location.getLatitude(), Toast.LENGTH_LONG).show();

getRange(location);

}


public void getRange(Location location) {

	double min_lat = location.getLatitude() - quarter_mile;
	double min_long = location.getLongitude() - quarter_mile;
	double max_lat = location.getLatitude() + quarter_mile;
	double max_long = location.getLongitude()+ quarter_mile;
	System.out.println("got location poop");
	db = MainActivity.getDB();
	locations = MainActivity.getLocationCollection();
	
	query = new BasicDBObject("latitude", new BasicDBObject("$gt", min_lat)).append("latitude", 
			new BasicDBObject("$lt", max_lat)).append("longitude", new BasicDBObject("$gt", min_long)).append("longitude",
					 new BasicDBObject("$lt", max_long));
	System.out.println("made query");
	cursor = locations.find(query);
	
	
	}

public void onEnterButtonClick(View view) {
	EditText edit = (EditText) this.findViewById(R.id.typesong);
	Button b = (Button) this.findViewById(R.id.submit);
	System.out.println("got views");
	String text = edit.getText().toString();
	
	System.out.println(text);
	addSong(text);
}

//traverse cursor
public void addSong(String song_title) {
	/*RelativeLayout rel = (RelativeLayout) findViewById(R.id.ListView01);
	//makes a new text view for every song in the cursor
	
	while(cursor.hasNext()) {
		TextView song = new TextView(this);
        //song.setId((int)System.currentTimeMillis());
        LayoutParams lay = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        lay.addRule(RelativeLayout.BELOW, rel.getId());
        song.setText("Title: "+ cursor.next().get("song_title"));
        rel.addView(song, lay);
	}*/
	
	
	BasicDBObject document = null;
	//get song collection 
	MongoClient mongo = null;
	try {
		mongo = new MongoClient("162.243.97.194", 27017);
		db = mongo.getDB("test");
		DBCollection songs = db.getCollection("songs");
		System.out.println("made client and collection poop");
		
		//make a new song object
		document = new BasicDBObject();
		document.put("song_title", song_title);
		System.out.println("made song doc");
		songs.insert(document); //it breaks here
		System.out.println("inserted document");
		
		
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("phewww");
	BasicDBObject location = new BasicDBObject();
	System.out.println("made location doc");
	location.put("latitude", latitude);
	location.put("longitude", longitude);
	System.out.println("put lat/long");
	location.put("songs", document.get("_id"));
	System.out.println("got put id");
	
	//put this document's id into locations database
	locations.insert(location);
	
}




@Override
public void onLocationChanged(Location location) {
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
