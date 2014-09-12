package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;

import engine.Function;
import engine.MathFunctions;

@SuppressWarnings("serial")
public class ImageView extends CalculatorInterface {

	private Image layout;
	private double ans;
	
	private ArrayList<String> history;
	private int current;
	private String temp;
	private int caretPosition;
	
	private boolean equalPressed; 
	private boolean isError;
	private boolean isHyp;
	
	private static String[] buttons;
	
	static {
		buttons = new String[]{"sin(", "cos(", "tan(", "sinh(", "cosh(", "tanh(", "ln(", 
				"log(", "fac(", "abs()", "\u221B(", "\u221A(", "sin\u207B\u00B9(", "cos\u207B\u00B9(", 
				"tan\u207B\u00B9(", "sinh\u207B\u00B9(", "cosh\u207B\u00B9(", "tanh\u207B\u00B9(",
				" + ", " - ", " x ", " \u00F7 ", " ^ (", "abs(", "Ans"};
	}
	
	public ImageView(JFrame mainFrame){
		super(mainFrame);
		current = -1;
		history = new ArrayList<String>();
		
		try {
			layout = ImageIO.read(getClass().getResource("resources/layout.gif"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		state.setBounds(48, 79, 20, 10);
		state.setBorder(null);
		setMouseListener(state);
		
		input.addKeyListener(new KeyListener(){

			public void keyTyped(KeyEvent e) {
				e.consume();	
			}

			public void keyPressed(KeyEvent e) {
				e.consume();
			}

			public void keyReleased(KeyEvent e) {
				e.consume();
			}
			
		});
		input.addMouseListener(new MouseListener(){

			public void mousePressed(MouseEvent e) {
				setCaretPos(caretPosition);
			}

			public void mouseClicked(MouseEvent e) {
				setCaretPos(caretPosition);				
			}

			public void mouseEntered(MouseEvent e) {
				setCaretPos(caretPosition);				
			}

			public void mouseExited(MouseEvent e) {
				setCaretPos(caretPosition);				
			}

			public void mouseReleased(MouseEvent e) {
				setCaretPos(caretPosition);				
			}
			
		});
		input.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		setMouseListener(input);
		
		result.setBounds(48, 144, 247, 25);
		result.setBorder(null);
		setMouseListener(result);
		
		scrollPane.setBounds(48, 94, 247, 50);
		scrollPane.setBorder(null);
		
		addMouseListener(new MouseAdapter(){

			public void mousePressed(MouseEvent e) {
				String b = getButton(e.getX(), e.getY());
				performAction(b);
				setFocus();
			}
			
		});
		
		setLayout(null);
		add(result);
		add(scrollPane);
		add(state);
	}
		
	private String getButton(int x, int y) {		
		if (x > 254 && x < 295 && y > 541 && y < 569)
			return "=";
		if (x > 198 && x < 243 && y > 539 && y < 569)
			return "Ans";
		if (x > 201 && x < 243 && y > 396 && y < 427)
			return "DEL";
		if (x > 255 && x < 298 && y > 398 && y < 427)
			return "AC";
		if (x > 77 && x < 115 && y > 226 && y < 252)
			return "SHIFT";
		if (x > 223 && x < 264 && y > 227 && y < 253)
			return "ALPHA";
		if (x > 156 && x < 180 && y > 216 && y < 236)
			return "U";
		if (x > 189 && x < 209 && y > 245 && y < 266)
			return "R";
		if (x > 156 && x < 181 && y > 278 && y < 297)
			return "D";
		if (x > 125 && x < 147 && y > 245 && y < 270)
			return "L";
		if (x > 128 && x < 161 && y > 316 && y < 338)
			return "hyp";

		boolean shift = state.getText().equals("S");
		boolean alpha = state.getText().equals("  A");
		boolean none = !shift && !alpha;

		if (x > 34 && x < 76 && y > 490 && y < 521)
			return none ? "1" : "";
		if (x > 90 && x < 131 && y > 492 && y < 522)
			return none ? "2" : "";
		if (x > 144 && x < 187 && y > 492 && y < 523)
			return none ? "3" : "";
		if (x > 36 && x < 77 && y > 443 && y < 474)
			return none ? "4" : "";
		if (x > 91 && x < 131 && y > 444 && y < 475)
			return none ? "5" : "";
		if (x > 144 && x < 188 && y > 445 && y < 475)
			return none ? "6" : "";
		if (x > 35 && x < 79 && y > 396 && y < 427)
			return none ? "7" : "";
		if (x > 90 && x < 133 && y > 397 && y < 427)
			return none ? "8" : "";
		if (x > 145 && x < 189 && y > 396 && y < 427)
			return none ? "9" : "";
		if (x > 34 && x < 76 && y > 539 && y < 569)
			return none ? "0" : "";
		if (x > 88 && x < 131 && y > 539 && y < 570)
			return none ? "." : "";
		if (x > 83 && x < 115 && y > 354 && y < 378)
			return none ? "-" : "";
		if (x > 199 && x < 240 && y > 493 && y < 521)
			return none ? " + " : "";
		if (x > 254 && x < 296 && y > 492 && y < 524)
			return none ? " - " : "";
		if (x > 201 && x < 243 && y > 444 && y < 474)
			return none ? " x " : "";
		if (x > 254 && x < 296 && y > 445 && y < 474)
			return none ? " \u00F7 " : ""; //divide
		if (x > 126 && x < 160 && y > 354 && y < 377)
			return none ? "(" : "";
		if (x > 173 && x < 207 && y > 355 && y < 377)
			return none ? ")" : "";
		if (x > 37 && x < 70 && y > 277 && y < 300)
			return none ? "\u221A(" : shift ? "\u221B(" : ""; //square root
		if (x > 83 && x < 116 && y > 276 && y < 299)
			return none ? " ^ (" : "";
		if (x > 265 && x < 299 && y > 277 && y < 300)
			return none ? " ^ (2)" : "";
		if (x > 220 && x < 253 && y > 276 && y < 300)
			return none ? " ^ (-1)" : shift ? "fac(" : "";
		if (x > 144 && x < 186 && y > 541 && y < 570)
			return none ? " x 10 ^ (" : shift ? "\u03c0" : "e"; //pi
		if (x > 219 && x < 253 && y > 354 && y < 377)
			return none ? "abs(" : "";
		if (x > 82 && x < 116 && y > 315 && y < 337)
			return none ? "ln(" : shift ? "e ^ (" : "";
		if (x > 36 && x < 70 && y > 315 && y < 337)
			return none ? "log(" : shift ? "10 ^ (" : "";
		if (x > 174 && x < 208 && y > 315 && y < 339)
			return none ? "sin(" : shift ? "sin\u207B\u00B9(" : "";
		if (x > 219 && x < 252 && y > 315 && y < 339)
			return none ? "cos(" : shift ? "cos\u207B\u00B9(" : "";
		if (x > 265 && x < 298 && y > 316 && y < 339)
			return none ? "tan(" : shift ? "tan\u207B\u00B9(" : "";

		return null;
	}
	
	private void performAction(String button){
		if (button == null){
			return;
		}
		if (button.isEmpty()){
			state.setText("");
		} else if (button.equals("hyp") && !isError && !isHyp){
			String l1 = "1-sinh  2-cosh  3-tanh";
			String l2 = "4-sinh\u207B\u00B9  5-cosh\u207B\u00B9  6-tanh\u207B\u00B9";
			if (equalPressed){
				temp = "";
			} else {
				temp = input.getText();
			}
			input.setText(l1 + "\n" + l2);	
			isHyp = true;
			state.setText("");
			result.setText("");
			input.setEditable(false);
			return;
		} else if (button.equals("SHIFT")){
			if (state.getText().equals("S")){
				state.setText("");	
			} else {
				state.setText("S");
			}
		} else if (button.equals("ALPHA")){
			if (state.getText().equals("  A")){
				state.setText("");	
			} else {
				state.setText("  A");
			}
		} else {
			if (button.equals("AC")){
				input.setText("");
				result.setText("");
				isError = false;
				equalPressed = false;
				isHyp = false;
				input.setEditable(true);
			} else if (isError){
				if (button.equals("L") || button.equals("R")){
					input.setText(temp);
					result.setText("");
					input.setEditable(true);
					isError = false;
					setCaretPos(button.equals("R")? 0 : temp.length());
				}
			} else if (isHyp){
				StringBuffer sb =  new StringBuffer(temp);
				String text = "";
				if (button.equals("1")){
					text = "sinh(";
				} else if (button.equals("2")){
					text = "cosh(";
				} else if (button.equals("3")){
					text = "tanh(";
				} else if (button.equals("4")){
					text = "sinh\u207B\u00B9(";
				} else if (button.equals("5")){
					text = "cosh\u207B\u00B9(";
				} else if (button.equals("6")){
					text = "tanh\u207B\u00B9(";
				} else {
					state.setText("");
					return;
				}
				if (equalPressed){
					input.setText(text);
					setCaretPos(text.length());
					equalPressed = false;
				} else {
					sb.insert(caretPosition, text);
					input.setText(sb.toString());
					setCaretPos(caretPosition + text.length());
				}
				input.setEditable(true);
				isHyp = false;
			} else if (button.equals("=")){
				if (!input.getText().isEmpty()){
					MathFunctions.degrees = degrees;
					String r;
					try {
						r = new Function(prepareExpression(input.getText())).evaluate(ans) + "";
					} catch (RuntimeException ex) {
						r = ex.getMessage();
					}
					if (!r.equals("Math Error") && !r.equals("Syntax Error")){
						ans = Double.parseDouble(r);
						r = r.replaceAll(".0E", "E");
						r = r.replaceAll("E", " x 10 ^ ");
						if (r.endsWith(".0")) {
							r = r.substring(0, r.length() - 2);
						}
						result.setText(r);
						history.add(input.getText() + "=" + r);
						current = history.size() - 1;
						equalPressed = true;
					} else {
						isError = true;
						temp = input.getText();
						input.setText(r + "\n[AC] : Cancel        [\u25C2][\u25B8] : Return");
					}
					input.setCaretPosition(0);
					input.setEditable(false);
				}
			} else if (equalPressed){
				if (button.equals("DEL")){
					return;
				} else if (button.equals("L")){
					input.setCaretPosition(input.getDocument().getLength());
					result.setText("");
				} else if (button.equals("R")){
					input.setCaretPosition(0);
					result.setText("");
				} else if(button.equals("U")){
					if (current != -1 && current != 0){
						current--;
						String s = history.get(current);
						input.setText(s.substring(0, s.indexOf('=')));
						input.setCaretPosition(0);
						result.setText(s.substring(s.indexOf('=') + 1));
					}
					return;
				} else if(button.equals("D")){
					if (current != -1 && current != history.size() - 1){
						current++;
						String s = history.get(current);
						input.setText(s.substring(0, s.indexOf('=')));
						input.setCaretPosition(0);
						result.setText(s.substring(s.indexOf('=') + 1));
					}
					return;
				} else {
					result.setText("");
					if (button.equals("Ans")){
						input.setText("Ans");
					} else if (button.equals(" \u00F7 ") || button.equals(" x ") || button.equals(" + ") 
							|| button.equals(" - ") || button.equals(" ^ (") || button.equals(" ^ (-1)") 
							|| button.equals(" ^ (2)") || button.equals(" ^ (3)") || button.equals(" * ")){

						input.setText("Ans" + button);
					} else if (button.equals("fac(")){
						input.setText("fac(Ans)");
					} else {
						input.setText(button);
					}
				} 
				input.setEditable(true);
				equalPressed = false;
			} else {
				if (button.equals("U") || button.equals("D")){
					
				} else if (button.equals("L")){
					StringBuffer sb = new StringBuffer(input.getText());
					String sub = "";
					boolean isFound = false;
					try {
						for (int i = 0; i < buttons.length && !isFound; i++){
							if (input.getCaretPosition() - buttons[i].length() >= 0){
								sub = sb.substring(input.getCaretPosition() - buttons[i].length(), input.getCaretPosition());
							}
							if (sub.equals(buttons[i])){
								isFound = true;
								setCaretPos(input.getCaretPosition() - buttons[i].length());
							}
						}
						if (!isFound){
							setCaretPos(input.getCaretPosition() - 1);
						}
					} catch (Exception ex){
						
					}
				} else if (button.equals("R")){
					StringBuffer sb = new StringBuffer(input.getText());
					String sub = "";
					boolean isFound = false;
					try {
						for (int i = 0; i < buttons.length && !isFound; i++){
							if (input.getCaretPosition() + buttons[i].length() <= input.getText().length()){
								sub = sb.substring(input.getCaretPosition(), input.getCaretPosition() + buttons[i].length());
							}
							if (sub.equals(buttons[i])){
								isFound = true;
								setCaretPos(input.getCaretPosition() + buttons[i].length());
							}
						}
						if (!isFound){
							setCaretPos(input.getCaretPosition() + 1);
						}
					} catch (Exception ex){}
				} else if(button.equals("DEL")){
					StringBuffer sb = new StringBuffer(input.getText());
					String sub = "";
					boolean isDeleted = false;
					try {
						for (int i = 0; i < buttons.length && !isDeleted; i++){
							if (input.getCaretPosition() - buttons[i].length() >= 0){
								sub = sb.substring(input.getCaretPosition() - buttons[i].length(), input.getCaretPosition());
							}
							if (sub.equals(buttons[i])){
								isDeleted = true;
								sb.delete(input.getCaretPosition() - buttons[i].length(), input.getCaretPosition());
								input.setText(sb.toString());
								setCaretPos(caretPosition - buttons[i].length());
							}
						}
						if (!isDeleted){
							sb.deleteCharAt(input.getCaretPosition() - 1);
							input.setText(sb.toString());
							setCaretPos(caretPosition - 1);
						}
					} catch (Exception ex) {}
				} else {
					StringBuffer sb =  new StringBuffer(input.getText());
					sb.insert(input.getCaretPosition(), button);
					input.setText(sb.toString());
					setCaretPos(caretPosition + button.length());
				}
			}
			state.setText("");
		}
		if (!isHyp){
			caretPosition = input.getCaretPosition();
		}
	}
	
	private void setCaretPos(int pos){
		try {
			input.setCaretPosition(pos);
		} catch (Exception ex){}
	}
	
	private void setMouseListener(JComponent component){
		component.addMouseListener(new MouseAdapter(){
			
			public void mousePressed(MouseEvent e) {			
				setFocus();
			}
			
		});
	}
	
	private static String prepareExpression(String expression){
		expression = expression.replaceAll("sin\u207B\u00B9", "arcsin");
		expression = expression.replaceAll("cos\u207B\u00B9", "arccos");
		expression = expression.replaceAll("tan\u207B\u00B9", "arctan");
		expression = expression.replaceAll("sinh\u207B\u00B9", "arcsinh");
		expression = expression.replaceAll("cosh\u207B\u00B9", "arccosh");
		expression = expression.replaceAll("tanh\u207B\u00B9", "arctanh");
		expression = expression.replaceAll("\u221A", "sqrt");
		expression = expression.replaceAll("\u221B", "cbrt");
		expression = expression.replaceAll("\u03c0", "pi");
		expression = expression.replaceAll("\u00F7", "/");
		expression = expression.replaceAll("x", "*");
		expression = expression.replaceAll("Ans", "x");
		return expression;
	}
	
	public void setFocus(){
		if (equalPressed || isError || isHyp){
			requestFocus();
		} else {
			input.requestFocus();
		}
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(layout, 0, 0, 338, 605, null);
		g.setColor(new Color(213, 229, 218));
		g.fillRect(43, 74, 256, 103);
		g.setColor(new Color(45, 43, 118));
		g.setFont(new Font("sansserif", Font.PLAIN, 10));
		g.drawString(degrees? "Deg" : "Rad", 270, 87);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(340, 605);
	}
	
}
