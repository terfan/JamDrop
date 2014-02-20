package com.example.jamdrop;

import java.net.UnknownHostException;

import android.location.Location;
import android.os.AsyncTask;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;


public class DropSongServer {
	
	 private final double quarter_mile = 0.0035714285;
	 static DB db;
	 static DBCollection locations;
	 static DBCollection songs;
	 String song_title;
	 Location location;
	 
	public DropSongServer(String song_title, Location location) {
		this.song_title = song_title;
		this.location = location;
		
		new dropSong().execute("executing");
	}
	
	public static DB getDB() {
		return db;
	}
	
	public DBCollection getLocationCollection() {
		return locations;
	}
	
	public DBCollection getSongsCollection() {
		return songs;
	}
	
	class dropSong extends AsyncTask<String, Void, String> {
		
		protected void onPostExecute(String result) {
			System.out.println("DONE!!!!");
		}

		@Override
		protected String doInBackground(String... params) {
			
			//Get the database stuff
			System.out.println("hello1");
			String uri = "mongodb://potato:poop@troup.mongohq.com:10003/jamdrop";
			System.out.println("hello2");
			MongoClientURI mongoClientURI=new MongoClientURI(uri);
			System.out.println("hello3");
			MongoClient mongoClient = null;
			System.out.println("hello4");
			try {
				mongoClient = new MongoClient(mongoClientURI); 
				System.out.println("hello5");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("hello6");
			DB db=mongoClient.getDB(mongoClientURI.getDatabase());
			System.out.println("hello7");
			db.authenticate(mongoClientURI.getUsername(), mongoClientURI.getPassword()); //authenticates username/password
			System.out.println("hello8");
			
			
			//get the location and song collection
	        /* locations = db.getCollection("locations");
	         songs = db.getCollection("songs");
	         System.out.println("initialized locationssss");*/
	         
	         
	         //get the minimum and maxiumum latitude/longitude from this location
	        double min_lat = location.getLatitude() - quarter_mile;
	     	double min_long = location.getLongitude() - quarter_mile;
	     	double max_lat = location.getLatitude() + quarter_mile;
	     	double max_long = location.getLongitude()+ quarter_mile;
	     	System.out.println("got location poop");
	     	getDatabase data = new getDatabase();
	     	
	     	locations = data.getLocationCollection();
	     	songs = data.getSongsCollection();
	     	System.out.println("got databases");
	     	
	     	//make a query based on the range of locations
	     	BasicDBObject query = new BasicDBObject("latitude", new BasicDBObject("$gt", min_lat)).append("latitude", 
	     			new BasicDBObject("$lt", max_lat)).append("longitude", new BasicDBObject("$gt", min_long)).append("longitude",
	     					 new BasicDBObject("$lt", max_long));
	     	
	     	//make a cursor for the query
	     	System.out.println("made query");
	     DBCursor cursor = locations.find(query);
	     	System.out.println("made query2");
	         
	        //get latitude/longitude
	         double latitude = location.getLatitude();
	         double longitude = location.getLongitude();
	         
	         //make a song object with the song title
	         BasicDBObject song = new BasicDBObject();
	 		song.put("song_title", song_title);
	 		System.out.println("made song doc");
	 		songs.insert(song); //it breaks here
	 		System.out.println("inserted document");
	 	System.out.println("phewww");
	 	
	 	//make a location and put this song in it
	 	BasicDBObject location = new BasicDBObject();
	 	System.out.println("made location doc");
	 	location.put("latitude", latitude);
	 	location.put("longitude", longitude);
	 	System.out.println("put lat/long");
	 	location.put("songs", song.get("_id")); //is this right?
	 	System.out.println("got put id");
	 	
	 	//put this document's id into locations database
	 	locations.insert(location);
		
			mongoClient.close();
		
		//call invalidate in post thing
		return "did background";
			
		}
	}
	}