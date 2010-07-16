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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import net.logomancy.diedroid.DiceSpinListener;

public class PoolActivity extends Activity implements OnClickListener {
	DiceSpinListener misc = new DiceSpinListener(); // needed for implementation of dice spinner listener
	MersenneTwisterFast Random = new MersenneTwisterFast();
	
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
	
	// click listener for roll button
	public void onClick(View v) {
		Integer numDice = -1;
		Integer numSides = -1;
		Integer winValue = -1;
		Integer failValue = -1;
		Integer temp = -2;
		Integer successes = 0;
		Integer failures = 0;
		Integer[] diceArray;
		StringBuilder winFailPopup = new StringBuilder();
		
		// capture text boxes
		TextView tNumDice = (TextView) findViewById(R.id.poolDiceNum);
		TextView tWin = (TextView) findViewById(R.id.poolWin);
		TextView tFail = (TextView) findViewById(R.id.poolFail);
		
		// get values needed to roll dice
		numDice = Integer.valueOf(tNumDice.getText().toString());
		numSides = misc.getSelValue();
		winValue = Integer.valueOf(tWin.getText().toString());
		failValue = Integer.valueOf(tFail.getText().toString());
		
		// check for error conditions
		if(numDice < 1) { 
			// less than one die is rolled
			Toast.makeText(this, R.string.errorNotEnoughDice, Toast.LENGTH_SHORT).show();
		}
		else if((winValue < 0) || (failValue < 0)) { 
			// a success or failure value of less than 0 is not possible...
			Toast.makeText(this, R.string.errorWinFailNegative, Toast.LENGTH_SHORT).show();
		}
		else if((winValue > numSides) || (failValue > numSides)) {
			// ...as is a success or failure value of greater than the number of sides on the dice 
			Toast.makeText(this, R.string.errorWinFailOverflow, Toast.LENGTH_SHORT).show();
		}
		else if((winValue > 0) && (winValue < failValue)) {
			// success and failure values are overlapping
			Toast.makeText(this, R.string.errorWinFailOverlap, Toast.LENGTH_SHORT).show();
		}
		else {
			diceArray = new Integer[numDice];
			Integer i;
			// roll the dice
			for(i = 0; i < numDice; i++) {
				temp = Random.nextInt(numSides) + 1;
				if(temp <= failValue) {failures++;}
				else if((winValue > 0) && (temp >= winValue)) {successes++;}
				diceArray[i] = temp;
			}
			
			// capture the GridView for the dice and attach an adapter based on the array of dice we just rolled
			GridView diceGrid = (GridView) findViewById(R.id.poolResultsGrid);
			diceGrid.setAdapter(new ArrayAdapter<Integer>(this, R.layout.diegridsquare, diceArray));
			
			// pop up a Toast stating the number of failures and successes
			if(successes > 0) {
				winFailPopup.append(getString(R.string.poolSuccesses));
				winFailPopup.append(" ");
				winFailPopup.append(successes);
			}
			if (failures > 0) {
				if(winFailPopup.length() > 0) {winFailPopup.append("  ");}
				winFailPopup.append(getString(R.string.poolFailures));
				winFailPopup.append(" ");
				winFailPopup.append(failures);
			}
			if(winFailPopup.length() > 0) {
				Toast.makeText(this, winFailPopup.toString(), Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pool); // set our layout
        
        // capture the spinner and attach a listener
        Spinner dieSpinner = (Spinner) findViewById(R.id.poolDiceSides);
        dieSpinner.setOnItemSelectedListener(misc);
        
        // ditto with the button
        Button roll = (Button) findViewById(R.id.poolRollButton);
        roll.setOnClickListener(this);
    }

}
