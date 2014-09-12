package engine.units;

public enum AreaUnits {
	
	SQUARE_METERS      (1.0),
	SQUARE_KILOMETERS  (1.0E6),
	SQUARE_CENTIMETERS (0.0001),
	SQUARE_MILLIMETERS (1.0E-6),
	SQUARE_FEET        (0.09290304),
	SQUARE_INCHES      (0.00064516),
	SQUARE_MILES       (2589988.11),
	SQUARE_YARDS       (0.83612736);
	
	private double toSquareMeters;
	
	private AreaUnits(double toSquareMeters){
		this.toSquareMeters = toSquareMeters;
	}
	
	public static double convert(double quantity, Prefix preFrom, AreaUnits from, Prefix preTo, AreaUnits to) {
		return quantity * (preFrom.getValue() / preTo.getValue()) * (from.getToSquareMeters() / to.getToSquareMeters());
	}
	
	public double getToSquareMeters() {
		return toSquareMeters;
	}

	public String toString() {
		return name().toLowerCase().replaceAll("_", " ");
	}
	
}
