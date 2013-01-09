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
import android.content.ActivityNotFoundException;
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

public class RollActivity extends Activity implements OnClickListener {
	DiceSpinListener misc = new DiceSpinListener(); // need this to implement the dice spinner listener
	DieGroup Die = new DieGroup();
	
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
	    	Intent about = new Intent("org.openintents.action.SHOW_ABOUT_DIALOG");
	    	try {
	    		startActivityForResult(about, 0);
	    	}
	    	catch(ActivityNotFoundException e) {
	    		AlertDialog.Builder notFoundBuilder = new AlertDialog.Builder(this);
	    		notFoundBuilder.setMessage(R.string.aboutNotFoundText)
	    				.setTitle(R.string.aboutNotFoundTitle)
	    				.setPositiveButton(R.string.commonYes, new DialogInterface.OnClickListener() {
	    					public void onClick(DialogInterface dialog, int id) {
	    						try{
	    							Intent getApp = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.urlAboutMarket)));
	    							startActivity(getApp);
	    						}
	    						catch(ActivityNotFoundException e) {
	    							Intent getAppAlt = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.urlAboutWeb)));
	    							startActivity(getAppAlt);
	    						}
	    					}
	    				})
	    				.setNegativeButton(R.string.commonNo, new DialogInterface.OnClickListener() {
	 	    	           public void onClick(DialogInterface dialog, int id) {
	 	    	                dialog.cancel();
	 	    	           }
	 	    	       });
	    		AlertDialog notFound = notFoundBuilder.create();
	    		notFound.show();
	    	}
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
		
		// capture text boxes
		TextView tNumDice = (TextView) findViewById(R.id.rollNumDice);
		TextView tAdder = (TextView) findViewById(R.id.rollAdd);
		TextView tMult = (TextView) findViewById(R.id.rollMult);
		
		// grab values from text boxes and spinner
		Die.Sides = misc.getSelValue();
		try { // catch blank strings in the text boxes
			Die.Quantity = Integer.valueOf(tNumDice.getText().toString());
			Die.Adder = Integer.valueOf(tAdder.getText().toString());
			Die.Multiplier = Integer.valueOf(tMult.getText().toString());
		}
		catch (NumberFormatException e) {
			Toast.makeText(this, R.string.errorInvalidEntry, Toast.LENGTH_SHORT).show();
		}
			
		if(Die.Quantity < 1) {
			// we can't roll less than one die
			Toast.makeText(this, R.string.errorNotEnoughDice, Toast.LENGTH_SHORT).show();
		}
		else {
			Integer diceTotal = Die.roll().total;
			
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
