package gui.mcalculator;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import engine.Matrix;
import gui.MessageDialog;

@SuppressWarnings("serial")
public class DeterminantPanel extends OptionPanel {
	
	private JPanel matrix1Panel;
	JTextField[] input1;
	private JTextField matrix1Size;
	private JTextField result;
	
	public DeterminantPanel(JFrame mainFrame) {
		super(mainFrame);
		
		matrix1Size = generateTextField();

		matrix1Panel = new JPanel();
		setMatrix1Panel();
		
		result = new JTextField(10);
		result.setEnabled(false);
		result.setBorder(new LineBorder(new Color(122, 138, 153)));
		result.setHorizontalAlignment(SwingConstants.CENTER);
		result.setDisabledTextColor(Color.BLACK);
		
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		p1.add(Box.createRigidArea(new Dimension(0, 30)));
		p1.add(result);
				
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 0));
		p2.add(matrix1Panel);
		p2.add(equalPanel);
		p2.add(p1);
		
		add(p2);
	}
	
	private void setMatrix1Panel() {
		int size = Integer.parseInt(matrix1Size.getText());
		if (size < 2 || size > 10){
			matrix1Size.setText(((int) Math.sqrt(input1.length)) + "");
			throw new IllegalArgumentException();
		}
		
		matrix1Panel.removeAll();
		
		JPanel p1 = new JPanel();
		input1 = new JTextField[size * size];
		for (int i = 0; i < input1.length; i++){
			input1[i] = new JTextField(3);
			input1[i].setHorizontalAlignment(SwingConstants.CENTER);
			input1[i].getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "calculate");
			input1[i].getActionMap().put("calculate", new AbstractAction(){

				public void actionPerformed(ActionEvent e) {
					calculate();
				}
				
			});
			p1.add(input1[i]);
		}
		p1.setLayout(new GridLayout(size, size, 3, 3));
		
		JPanel p2 = new JPanel();
		p2.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.BLACK));
		p2.add(p1);
		
		JPanel p3 = new JPanel();
		p3.add(new JLabel("Size  = "));
		p3.add(matrix1Size);
		
		matrix1Panel.setLayout(new BorderLayout());
		matrix1Panel.add(p3, BorderLayout.NORTH);
		matrix1Panel.add(p2);
	}
	
	public void calculate() {
		try {
			int size = (int) Math.sqrt(input1.length);
			double[][] array = new double[size][size];
			for (int i = 0; i < input1.length; i++){
				array[i / size][i % size] = Double.parseDouble(((JTextField) input1[i]).getText());
			}
			String r = String.format("%.2f", new Matrix(array).evaluate());
			result.setText(r);
		} catch (Exception ex){
			new MessageDialog(mainFrame, "Invalid input\nPlease check your input!", MessageDialog.ERROR).setVisible(true);
		}
	}
	
	public void clear() {
		for (int i = 0; i < input1.length; i++){
			input1[i].setText("");
			result.setText("");
		}
		setFocus();
	}
	
	public void changeMatrixSize() {
		try {
			setMatrix1Panel();
			result.setText("");
			mainFrame.pack();
			mainFrame.setLocationRelativeTo(null);
			setFocus();
		} catch (Exception ex){
			new MessageDialog(mainFrame, "Invalid size\nPlease check your input!", MessageDialog.ERROR).setVisible(true);
		}
	}
	
	public void setFocus() {
		input1[0].requestFocus();
	}
	
}

