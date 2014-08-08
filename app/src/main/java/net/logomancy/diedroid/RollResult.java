package net.logomancy.diedroid;

import java.util.Arrays;
import java.util.List;

public class RollResult {
	protected String description;
	protected Integer[] rolls;
	protected Integer total;
	protected Integer winThreshold = 0;
	protected Integer failThreshold = 0;
	protected Integer numWins = 0;
	protected Integer numFails = 0;
	
	RollResult(String desc, Integer[] roll, Integer sum) {
		description = desc;
		rolls = roll;
		total = sum;
	}
	
	RollResult(String desc, Integer[] roll, Integer sum, Integer win, Integer fail, Integer wins, Integer fails) {
		description = desc;
		rolls = roll;
		total = sum;
		
		if (win > 0) {winThreshold = win;}
		if (fail > 0) {failThreshold = fail;}
		if (wins > 0) {numWins = wins;}
		if (fails > 0) {numFails = fails;} 
		
	}
	
	public List<Integer> getRollList() {
		return Arrays.asList(rolls);
	}
}
