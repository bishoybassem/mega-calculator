package engine;
public class Equations {
	
	private double[][] coefficients;
	private double[] constants;
	private String[] unknowns;
	
	public Equations(double[][] coefficients, double[] constants) {
		this.coefficients = coefficients;
		this.constants = constants;
		solve();
	}
	
	private void solve() {
		String[] unknowns = new String[coefficients.length];
		double n;
		Matrix det = new Matrix(coefficients);
		double d = det.evaluate();
		for (int i = 0; i < unknowns.length; i++) {
			n = det.replaceCoulmn(constants, i).evaluate();
			unknowns[i] = getSolution(n, d);
		}
		this.unknowns = unknowns;
	}

	public String[] getUnknowns() {
		return unknowns;
	}
	
	private String getSolution(double n, double d) {
		if (d == 0) {
			if (n == 0)
				return "Infinity";
				
			return "Undefined";
		}
		if (d == 1)
			return String.format("%f", n);
		if ((n / d) == 0)
			return String.format("%f", 0.0);

		return String.format("%f", (n / d));
	}
	
}
