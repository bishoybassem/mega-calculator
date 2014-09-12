package engine;
public class MathFunctions {
	
	public static boolean degrees = true;
	
	public static double sin(double angle) {
		if (degrees){
			if (Math.abs(angle) % 180 == 0)
				return 0;

			return Math.sin(Math.toRadians(angle));
		}
		if (Math.abs(angle) % Math.PI == 0)
			return 0;

		return Math.sin(angle);
	}
	
	public static double cos(double angle) {
		double x;
		if (degrees){
			x = Math.abs(angle) % 360;
			if (x == 270 || x == 90)
				return 0;
			
			return Math.cos(Math.toRadians(angle));
		}
		x = Math.abs(angle) % Math.PI;
		if (x == (Math.PI / 2) || x == (3 * Math.PI / 2))
			return 0;

		return Math.cos(angle);
	}
	
	public static double tan(double angle) {
		return sin(angle) / cos(angle);
	}
	
	public static double sec(double angle) {
		return 1 / cos(angle);
	}
	
	public static double csc(double angle) {
		return 1 / sin(angle);
	}
	
	public static double cot(double angle) {
		return cos(angle) / sin(angle);
	}
	
	public static double arcsin(double number) {
		if (degrees)
			return Math.toDegrees(Math.asin(number));
		
		return Math.asin(number);
	}
	
	public static double arccos(double number) {
		if (degrees)
			return Math.toDegrees(Math.acos(number));

		return Math.acos(number);
	}
	
	public static double arctan(double number) {
		if (degrees)
			return Math.toDegrees(Math.atan(number));

		return Math.atan(number);
	}
	
	public static double arccsc(double number) {
		return arcsin(1 / number);
	}
	
	public static double arcsec(double number) {
		return arccos(1 / number);
	}
	
	public static double arccot(double number) {
		return arctan(1 / number);
	}
	
	public static double sinh(double number) {
		return Math.sinh(number);
	}
	
	public static double cosh(double number) {
		return Math.cosh(number);
	}
	
	public static double tanh(double number) {
		return Math.tanh(number);
	}
	
	public static double csch(double number) {
		return 1 / sinh(number);
	}
	
	public static double sech(double number) {
		return 1 / cosh(number);
	}
	
	public static double coth(double number) {
		return 1 / tanh(number);
	}
	
	public static double arcsinh(double number) {
		return Math.log(number + Math.sqrt((number * number) + 1));
	}
	
	public static double arccosh(double number) {
		return Math.log(number + Math.sqrt((number * number) - 1));
	}
	
	public static double arctanh(double number) {
		return 0.5 * Math.log((1 + number) /(1 - number));
	}
	
	public static double arccsch(double number) {
		return arcsinh(1 / number);
	}
	
	public static double arcsech(double number) {
		return arccosh(1 / number);
	}
	
	public static double arccoth(double number) {
		return arctanh(1 / number);
	}
	
	public static double fac(double number) {
		if (number < 0 || (int) number != number)
			throw new ArithmeticException();

		double result = 1;
		for (int i = (int) number; i > 1; i--){
			result *= i;
		}
		return result;
	}
	
	public static double ln(double number) {
		return Math.log(number);
	}
	
	public static double log(double number) {
		return Math.log10(number);
	}
	
	public static double abs(double number) {
		return Math.abs(number);
	}
	
	public static double sqrt(double number) {
		return Math.sqrt(number);
	}
	
	public static double cbrt(double number) {
		return Math.cbrt(number);
	}
		
}
