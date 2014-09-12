package engine.units;

public enum VolumeUnits {
	
	LITRES			  (1),
	CUBIC_METERS	  (1000),
	CUBIC_CENTIMETERS (0.001),
	CUBIC_MILLIMETERS (1.0E-6),
	CUBIC_FEET		  (28.3168466),
	CUBIC_INCHES	  (0.016387064);
	
	private double toLitres;
	
	private VolumeUnits(double toLitres) {
		this.toLitres = toLitres;
	}
	
	public static double convert(double quantity, Prefix preFrom, VolumeUnits from, Prefix preTo, VolumeUnits to) {
		return quantity * (preFrom.getValue() / preTo.getValue()) * (from.getToLitres() / to.getToLitres());
	}

	public double getToLitres() {
		return toLitres;
	}
	
	public String toString() {
		return name().toLowerCase().replaceAll("_", " ");
	}
	
}
