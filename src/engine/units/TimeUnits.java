package engine.units;

public enum TimeUnits {
	
	SECONDS	(1),
	MINUTES	(60),
	HOURS	(3600),
	DAYS	(86400),
	WEEKS	(604800),
	MONTHS	(2629743.83),
	YEARS   (31556926);
	
	private double toSeconds;
	
	private TimeUnits(double toSeconds) {
		this.toSeconds = toSeconds;
	}
	
	public static double convert(double quantity, Prefix preFrom, TimeUnits from, Prefix preTo, TimeUnits to) {
		return quantity * (preFrom.getValue() / preTo.getValue()) * (from.getToSeconds() / to.getToSeconds());
	}

	public double getToSeconds() {
		return toSeconds;
	}
	
	public String toString() {
		return name().toLowerCase();
	}
	
}
