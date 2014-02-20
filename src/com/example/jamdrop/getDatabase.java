package com.example.jamdrop;

import java.net.UnknownHostException;

import android.os.AsyncTask;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class getDatabase {
	
	 static DB db;
	 static DBCollection locations;
	 static DBCollection songs;
	 
	public getDatabase() {
		System.out.println("about to initialize");
		new mongoThread().execute("executing");
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
	
	class mongoThread extends AsyncTask<String, Void, String> {
		
		protected void onPostExecute(String result) {
			
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
