package engine;

import java.util.Arrays;

public class Matrix {
	
	private double[][] array;
	
	public Matrix(int rows, int columns) {
		if (rows > 1 && columns > 1 && (rows != 1 && columns != 1)){
			array = new double[rows][columns];
		} else
			throw new IllegalArgumentException();
	}

	public Matrix(double[][] matrix) {
		for (int i = 0; i < matrix.length - 1; i++){
			if (matrix[i].length != matrix[i + 1].length)
				throw new IllegalArgumentException();
		}
		this.array = cloneArray(matrix);
	}
	
	public double evaluate() {
		if (array.length != array[0].length)
			throw new IllegalArgumentException();

		return evaluate(array, 0);
	}
	
	private static double evaluate(double[][] determinant, int i) {
		if (determinant.length == 2)
			return (determinant[0][0] * determinant[1][1]) - (determinant[0][1] * determinant[1][0]);
		if (i == determinant.length)
			return 0;
		
		return ((int) Math.pow(-1, i)) * determinant[0][i] * evaluate(getMinor(determinant, 0, i), 0) + evaluate(determinant, i + 1);
	}
	
	public Matrix getMinor(int row, int column) {
		if (row >= 0 && row < array.length && column >= 0 && column < array[0].length)
			return new Matrix(getMinor(array, row, column));
		
		throw new IllegalArgumentException();
	}
	
	private static double[][] getMinor(double[][] matrix, int row, int column) {
		double[][] minor = new double[matrix.length - 1][matrix.length - 1];
		int m = 0;
		int n;
		for (int i = 0; i < matrix.length; i++) {
			if (i != row){
				n = 0;
				for (int j = 0; j < matrix.length; j++) {
					if (j != column) {
						minor[m][n] = matrix[i][j];
						n++;
					}
				}
				m++;
			}
		}
		return minor;
	}
	
	public Matrix replaceCoulmn(double[] column, int position) {
		double[][] result = cloneArray(array);
		for (int i = 0; i < result.length; i++) {
			result[i][position] = column[i];
		}
		return new Matrix(result);
	}
	
	public Matrix add(Matrix matrix) {
		if ((array.length != matrix.array.length) || (array[0].length != matrix.array[0].length))
			throw new IllegalArgumentException();
		
		double[][] result = new double[array.length][array[0].length];
		for (int i = 0; i < result.length; i++){
			for (int j = 0; j < result[0].length; j++){
				result[i][j] = array[i][j] + matrix.array[i][j];
			}
		}
		return new Matrix(result);
	}
	
	public Matrix subtract(Matrix matrix) {
		if ((array.length != matrix.array.length) || (array[0].length != matrix.array[0].length))
			throw new IllegalArgumentException();
		
		double[][] result = new double[array.length][array[0].length];
		double[][] array2 = matrix.array;
		for (int i = 0; i < result.length; i++){
			for (int j = 0; j < result[0].length; j++){
				result[i][j] = array[i][j] - array2[i][j];
			}
		}
		return new Matrix(result);
	}

	public Matrix multiply(Matrix matrix) {
		if (array[0].length != matrix.array.length)
			throw new IllegalArgumentException();
		
		double[][] result = new double[array.length][matrix.array[0].length];
		for (int i = 0; i < result.length; i++){
			for (int j = 0; j < result[0].length; j++){
				for (int k = 0; k < array[0].length; k++){
					result[i][j] += (array[i][k] * matrix.array[k][j]);
				}
			}
		}
		return new Matrix(result);
	}
	
	public Matrix transpose() {
		double[][] result = new double[array[0].length][array.length];
		for (int i = 0; i < result.length; i++){
			for (int j = 0; j < result[0].length; j++){
				result[i][j] = array[j][i];
			}
		}
		return new Matrix(result);
	}
	
	public void set(int row, int column, double number) {
		if (row >= 0 && row < array.length && column >= 0 && column < array[0].length){
			array[row][column] = number;
		} else
			throw new IllegalArgumentException();
	}
	
	public double get(int row, int column) {
		if (row >= 0 && row < array.length && column >= 0 && column < array[0].length)
			return array[row][column];
		
		throw new IllegalArgumentException();
	}
	
	public int[] getSize() {
		return new int[]{array.length, array[0].length};
	}
	
	private static double[][] cloneArray(double[][] array) {
		double[][] cloned = new double[array.length][array[0].length];
		for (int i = 0; i < cloned.length; i++){
			cloned[i] = array[i].clone();
		}
		return cloned;
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < array.length; i++){
			s += Arrays.toString(array[i]) + "\n";
		}
		return s;
	}
	
}
