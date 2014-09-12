package engine.units;

public enum Prefix {
	
	NONE  (1), 
	YOTTA (1.0E24), 
	ZETTA (1.0E21), 
	EXA	  (1.0E18), 
	PETA  (1.0E15), 
	TERA  (1.0E12), 
	GIGA  (1.0E9), 
	MEGA  (1.0E6), 
	KILO  (1.0E3), 
	HECTO (1.0E2),
	DEKA  (1.0E1), 
	DECI  (1.0E-1), 
	CENTI (1.0E-2), 
	MILLI (1.0E-3), 
	MICRO (1.0E-6), 
	NANO  (1.0E-9), 
	PICO  (1.0E-12), 
	FEMTO (1.0E-15), 
	ATTO  (1.0E-18), 
	ZEPTO (1.0E-21), 
	YOCTO (1.0E-24);
	
	private double value;

	private Prefix(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}
	
	public String toString() {
		return name().toLowerCase();
	}
	
}
