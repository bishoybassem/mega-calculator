package gui.fdifferentiator;

import engine.Function;
import gui.MegaCalculator;
import gui.MessageDialog;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class DifferentialPanel extends JPanel {
	
	private JFrame mainFrame;
	private JTextField function;
	private JTextField at;

	public DifferentialPanel(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		
		function = new JTextField(20);
		function.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "Calculate");
		function.getActionMap().put("Calculate", new AbstractAction(){

			public void actionPerformed(ActionEvent e) {
				calculate();
			}
			
		});
		
		at = new JTextField(4);
		at.setHorizontalAlignment(SwingConstants.CENTER);
		at.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "Calculate");
		at.getActionMap().put("Calculate", new AbstractAction(){

			public void actionPerformed(ActionEvent e) {
				calculate();
			}
			
		});
		
		JLabel label1 = new JLabel("x");
		label1.setFont(new Font("palatino linotype", Font.ITALIC, 35));
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		setOpaque(false);
		add(new JLabel(MegaCalculator.IMAGES.get("ddx.png")));
		add(function);
		add(new JLabel("at"));
		add(label1);
		add(new JLabel("="));
		add(at);
	}
	
	public void calculate() {
		int c = 0;
		try {
			Function f = new Function(function.getText());
			c++;
			double a = new Function(at.getText()).evaluate();
			c++;
			String r = "Result\n" + f.differentiate(a);
			new MessageDialog(mainFrame, r, MessageDialog.RESULT).setVisible(true);
			function.requestFocus();
		} catch (Exception ex){
			String type;
			if (c == 0) {
				type = "function";
			} else if (c == 1){
				type = "point";
			} else {
				if (ex instanceof ArithmeticException){
					type = "point";
				} else {
					type = "function";
				}
			}
			new MessageDialog(mainFrame, "Invalid " + type + "\nPlease check your input!", MessageDialog.ERROR).setVisible(true);
		}
	}
	
	public void clear() {
		function.setText("");
		at.setText("");
		function.requestFocus();
	}
	
}