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

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

public class DieDroidMain extends ListActivity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        String[] activities = getResources().getStringArray(R.array.activityListChoices);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.approw, activities));
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
    	Intent newActivity = new Intent();
    	
    	// here we open activities based on what item was selected
    	switch(position) {
    	case 0: // Dice Roller
    		newActivity.setClassName(this, "net.logomancy.diedroid.RollActivity");
    		startActivity(newActivity);
    		break;
    	case 1: // Ability Score Roller
    		newActivity.setClassName(this, "net.logomancy.diedroid.StatsActivity");
    		startActivity(newActivity);
    		break;
    	case 2: // Dice Pool Roller
    		newActivity.setClassName(this, "net.logomancy.diedroid.PoolActivity");
    		startActivity(newActivity);
    		break;
    	default: //not implemented yet
    		Toast.makeText(this, R.string.errorNotImplemented, Toast.LENGTH_SHORT).show(); 
    		break;
    	}
    }
}