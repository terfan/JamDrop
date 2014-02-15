package com.example.jamdrop;

import android.os.AsyncTask;

public class GetSongs {
	

	//get user's location
	public void getLocation() {
		LocationHelper myLocationHelper = new LocationHelper(this);
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