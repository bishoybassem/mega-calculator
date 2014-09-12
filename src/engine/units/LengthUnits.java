package engine.units;
public enum LengthUnits {
	
	METERS      (1.0),
	FEET        (0.3048),
	INCHES      (0.0254),
	MILES       (1609.344),
	ANSTRIMS    (1.0E-10),
	LIGHT_YEARS (9.4605284E15),
	YARDS       (0.9144);
	
	private double toMeters;
	
	private LengthUnits(double toMeters){
		this.toMeters = toMeters;
	}
	
	public static double convert(double quantity, Prefix preFrom, LengthUnits from, Prefix preTo, LengthUnits to) {
		return quantity * (preFrom.getValue() / preTo.getValue()) * (from.getToMeters() / to.getToMeters());
	}
	
	public double getToMeters() {
		return toMeters;
	}

	public String toString() {
		return name().toLowerCase().replaceAll("_", " ");
	}
	
}
