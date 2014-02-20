package com.example.jamdrop;


import java.net.UnknownHostException;
import java.util.List;

import com.example.jamdrop.getDatabase.mongoThread;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import android.os.AsyncTask;
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
String song_title;
//MainActivity activity;
DB db;
DBCollection locations;
DBCollection songs;
BasicDBObject query;
DBCursor cursor;
Location location;

 
@Override
protected void onCreate(Bundle savedInstanceState) {
	System.out.println("creating");
super.onCreate(savedInstanceState);
//activity = (MainActivity) this.getParent();
setContentView(R.layout.drop_song_activity);
//txtLat = (TextView) findViewById(R.id.test);
System.out.println("here");
locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
List<String> providers = locationManager.getProviders(true);
locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
location = locationManager.getLastKnownLocation(providers.get(0));
System.out.println("end of on create almost");
//Toast.makeText(this, "lat: "+location.getLatitude(), Toast.LENGTH_LONG).show();

//getRange(location);

}


public void getRange(Location location) {
System.out.println("getting range");
	double min_lat = location.getLatitude() - quarter_mile;
	double min_long = location.getLongitude() - quarter_mile;
	double max_lat = location.getLatitude() + quarter_mile;
	double max_long = location.getLongitude()+ quarter_mile;
	System.out.println("got location poop");
	getDatabase data = new getDatabase();
	
	locations = data.getLocationCollection();
	songs = data.getSongsCollection();
	System.out.println("got databases");
	
	query = new BasicDBObject("latitude", new BasicDBObject("$gt", min_lat)).append("latitude", 
			new BasicDBObject("$lt", max_lat)).append("longitude", new BasicDBObject("$gt", min_long)).append("longitude",
					 new BasicDBObject("$lt", max_long));
	
	System.out.println("made query");
	cursor = locations.find(query);
	System.out.println("made query2");
	
	}


public void onEnterButtonClick(View view) {
	EditText edit = (EditText) this.findViewById(R.id.typesong);
	Button b = (Button) this.findViewById(R.id.submit);
	System.out.println("got views");
	String text = edit.getText().toString();
	
	System.out.println(text);
	song_title = text;
	
	DropSongServer drop = new DropSongServer(text, location);
//	new dropSong().execute("executing");
	//addSong(text);
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
	
	
	
		
		//make a new song object
		BasicDBObject song = new BasicDBObject();
		song.put("song_title", song_title);
		System.out.println("made song doc");
		songs.insert(song); //it breaks here
		System.out.println("inserted document");
	System.out.println("phewww");
	
	BasicDBObject location = new BasicDBObject();
	System.out.println("made location doc");
	location.put("latitude", latitude);
	location.put("longitude", longitude);
	System.out.println("put lat/long");
	location.put("songs", song.get("_id")); //is this right?
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


class dropSong extends AsyncTask<String, Void, String> {
	
	protected void onPostExecute(String result) {
		System.out.println("DONE!!!!");
	}

	@Override
	protected String doInBackground(String... params) {
		System.out.println("hello1");
		String uri = "mongodb://potato:poop@troup.mongohq.com:10003/jamdrop";
		System.out.println("hello2");
		MongoClientURI mongoClientURI=new MongoClientURI(uri); //it crashes here
		System.out.println("hello3");
		MongoClient mongoClient = null;
		System.out.println("hello4");
		try {
			mongoClient = new MongoClient(mongoClientURI); //now it crashes here
			System.out.println("hello5");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("hello6");
		DB db=mongoClient.getDB(mongoClientURI.getDatabase());
		System.out.println("hello7");
		db.authenticate(mongoClientURI.getUsername(), mongoClientURI.getPassword());
		System.out.println("hello8");
		
		
         locations = db.getCollection("locations");
         songs = db.getCollection("songs");
         System.out.println("initialized locationssss");
         
         
         
         double min_lat = location.getLatitude() - quarter_mile;
     	double min_long = location.getLongitude() - quarter_mile;
     	double max_lat = location.getLatitude() + quarter_mile;
     	double max_long = location.getLongitude()+ quarter_mile;
     	System.out.println("got location poop");
     	getDatabase data = new getDatabase();
     	
     	locations = data.getLocationCollection();
     	songs = data.getSongsCollection();
     	System.out.println("got databases");
     	
     	query = new BasicDBObject("latitude", new BasicDBObject("$gt", min_lat)).append("latitude", 
     			new BasicDBObject("$lt", max_lat)).append("longitude", new BasicDBObject("$gt", min_long)).append("longitude",
     					 new BasicDBObject("$lt", max_long));
     	
     	System.out.println("made query");
     	cursor = locations.find(query);
     	System.out.println("made query2");
         
         
         
         
         BasicDBObject song = new BasicDBObject();
 		song.put("song_title", song_title);
 		System.out.println("made song doc");
 		songs.insert(song); //it breaks here
 		System.out.println("inserted document");
 	System.out.println("phewww");
 	
 	BasicDBObject location = new BasicDBObject();
 	System.out.println("made location doc");
 	location.put("latitude", latitude);
 	location.put("longitude", longitude);
 	System.out.println("put lat/long");
 	location.put("songs", song.get("_id")); //is this right?
 	System.out.println("got put id");
 	
 	//put this document's id into locations database
 	locations.insert(location);
         
         
         
         
         
         
         //if (locations == null) System.out.println("null here too :(");
	
         
         
         //System.out.println("Basic DB Object Ex:");
		//BasicDBObject document = new BasicDBObject();
		//document.put("latitude", 39);
		//document.put("longitude", -71); //test
		
		//locations.insert(document);
	
		mongoClient.close();
	
	//call invalidate in post thing
	return "did background";
		
	}
}
}
