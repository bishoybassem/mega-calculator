package gui.fintegrator;

import engine.Function;
import gui.MegaCalculator;
import gui.MessageDialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class IntegralPanel extends JPanel {
	
	private JFrame mainFrame;
	private JTextField function;
	private JTextField from;
	private JTextField to;

	public IntegralPanel(JFrame mainFrame) {
		this.mainFrame = mainFrame;

		function = new JTextField(20);
		function.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "Calculate");
		function.getActionMap().put("Calculate", new AbstractAction(){

			public void actionPerformed(ActionEvent e) {
				calculate();
			}
			
		});
		
		from = new JTextField(3);
		from.setHorizontalAlignment(SwingConstants.CENTER);
		from.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "Calculate");
		from.getActionMap().put("Calculate", new AbstractAction(){

			public void actionPerformed(ActionEvent e) {
				calculate();
			}
			
		});
		
		to = new JTextField(3);
		to.setHorizontalAlignment(SwingConstants.CENTER);
		to.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "Calculate");
		to.getActionMap().put("Calculate", new AbstractAction(){

			public void actionPerformed(ActionEvent e) {
				calculate();
			}
			
		});
		
		JPanel p1 = new JPanel();
		p1.setOpaque(false);
		p1.add(new JLabel(MegaCalculator.IMAGES.get("integral.png")));
		p1.add(function);
		p1.add(new JLabel(MegaCalculator.IMAGES.get("differential.png")));
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		p2.setOpaque(false);
		p2.add(new JLabel(MegaCalculator.IMAGES.get("a.png")));
		p2.add(new JLabel(" = "));
		p2.add(from);
		p2.add(Box.createRigidArea(new Dimension(10, 0)));
		p2.add(new JLabel(MegaCalculator.IMAGES.get("b.png")));
		p2.add(new JLabel(" = "));
		p2.add(to);
		p2.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		
		setLayout(new BorderLayout());
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
	}
	
	public void calculate() {
		int c = 0;
		try {
			Function f = new Function(function.getText());
			c++;
			double a = new Function(from.getText()).evaluate();
			double b = new Function(to.getText()).evaluate();
			c++;
			double[] ans = f.integrate(a, b);
			String result = String.format("Result = %.15f\n", ans[0]);
			String area = String.format("Area = %.15f", ans[1]);
			new MessageDialog(mainFrame, result + area, MessageDialog.RESULT).setVisible(true);
			function.requestFocus();
		} catch (Exception ex){
			String m;
			if (c == 0) {
				m = "function";
			} else if (c == 1){
				m = "range";
			} else {
				if (ex instanceof ArithmeticException){
					m = "range";
				} else {
					m = "function";
				}
			}
			new MessageDialog(mainFrame, "Invalid " + m + "\nPlease check your input!", MessageDialog.ERROR).setVisible(true);
		}
	}
	
	public void clear() {
		function.setText("");
		from.setText("");
		to.setText("");
		function.requestFocus();
	}
	
}