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
public class TransposePanel extends OptionPanel {
	
	private JPanel matrix1Panel;
	private JTextField matrix1Rows;
	private JTextField matrix1Columns;
	private JTextField[] input1;
	private int matrix1R;
	
	private JPanel matrix2Panel;
	private JTextField[] output;
	
	private int space;
	
	public TransposePanel(JFrame mainFrame) {
		super(mainFrame);
		
		matrix1Rows = generateTextField();
		matrix1Columns = generateTextField();
		
		matrix1Panel = new JPanel();
		setMatrix1Panel();
		matrix2Panel = new JPanel();
		setMatrix2Panel();
		
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));
		p2.add(matrix1Panel);
		p2.add(equalPanel);
		p2.add(matrix2Panel);
		
		add(p2);
	}

	private void setMatrix1Panel() {
		int rows = Integer.parseInt(matrix1Rows.getText());
		int columns = Integer.parseInt(matrix1Columns.getText());
		if ((rows == 1 && columns == 1) || rows < 1 || rows > 10 || columns < 1 || columns > 10){
			matrix1Rows.setText(matrix1R + "");
			matrix1Columns.setText((input1.length / matrix1R) + "");
			throw new IllegalArgumentException();
		}
		
		JPanel p1 = new JPanel();
		input1 = new JTextField[rows * columns];
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
		p1.setLayout(new GridLayout(rows, columns, 3, 3));
		
		JPanel p2 = new JPanel();
		p2.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.BLACK));
		p2.add(p1);
		
		JPanel p3 = new JPanel();
		p3.add(p2);
		
		JPanel p4 = new JPanel();
		p4.add(new JLabel("Size  = "));
		p4.add(matrix1Rows);
		p4.add(new JLabel(" x "));
		p4.add(matrix1Columns);
		
		space = (int) ((p4.getPreferredSize().getWidth() - p3.getPreferredSize().getWidth()) / 2);
		
		matrix1R = rows;
		matrix1Panel.removeAll();
		matrix1Panel.setLayout(new BorderLayout());
		matrix1Panel.add(p4, BorderLayout.NORTH);
		matrix1Panel.add(p3, BorderLayout.CENTER);
	}

	private void setMatrix2Panel() {
		int rows = input1.length / matrix1R;
		int columns = matrix1R;

		JPanel p1 = new JPanel();
		output = new JTextField[input1.length];
		for (int i = 0; i < output.length; i++){
			output[i] = new JTextField(3);
			output[i].setEnabled(false);
			output[i].setBorder(new LineBorder(new Color(122, 138, 153), 1));
			output[i].setHorizontalAlignment(SwingConstants.CENTER);
			output[i].setDisabledTextColor(Color.BLACK);
			p1.add(output[i]);
		}
		p1.setLayout(new GridLayout(rows, columns, 4, 4));
		
		JPanel p2 = new JPanel();
		p2.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.BLACK));
		p2.add(p1);
		
		JPanel p3 = new JPanel();
		p3.add(p2);
		
		matrix2Panel.removeAll();
		matrix2Panel.setLayout(new BorderLayout());
		if (space > 0){
			matrix2Panel.add(Box.createRigidArea(new Dimension(space, 0)), BorderLayout.WEST);
		}
		if (p3.getPreferredSize().getHeight() < matrix1Panel.getPreferredSize().getHeight()){
			matrix2Panel.add(Box.createRigidArea(new Dimension(0, 30)), BorderLayout.NORTH);	
		} else {
			matrix1Panel.add(Box.createRigidArea(new Dimension(0, 30)), BorderLayout.SOUTH);
			equalPanel.add(Box.createRigidArea(new Dimension(0, 30)), BorderLayout.SOUTH);
		}
		matrix2Panel.add(p3);
	}
	
	public void clear() {
		for (int i = 0; i < input1.length; i++){
			input1[i].setText("");
			output[i].setText("");
		}
		setFocus();
	}

	public void calculate() {
		try {
			int rows = Integer.parseInt(matrix1Rows.getText());
			int columns = Integer.parseInt(matrix1Columns.getText());
			double[][] array = new double[rows][columns];
			for (int i = 0; i < input1.length; i++){
				array[i / columns][i % columns] = Double.parseDouble(((JTextField) input1[i]).getText());
			}
			Matrix r = new Matrix(array).transpose();
			int[] s = r.getSize();
			for (int i = 0; i < output.length; i++){
				output[i].setText(r.get(i / s[1], i % s[1]) + "");
			}
		} catch (Exception ex){
			new MessageDialog(mainFrame, "Invalid input\nPlease check your input!", MessageDialog.ERROR).setVisible(true);
		}
	}

	public void changeMatrixSize() {
		try {
			setEqualPanel();
			setMatrix1Panel();
			setMatrix2Panel();
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
