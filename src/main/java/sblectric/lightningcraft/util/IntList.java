package sblectric.lightningcraft.util;

/** A more useful integer list */
public class IntList extends JointList<Integer> {
	
	public IntList(){
		super();
	}
	
	/** Returns the average of the numbers in the list */
	public int average() {
		int a = 0;
		for(int i : this) {
			a += i;
		}
		return a / this.size();
	}
	
	/** returns the minimum of the numbers in this list */
	public int min() {
		int a = Integer.MAX_VALUE;
		for(int i : this) {
			a = Math.min(a, i);
		}
		return a;
	}
	
	/** returns the maximum of the numbers in this list */
	public int max() {
		int a = Integer.MIN_VALUE;
		for(int i : this) {
			a = Math.max(a, i);
		}
		return a;
	}
	
	/** returns the variance of numbers in this list */
	public int variance() {
		return this.max() - this.min();
	}
	
	/** Averages the average with the minimum in this list */
	public int averageLowBias() {
		return (this.average() + this.min()) / 2;
	}
	
	/** Averages the average with the maximum in this list */
	public int averageHighBias() {
		return (this.average() + this.max()) / 2;
	}

}
