package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import engine.Function;
import engine.MathFunctions;

@SuppressWarnings("serial")
public class SimpleView extends CalculatorInterface {
	
	private JFrame mainFrame;
	
	public SimpleView(JFrame mainFrame){
		super(mainFrame);
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		state.setText((degrees)? "Deg " : "Rad ");
		state.setHorizontalAlignment(SwingConstants.RIGHT);
		state.setBorder(null);
		
		input.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "pressEqual");
		input.getActionMap().put("pressEqual", new AbstractAction(){

			public void actionPerformed(ActionEvent e) {
				calculate();
			}
			
		});
		input.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "clear");
		input.getActionMap().put("clear", new AbstractAction(){

			public void actionPerformed(ActionEvent e) {
				input.setText("");
				result.setText("");
				setFocus();
			}
			
		});
		input.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		
		result.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		
		scrollPane.setBorder(null);
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setOpaque(false);
		p1.add(state, BorderLayout.NORTH);
		p1.add(result, BorderLayout.SOUTH);
		p1.add(scrollPane, BorderLayout.CENTER);
		p1.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(80, 0, 0, 0), new LineBorder(Color.GRAY, 1)));
		
		setBackground(Color.WHITE);
		add(p1);
	}
	
	public void calculate() {
		MathFunctions.degrees = degrees;
		try {
			String r = new Function(input.getText()).evaluate() + "";
			r = r.replaceAll(".0E", "E");
			r = r.replaceAll("E", " x 10 ^ ");
			if (r.endsWith(".0")) {
				r = r.substring(0, r.length() - 2);
			}
			result.setText(r);
		} catch (RuntimeException ex){
			new MessageDialog(mainFrame, ex.getMessage(), 0).setVisible(true);
		}
	}
	
	public void setFocus() {
		input.requestFocus();
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(340, 0);
	}
		
}
