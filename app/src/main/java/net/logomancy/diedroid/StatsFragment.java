/*
 * //DieDroid -- An Android dice-roller app
 * //Copyright (C) 2010-2014  Andrew Mike <andrew@logomancy.net>
 * //
 * //This program is free software: you can redistribute it and/or modify
 * //it under the terms of the GNU General Public License as published by
 * //the Free Software Foundation, either version 3 of the License, or
 * //(at your option) any later version.
 * //
 * //This program is distributed in the hope that it will be useful,
 * //but WITHOUT ANY WARRANTY; without even the implied warranty of
 * //MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * //GNU General Public License for more details.
 * //
 * //For a copy of the GNU GPLv3, see http://www.gnu.org/licenses/gpl-3.0-standalone.html
 * //or http://www.gnu.org/licenses/translations.html for a translation into your local language.
 */

package net.logomancy.diedroid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;

public class StatsFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private Integer rollType = -1;
    private Integer dropThreshold = 3;
    private Integer dropBase = 3;
    private SeekBar dropBar;
    private DieGroup dice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflate the view layout
        return inflater.inflate(R.layout.stats, container, false);
    }
    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        // capture spinner and attach listener
        Spinner methodSpinner = (Spinner) v.findViewById(R.id.statsMethodSpin);
        methodSpinner.setOnItemSelectedListener(this);

        // ditto with the button
        Button rollBtn = (Button) v.findViewById(R.id.statsRollButton);
        rollBtn.setOnClickListener(this);

        dropBar = (SeekBar) v.findViewById(R.id.statsDropBar);
        dropBar.setOnSeekBarChangeListener(this);

        super.onViewCreated(v, savedInstanceState);
    }

    // item selected listener for the method spinner
    public void onItemSelected(AdapterView<?> parent, View view, int pos,
                               long id) {
        // just grabbing the selected position here; logic for this will be implemented in the roll button's click listener
        rollType = pos;
        if (rollType == 2) { //4d6 + 14
            dropBase = 15;
            dropBar.setMax(5);
            dropBar.setProgress(0);
            onProgressChanged(dropBar, 0, false);
        } else {
            dropBase = 3;
            dropBar.setMax(15);
            dropBar.setProgress(0);
            onProgressChanged(dropBar, 0, false);
        }
    }

    // stub required for the listener implementation
    public void onNothingSelected(AdapterView<?> arg0) {
        // do nothing

    }

    public void onProgressChanged(SeekBar src, int progress, boolean FromUser) {
        dropThreshold = progress + dropBase;
        StringBuilder thresh = new StringBuilder();
        thresh.append(dropThreshold);
        TextView dropValue = (TextView) getView().findViewById(R.id.statsDropVal);
        dropValue.setText(thresh);
    }

    // click listener for the roll button
    public void onClick(View v) {
        Integer numStats = 6; // we're rolling 6 stats
        Integer[] diceTemp, stats;
        Integer temp;
        int i;
        stats = new Integer[numStats]; // initialize stats array

        // now we roll stats based on what method we want
        switch (rollType) {
            case 1: // Straight 3d6
                dice.Quantity = 3;
                dice.Sides = 6;
                for (i = 0; i < numStats; ) {
                    temp = dice.roll().total;
                    if (temp >= dropThreshold) {
                        stats[i] = temp;
                        i++;
                    }
                }
                break;
            case 0: // 4d6, drop the lowest
                dice.Quantity = 1;
                dice.Sides = 6;
                diceTemp = new Integer[4];
                for (i = 0; i < numStats; ) {
                    diceTemp[0] = dice.roll().total;
                    diceTemp[1] = dice.roll().total;
                    diceTemp[2] = dice.roll().total;
                    diceTemp[3] = dice.roll().total;
                    Arrays.sort(diceTemp);
                    // since the array is now sorted in ascending numerical order, dice[0] is the lowest
                    temp = diceTemp[1] + diceTemp[2] + diceTemp[3];
                    if (temp >= dropThreshold) {
                        stats[i] = temp;
                        i++;
                    }
                }
                break;
            case 2: // d6 + 14
                dice.Quantity = 1;
                dice.Sides = 6;
                dice.Adder = 14;
                for (i = 0; i < numStats; ) {
                    temp = dice.roll().total;
                    if (temp >= dropThreshold) {
                        stats[i] = temp;
                        i++;
                    }
                }
                break;
        }

        // capture the GridView and attach an adapter with our stats in it
        GridView statsGrid = (GridView) getView().findViewById(R.id.statsResultsGrid);
        statsGrid.setAdapter(new ArrayAdapter<Integer>(getView().getContext(), R.layout.diegridsquare, stats));
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        // do nothing

    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        // do nothing

    }
}
