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

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class DiceSpinListener implements OnItemSelectedListener {

	private int selectionValue = -1;
	private final int[] VALUES = {2, 3, 4, 6, 8, 10, 12, 20, 30, 100};
    
	// this is the event callback for the dice sides spinner used in multiple activities.
    public void onItemSelected(AdapterView<?> parent,
        View view, int pos, long id) {
    	selectionValue = VALUES[pos];
    }

    // stub function required by the listener implementation
    public void onNothingSelected(AdapterView<?> parent) {
      // Do nothing.
    }
    
    // and this is the getter function for the value selected by the dice spinner. If nothing had been selected, it will return -1.
    public int getSelValue() {return selectionValue;}
}