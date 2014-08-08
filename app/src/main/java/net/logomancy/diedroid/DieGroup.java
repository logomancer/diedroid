package net.logomancy.diedroid;

import ec.util.MersenneTwisterFast;

public class DieGroup {
	protected Integer Quantity = 0;
	protected Integer Sides = 2;
	protected Integer Adder = 0;
	protected Integer Multiplier = 1;
	protected Integer winThreshold = 0;
	protected Integer failThreshold = 0;
	protected Integer[] Rolls;
	protected MersenneTwisterFast Randomizer = new MersenneTwisterFast();	
	
	protected RollResult roll() {
		Integer total = 0;
		Integer wins = 0;
		Integer fails = 0;
		Rolls = new Integer[Quantity];
		
		if(Quantity > 0) {
			int i;
			for (i = 0; i < Quantity; i++) {
				Rolls[i] = Randomizer.nextInt(Sides) + 1;
				total += Rolls[i];
				if((winThreshold > 0) && (Rolls[i] >= winThreshold)) {wins++;}
				if((failThreshold > 0) && (Rolls[i] <= failThreshold)) {fails++;}
			}
		}
		total *= Multiplier;			
		total += Adder;
		return new RollResult(this.getString(), Rolls, total, winThreshold, failThreshold, wins, fails);
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

	protected DieGroup(int NumDice, int NumSides, int Add, int Multiply, int Win, int Fail) {
		if (NumDice < 0) {
			Quantity = 0;
		}
		else {
			Quantity = NumDice;
		}
		if (NumSides > 2) {
			Sides = NumSides;
		}		
		Adder = Add;
		Multiplier = Multiply;
		if (Win > 0) {winThreshold = Win;}
		if (Fail > 0) {failThreshold = Fail;}
	}
	
	protected DieGroup(int NumDice, int NumSides, int Add, int Multiply) {
		if (NumDice < 0) {
			Quantity = 0;
		}
		else {
			Quantity = NumDice;
		}
		if (NumSides > 2) {
			Sides = NumSides;
		}		
		Adder = Add;
		Multiplier = Multiply;
	}
	
	protected DieGroup(int NumDice, int NumSides) {
		if (NumDice > 0) {
			Quantity = NumDice;
		}
		if (NumSides > 2) {
			Sides = NumSides;
		}
	}
	
	protected DieGroup() {
		
	}
}
