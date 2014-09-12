package engine.units;

public enum MassUnits {
	
	GRAMS  (1), 
	POUNDS (453.59237), 
	OUNCES (28.3495231), 
	TONNES (1000000);
	
	private double toGrams;
	
	private MassUnits(double toGrams){
		this.toGrams = toGrams;
	}
	
	public static double convert(double quantity, Prefix preFrom, MassUnits from, Prefix preTo, MassUnits to) {
		return quantity * (preFrom.getValue() / preTo.getValue()) * (from.getToGrams() / to.getToGrams());
	}
	
	public double getToGrams() {
		return toGrams;
	}

	public String toString() {
		return name().toLowerCase();
	}


}
