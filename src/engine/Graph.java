package engine;
import java.awt.geom.Point2D;

public class Graph {
	
	private Function function;
	private boolean isDerivative;
	
	public Graph(String function, boolean isDerivative) {
		this.function = new Function(function);
		this.isDerivative = isDerivative;
	}
	
	public Point2D[] getPoints(int start, int end, int noOfPoints){
		MathFunctions.degrees = false;
		Point2D[] points = new Point2D[noOfPoints + 1];
		double delta = (end - start + 0.0) / noOfPoints;
		for (int i = 0; i < points.length; i++){
			try {
				double y;
				double x = start + (i * delta);
				if (isDerivative){
					y = function.differentiate(x);
				} else {
					y = function.evaluate(x);
				}
				points[i] = new Point2D.Double(x, y);
			} catch (RuntimeException ex) {
				points[i] = null;
			}
		}
		return points;
	}
		
}
