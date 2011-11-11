package net.logomancy.diedroid;

import ec.util.MersenneTwisterFast;
import android.content.ContentValues;

public class DieGroup {
	protected int Quantity;
	protected int Sides;
	protected int Adder;
	protected int Multiplier;
	protected MersenneTwisterFast Randomizer;
	
	public static final String COLUMN_NAME_SIDES = "sides";
	public static final String COLUMN_NAME_DICE = "quantity";
	public static final String COLUMN_NAME_ADD = "add";
	public static final String COLUMN_NAME_MULTIPLY = "mult";
	
	
	protected int roll() {
		int Total = 0;
		if(Quantity > 0) {
			int i;
			for (i = 0; i < Quantity; i++) {
				Total += Randomizer.nextInt(Sides) + 1;
			}
		}
		Total *= Multiplier;			
		Total += Adder;
		return Total;
	}
	
	public String getString() {
		StringBuilder DieString = new StringBuilder();
		DieString.append(Quantity);
		DieString.append("d");
		DieString.append(Sides);
		if(Adder != 0) {
			DieString.append(" + ");
			DieString.append(Adder);
		}
		if(Multiplier != 1) {
			DieString.append(" x ");
			DieString.append(Multiplier);
		}
		return DieString.toString();
	}
	
	ContentValues toRecord() {
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME_DICE, Quantity);
		values.put(COLUMN_NAME_SIDES, Sides);
		values.put(COLUMN_NAME_ADD, Adder);
		values.put(COLUMN_NAME_MULTIPLY, Multiplier);
		return values;
	}

	protected DieGroup(int NumDice, int NumSides, int Add, int Multiply) {
		if (NumDice < 0) {
			Quantity = 0;
		}
		else {
			Quantity = NumDice;
		}
		if (NumSides < 2) {
			Sides = 2;
		}
		else {
			Sides = NumSides;
		}
		
		Adder = Add;
		Multiplier = Multiply;
		Randomizer = new MersenneTwisterFast();
	}
	
	protected DieGroup() {
		Quantity = 0;
		Sides = 0;
		Adder = 0;
		Multiplier = 1;
		Randomizer = new MersenneTwisterFast();
	}
}
