//DieDroid -- An Android dice-roller app
//Copyright (C) 2010  Andrew Mike <logomancer@gmail.com>
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//For a copy of the GNU GPLv3, see http://www.gnu.org/licenses/gpl-3.0-standalone.html
//or http://www.gnu.org/licenses/translations.html for a translation into your local language.

package net.logomancy.diedroid;

import ec.util.MersenneTwisterFast;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.Arrays;

public class StatsActivity extends Activity implements OnItemSelectedListener, OnClickListener {

	private Integer rollType = -1;
	MersenneTwisterFast Random = new MersenneTwisterFast();
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats); // set layout
        
        // capture spinner and attach listener 
        Spinner methodSpinner = (Spinner) findViewById(R.id.statsMethodSpin);
        methodSpinner.setOnItemSelectedListener(this);
        
        // ditto with the button
        Button rollBtn = (Button) findViewById(R.id.statsRollButton);
        rollBtn.setOnClickListener(this);
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.optionmenu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.menuAbout:
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setMessage(R.string.menuAboutText)
	    	       .setCancelable(false)
	    	       .setTitle(R.string.menuAboutTitle)
	    	       .setPositiveButton(R.string.menuAboutSiteBtn, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                Uri url = Uri.parse(getString(R.string.urlWebsite));
	    	                startActivity(new Intent("android.intent.action.VIEW", url));
	    	                
	    	           }
	    	       })
	    	       .setNeutralButton(R.string.menuAboutLicBtn, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	        	   Uri url = Uri.parse(getString(R.string.urlLicense));
	    	               startActivity(new Intent("android.intent.action.VIEW", url));
	    	           }
	    	       })
	    	       .setNegativeButton(R.string.menuAboutCloseBtn, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                dialog.cancel();
	    	           }
	    	       });
	    	AlertDialog about = builder.create();
	    	about.show();
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
    
    // item selected listener for the method spinner
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// just grabbing the selected position here; logic for this will be implemented in the roll button's click listener
		rollType = pos;		
	}

	// stub required for the listener implementation
	public void onNothingSelected(AdapterView<?> arg0) {
		// do nothing
		
	}
	
	// click listener for the roll button
	public void onClick(View v) {
		Integer numStats = 6; // we're rolling 6 stats
		Integer[] dice, stats;
		Integer temp = 0;
		int i;
		stats = new Integer[numStats]; // initialize stats array
		
		// now we roll stats based on what method we want
		switch(rollType) {
		case 1: // Straight 3d6
			for(i = 0; i < numStats; i++) {
				temp = 0;
				temp += Random.nextInt(6) + 1;
				temp += Random.nextInt(6) + 1;
				temp += Random.nextInt(6) + 1;
				stats[i] = temp;
			}
			break;
		case 0: // 4d6, drop the lowest
			dice = new Integer[4];
			for(i = 0; i < numStats; i++) {
				dice[0] = Random.nextInt(6) + 1;
				dice[1] = Random.nextInt(6) + 1;
				dice[2] = Random.nextInt(6) + 1;
				dice[3] = Random.nextInt(6) + 1;
				Arrays.sort(dice);
				// since the array is now sorted in ascending numerical order, dice[0] is the lowest
				stats[i] = dice[1] + dice[2] + dice[3];
			}
			break;
		case 2: // d6 + 14
			for(i = 0; i < numStats; i++) {
				stats[i] = Random.nextInt(6) + 15; // we add the extra 1 because Random will give us 0-5
				
			}
			break;
		}
		
		// capture the GridView and attach an adapter with our stats in it
		GridView statsGrid = (GridView) findViewById(R.id.statsResultsGrid);
		statsGrid.setAdapter(new ArrayAdapter<Integer>(this, R.layout.diegridsquare, stats));
	}

}
