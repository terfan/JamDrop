package com.example.jamdrop;

import java.util.Locale;

import com.google.android.gms.common.GooglePlayServicesUtil;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class GetSongs extends Activity {
	LocationHelper myLocationHelper;
	float longitude;
	float latitude;
	
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
	        setContentView(R.layout.get_songs_activity);  
	        //myLocationHelper = new LocationHelper(this);
			//launches the task that waits and gets location
	        System.out.println(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this));
	        
	        
			//LocationWorker locationTask = new LocationWorker();
			//locationTask .execute(new Boolean[] {true});
	       // getLocation();
	    }
	
	
	//get user's location
	public void getLocation() {
		myLocationHelper = new LocationHelper(this);
		//launches the task that waits and gets location
		LocationWorker locationTask = new LocationWorker();
		locationTask .execute(new Boolean[] {true});
	}
	
	//get location songs
	public void getSongsforLocation() {
	
	}
	
	//show songs
	
class LocationWorker extends AsyncTask<Boolean, Integer, Boolean> {
    
    @Override
    protected void onPreExecute() {}       
   
    @Override
    protected void onPostExecute(Boolean result) {

		//gets lat/long
		longitude = myLocationHelper.getLong();
		latitude = myLocationHelper.getLat();
		TextView testview = (TextView) findViewById(R.id.test);
		testview.setText(String.valueOf(myLocationHelper.getLat()) + " " + String.valueOf(longitude));
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

@Override
public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
}

@Override
protected void onResume() {
	
}

}