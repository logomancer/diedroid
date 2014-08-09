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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RollFragment extends Fragment implements View.OnClickListener {

    DiceSpinListener misc = new DiceSpinListener(); // need this to implement the dice spinner listener
    private DieGroup Die;

    public void onClick(View v) {

        // capture text boxes
        TextView tNumDice = (TextView) getView().findViewById(R.id.rollNumDice);
        TextView tAdder = (TextView) getView().findViewById(R.id.rollAdd);
        TextView tMult = (TextView) getView().findViewById(R.id.rollMult);

        // grab values from text boxes and spinner
        Die.Sides = misc.getSelValue();
        try { // catch blank strings in the text boxes
            Die.Quantity = Integer.valueOf(tNumDice.getText().toString());
            Die.Adder = Integer.valueOf(tAdder.getText().toString());
            Die.Multiplier = Integer.valueOf(tMult.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), R.string.errorInvalidEntry, Toast.LENGTH_SHORT).show();
        }

        if (Die.Quantity < 1) {
            // we can't roll less than one die
            Toast.makeText(getActivity(), R.string.errorNotEnoughDice, Toast.LENGTH_SHORT).show();
        } else {
            Integer diceTotal = Die.roll().total;

            // capture TextView for result and output total
            TextView grandTotal = (TextView) getView().findViewById(R.id.rollResult);
            StringBuilder grandTotalStr = new StringBuilder();
            grandTotalStr.append(diceTotal);
            grandTotal.setText(grandTotalStr);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the view layout
        return inflater.inflate(R.layout.roll, container, false);
    }

    public void onViewCreated(View v, Bundle savedInstanceState) {
        // capture spinner and attach listener
        Spinner dieSpinner = (Spinner) v.findViewById(R.id.rollSidesSpin);
        dieSpinner.setOnItemSelectedListener(misc);

        // ditto with the button
        Button roll = (Button) v.findViewById(R.id.rollRollButton);
        roll.setOnClickListener(this);

        super.onViewCreated(v, savedInstanceState);
    }

}
