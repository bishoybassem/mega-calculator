package engine.units;
public enum AngleUnits {
	
	DEGREES		(1),
	RADIANS		(57.2957795),
	REVOLUTIONS (360);
	
	private double toDegrees;
	
	private AngleUnits(double toDegrees) {
		this.toDegrees = toDegrees;
	}
	
	public static double convert(double quantity, Prefix preFrom, AngleUnits from, Prefix preTo, AngleUnits to) {
		return quantity * (preFrom.getValue() / preTo.getValue()) * (from.getToDegrees() / to.getToDegrees());
	}

	public double getToDegrees() {
		return toDegrees;
	}
	
	public String toString() {
		return name().toLowerCase();
	}
	
}
