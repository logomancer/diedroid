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
import android.widget.ImageView;
import android.widget.Toast;

import ec.util.MersenneTwisterFast;

public class PIOFragment extends Fragment implements View.OnClickListener {

    DieGroup die = new DieGroup(1,6,-1,1);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflate the view layout
        return inflater.inflate(R.layout.passiton, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        Button rollOne = (Button) v.findViewById(R.id.pioOneDieBtn);
        rollOne.setOnClickListener(this);
        Button rollTwo = (Button) v.findViewById(R.id.pioTwoDiceBtn);
        rollTwo.setOnClickListener(this);
        Button rollThree = (Button) v.findViewById(R.id.pioThreeDiceBtn);
        rollThree.setOnClickListener(this);

        super.onViewCreated(v, savedInstanceState);

    }

    public void onClick(View v) {
        int howMany = v.getId();
        if (howMany != -1) {
            Integer blank = R.color.Transparent;
            Integer[] OPS = {
                    R.drawable.pio_left,
                    R.drawable.pio_nop,
                    R.drawable.pio_in,
                    R.drawable.pio_nop,
                    R.drawable.pio_right,
                    R.drawable.pio_nop
            };
            ImageView imgOne = (ImageView) getView().findViewById(R.id.pioDieOne);
            ImageView imgTwo = (ImageView) getView().findViewById(R.id.pioDieTwo);
            ImageView imgThree = (ImageView) getView().findViewById(R.id.pioDieThree);
            switch (howMany) {
                case R.id.pioOneDieBtn: // roll one die
                    imgOne.setImageResource(blank);
                    imgTwo.setImageResource(OPS[die.roll().total]);
                    imgThree.setImageResource(blank);
                    break;
                case R.id.pioTwoDiceBtn: // roll two dice
                    imgOne.setImageResource(OPS[die.roll().total]);
                    imgTwo.setImageResource(blank);
                    imgThree.setImageResource(OPS[die.roll().total]);
                    break;
                default: // roll three dice
                    imgOne.setImageResource(OPS[die.roll().total]);
                    imgTwo.setImageResource(OPS[die.roll().total]);
                    imgThree.setImageResource(OPS[die.roll().total]);
                    break;
            }
        } else {
            Toast.makeText(getView().getContext(), getString(R.string.pioErrNoDice), Toast.LENGTH_SHORT).show();
        }

    }
}
