package engine;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Function {
	
	private String function;
	
	public Function(String function) {
		function = prepareFunction(function);
		if (function.isEmpty() || !isFunctionValid(function)){
			throw new RuntimeException("Syntax Error");
		}
		this.function = function;
	}
	
	private static String prepareFunction(String function) {
		function = function.toLowerCase();		
		function = function.replaceAll("sec", "s\u2107c");
		function = function.replaceAll("e", "(" + Math.E + ")");
		function = function.replaceAll("s\u2107c", "sec");
		function = function.replaceAll("pi", "(" + Math.PI + ")");
		function = function.replaceAll("x", "(x)");
		function = function.replaceAll(" ", "");
		function = function.replaceAll("\\+", " + ");
		function = function.replaceAll("\\*", " * ");
		function = function.replaceAll("/", " / ");
		function = function.replaceAll("\\^", " ^ ");
		function = function.replaceAll("%", " % ");
		
		StringBuffer sb = new StringBuffer(function);
	    Matcher m = Pattern.compile("[0-9\\)]\\-").matcher(function);
	    int i = 0;
	    int x = 0;
	    while(m.find(x)) {
	    	sb.replace(m.start() + 1 + i, m.end() + i, " - ");
	    	i += 2;
	    	x = m.end() - 1;
	    }

	    m = Pattern.compile("[[0-9]|\\)][[a-z&&[^x]]|\\(]").matcher(sb.toString());
	    i = 0; 
	    x = 0;
	    while(m.find(x)) {
	    	sb.insert(m.start() + 1 + i, " * ");
	    	i += 3;
	    	x = m.end() - 1;
	    }
		
	    m = Pattern.compile("\\-[a-z\\(\\-]").matcher(sb.toString());
	    i = 0;
	    x = 0;
	    while(m.find(x)) {
	    	sb.insert(m.start() + 1 + i, "1 * ");
	    	i += 4;
	    	x = m.end() - 1;
	    }
	    
		return sb.toString();
	}
	
	public double evaluate() {
		return evaluate(null);
	}
	
	public double evaluate(double at) {
		return evaluate(new Double(at));
	}
	
	private double evaluate(Double at) {
		try {
			return evaluateExpression(function.replaceAll("x", at + ""));
		} catch (Exception ex){
			throw new RuntimeException("Math Error");
		}
	}
	
	private static double evaluateExpression(String expression) throws Exception {
		if (expression.indexOf('(') >= 0){
			int begin = expression.indexOf('(');
			int end = getEnclosingBracket(expression, begin);
			String s = expression.substring(begin + 1, end);
			StringBuffer sb = new StringBuffer(expression);
			sb.delete(begin, end + 1);
			sb.insert(begin, evaluateExpression(s));
			return evaluateExpression(sb.toString());
		} else {
			return performAllOperations(expression);
		}
	} 
	
	private static double performAllOperations(String expression) throws Exception {
		ArrayList<String> terms = new ArrayList<String>(Arrays.asList(expression.split(" ")));
		int first, i, j, k;
		for (int x = 0; x < 3; x++){
			while (true){
				if (x == 0){
					i = terms.indexOf("^");
					j = -1;
					k = -1;
				} else if (x == 1){
					i = terms.indexOf("*");
					j = terms.indexOf("/");
					k = terms.indexOf("%");
				} else {
					i = terms.indexOf("+");
					j = terms.indexOf("-");
					k = -1;
				}
				if (i > 0 && j > 0 && k > 0){
					first = Math.min(Math.min(i, j), k) - 1;
				} else if (i > 0 && j > 0){
					first = Math.min(i, j) - 1;
				} else if (j > 0 && k > 0){
					first = Math.min(j, k) - 1;
				} else if (i > 0 && k > 0){
					first = Math.min(i, k) - 1;
				} else if (i > 0){
					first = i - 1;
				} else if (j > 0){
					first = j - 1;
				} else if (k > 0){
					first = k - 1;
				} else {
					break;
				}
				terms.set(first, "" + performOperation(terms.remove(first), terms.remove(first), terms.get(first)));
			}
		}
		if (terms.size() != 1){
			throw new RuntimeException();
		}
		double result = getTermValue(terms.get(0));
		if (Double.isNaN(result) || Double.isInfinite(result)){
			throw new ArithmeticException();
		} else {
			return result;
		}	
	}
	
	private static double performOperation(String term1, String operation, String term2) throws Exception {
		char c = operation.charAt(0);
		double a = getTermValue(term1);
		double b = getTermValue(term2);
		switch (c){
			case '+' : return a + b;
			case '-' : return a - b;
			case '/' : return a / b;
			case '*' : return a * b;
			case '%' : return a % b;
			case '^' : return Math.pow(a, b);
			default : throw new RuntimeException();
		}
	}
	
	private static double getTermValue(String term) throws Exception {
		try {
			return Double.parseDouble(term);
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer(term);
		    Matcher m = Pattern.compile("[a-z][[0-9]|\\-]").matcher(term);
		    m.find();
		    sb.insert(m.start() + 1, " ");
		    String[] s = sb.toString().split(" ");
			double number = Double.parseDouble(s[1]);
			Method method = MathFunctions.class.getDeclaredMethod(s[0], double.class);
			return (Double)method.invoke(null, number);
		}
	}
			
	private static int getEnclosingBracket(String expression, int at) {
		int skip = 0;
		for (int i = at + 1; i < expression.length(); i++){
			if (expression.charAt(i) == '('){
				skip++;
			}
			if (expression.charAt(i) == ')'){
				if (skip == 0){
					return i;
				} else {
					skip--;
				}
			}
		}
		return -1;
	}
	
	public double[] integrate(double from, double to) {
		MathFunctions.degrees = false;
		double area = 0;
		double result = 0;
		int numberOfRectangles = 10000;
		double delta = (to - from) / numberOfRectangles;
		for (int i = 0; i < numberOfRectangles; i++){
			double h = evaluate(from + ((i + 0.5) * delta));
			result += (h * delta);
			area += Math.abs(h * delta);
		}
		return new double[]{result, area};
	}
	
	public double differentiate(double at) {
		MathFunctions.degrees = false;
		double k = 1.0E-10;
		double fxk = evaluate(at + k);
		double fx = evaluate(at);
		return (fxk - fx) / k;
	}
	
	private static boolean isFunctionValid(String expression) {
		if (expression.indexOf('(') >= 0){
			int begin = expression.indexOf('(');
			int end = getEnclosingBracket(expression, begin);
			if (end < 0){
				return false; 
			}
			String s = expression.substring(begin + 1, end);
			StringBuffer sb = new StringBuffer(expression);
			if (s.isEmpty()) {
				return false;
			}
			if (isFunctionValid(s)) {
				sb.replace(begin, end + 1, " 1 ");
			} else {
				return false;
			}
			return isFunctionValid(sb.toString());
		} else {
			String[] sa = expression.trim().split("[\\s]+");
			for (int i = 0; i < sa.length; i++) {
				if (isFunction(sa[i])) {
					if (i + 1 >= sa.length || isOperator(sa[i + 1]) || (i > 0 && isNumber(sa[i - 1]))){
						return false;
					}
				} else if (isOperator(sa[i])) {
					if (i == 0 || i + 1 >= sa.length || !isNumber(sa[i - 1]) || isOperator(sa[i + 1])){
						return false;
					}
				} else if (isNumber(sa[i])) {
					if ((i + 1 < sa.length && !isOperator(sa[i + 1])) || (i > 0 && isNumber(sa[i - 1]))){
						return false;
					}
				} else {
					return false;
				}
			}
			return true;
		}
	}
	
	private static boolean isFunction(String s) {
		Method[] functions = MathFunctions.class.getDeclaredMethods();
		for (int i = 0; i < functions.length; i++){
			if (functions[i].getName().equals(s)){
				return true;
			}
		}
		return false;
	}
	
	private static boolean isOperator(String s) {
		return Arrays.asList("+", "-", "*", "/", "^", "%").indexOf(s) >= 0;
	}
	
	private static boolean isNumber(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception ex) {
			return (s.charAt(0) == 'x');
		}
	}
	
	public String toString() {
		return function;
	}
	
}
