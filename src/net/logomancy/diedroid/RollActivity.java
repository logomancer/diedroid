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

import android.net.Uri;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import net.logomancy.diedroid.DiceSpinListener;
import ec.util.MersenneTwisterFast;

public class RollActivity extends Activity implements OnClickListener {
	DiceSpinListener misc = new DiceSpinListener(); // need this to implement the dice spinner listener
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
	    	       .setNegativeButton(R.string.commonClose, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                dialog.cancel();
	    	           }
	    	       });
	    	AlertDialog about = builder.create();
	    	about.show();
	    	return true;
	    case R.id.menuHelp:
	    	AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
	    	builder1.setMessage(R.string.menuHelpDice)
	    	       .setTitle(R.string.menuHelp)
	    	       .setNegativeButton(R.string.commonClose, new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	                dialog.cancel();
	    	           }
	    	       });
	    	AlertDialog help = builder1.create();
	    	help.show();
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	public void onClick(View v) {
		int numDice = -1;
		int numSides = -1;
		int plus = 0;
		int mult = 1;
		int temp = -2;
		
		// capture text boxes
		TextView tNumDice = (TextView) findViewById(R.id.rollNumDice);
		TextView tAdder = (TextView) findViewById(R.id.rollAdd);
		TextView tMult = (TextView) findViewById(R.id.rollMult);
		
		// grab values from text boxes and spinner
		numSides = misc.getSelValue();
		try { // catch blank strings in the text boxes
			numDice = Integer.valueOf(tNumDice.getText().toString());
			plus = Integer.valueOf(tAdder.getText().toString());
			mult = Integer.valueOf(tMult.getText().toString());
		}
		catch (NumberFormatException e) {
			Toast.makeText(this, R.string.errorInvalidEntry, Toast.LENGTH_SHORT).show();
		}
			
		if(numDice < 1) {
			// we can't roll less than one die
			Toast.makeText(this, R.string.errorNotEnoughDice, Toast.LENGTH_SHORT).show();
		}
		else {
			int Idx;
			int diceTotal = 0;
			
			// roll dice
			for (Idx = 0; Idx < numDice; Idx++) {
				temp = Random.nextInt(numSides) + 1;
				diceTotal += temp;
			}
			
			// add and multiply specified values 
			diceTotal += plus;
			diceTotal *= mult;
			
			// capture TextView for result and output total
			TextView grandTotal = (TextView) findViewById(R.id.rollResult);
			StringBuilder grandTotalStr = new StringBuilder();
			grandTotalStr.append(diceTotal);
			grandTotal.setText(grandTotalStr);
		}
	}
		
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.roll); // set layout
        
        // capture spinner and attach listener
        Spinner dieSpinner = (Spinner) findViewById(R.id.rollSidesSpin);
        dieSpinner.setOnItemSelectedListener(misc);
        
        // ditto with the button
        Button roll = (Button) findViewById(R.id.rollRollButton);
        roll.setOnClickListener(this);
        
    }
	
}
