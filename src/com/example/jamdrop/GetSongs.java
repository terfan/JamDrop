package com.example.jamdrop;

import android.app.Activity;
import android.os.AsyncTask;


public class GetSongs extends Activity {
	LocationHelper myLocationHelper = new LocationHelper(this);
	float longitude;
	float latitude;
	

	//get user's location
	public void getLocation() {
		//launches the task that waits and gets location
		LocationWorker locationTask = new LocationWorker();
		locationTask .execute(new Boolean[] {true});
		longitude = myLocationHelper.getLong();
		latitude = myLocationHelper.getLat();
		
		
		
	}
	
	//get location songs
	public void getSongsforLocation() {
	
	}
	
	//show songs
	
}

class LocationWorker extends AsyncTask<Boolean, Integer, Boolean> {
    
    @Override
    protected void onPreExecute() {}       
   
    @Override
    protected void onPostExecute(Boolean result) {
            /* Here you can call myLocationHelper.getLat() and
            myLocationHelper.getLong() to get the location data.*/
    }
   
    @Override
    protected Boolean doInBackground(Boolean... params) {
           
            //while the location helper has not got a lock
            while(myLocationHelper.gotLocation() == false){
                    //do nothing, just wait
            }
            //once done return true
            return true;
    }
}