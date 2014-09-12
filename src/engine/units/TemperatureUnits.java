package engine.units;

public enum TemperatureUnits {

	KELVIN, DEGREES, FAHRENHEIT;
	
	private TemperatureUnits() {

	}
	
	public static double convert(double quantity, TemperatureUnits from, TemperatureUnits to) {
		if (from == TemperatureUnits.KELVIN && to == TemperatureUnits.DEGREES){
			return quantity - 273.15;
		} else if (from == TemperatureUnits.KELVIN && to == TemperatureUnits.FAHRENHEIT){
			return (quantity * 9 / 5) - 459.67;
		} else if (from == TemperatureUnits.DEGREES && to == TemperatureUnits.KELVIN){
			return quantity + 273.15;
		} else if (from == TemperatureUnits.DEGREES && to == TemperatureUnits.FAHRENHEIT){
			return (quantity * 9 / 5) + 32 ;
		} else if (from == TemperatureUnits.FAHRENHEIT && to == TemperatureUnits.DEGREES){
			return (quantity - 32) * 5 / 9;
		} else if (from == TemperatureUnits.FAHRENHEIT && to == TemperatureUnits.KELVIN){
			return (quantity + 459.67) * 5 / 9;
		}
		return quantity;
	}

	public String toString() {
		return name().toLowerCase();
	}
	
}
