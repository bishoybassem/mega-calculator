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
public class OperationsPanel extends OptionPanel {
	
	public static final int ADDITION = 1;
	public static final int SUBTRACTION = 2;
	public static final int MULTIPLICATION = 3;
	
	private int currentOperation;
	private JPanel operationPanel;
	
	private JPanel matrix1Panel;
	private JTextField matrix1Rows;
	private JTextField matrix1Columns;
	private JTextField[] input1;
	private int matrix1R;
	private int space1;
	
	private JPanel matrix2Panel;
	private JTextField matrix2Rows;
	private JTextField matrix2Columns;
	private JTextField[] input2;
	private int matrix2R;
	private int space2;
	
	private JPanel matrix3Panel;
	private JTextField[] output;
	
	public OperationsPanel(JFrame mainFrame, int operation) {
		super(mainFrame);
		currentOperation = operation;

		matrix1Rows = generateTextField();
		matrix1Columns = generateTextField();
		matrix2Rows = generateTextField();
		matrix2Columns = generateTextField();

		operationPanel = new JPanel();
		setOperationPanel();
		matrix1Panel = new JPanel();
		setMatrix1Panel();
		matrix2Panel = new JPanel();
		setMatrix2Panel();
		matrix3Panel = new JPanel();
		setMatrix3Panel();
		
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));
		p1.add(matrix1Panel);
		p1.add(operationPanel);
		p1.add(matrix2Panel);
		p1.add(equalPanel);
		p1.add(matrix3Panel);
		
		add(p1);
	}

	private void setMatrix1Panel() {
		int rows1 = Integer.parseInt(matrix1Rows.getText());
		int columns1 = Integer.parseInt(matrix1Columns.getText());
		int rows2 = Integer.parseInt(matrix2Rows.getText());
		int columns2 = Integer.parseInt(matrix2Columns.getText());
		if (((currentOperation == 1 || currentOperation == 2) && (rows1 != rows2 || columns1 != columns2)) || (currentOperation == 3 && rows2 != columns1)){
			matrix1Rows.setText(matrix1R + "");
			matrix1Columns.setText((input1.length / matrix1R) + "");
			matrix2Rows.setText(matrix2R + "");
			matrix2Columns.setText((input2.length / matrix2R) + "");
			throw new IllegalArgumentException();
		}
		
		JPanel p1 = new JPanel();
		input1 = new JTextField[rows1 * columns1];
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
		p1.setLayout(new GridLayout(rows1, columns1, 3, 3));
		
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
		
		space1 = (int) ((p4.getPreferredSize().getWidth() - p3.getPreferredSize().getWidth()) / 2);
		
		JPanel p5 = new JPanel();
		p5.setLayout(new BorderLayout());
		p5.add(p4, BorderLayout.NORTH);
		p5.add(p3, BorderLayout.CENTER);
		
		matrix1R = rows1;
		matrix1Panel.removeAll();
		matrix1Panel.setLayout(new BorderLayout());
		matrix1Panel.add(p5);
	}
	
	private void setMatrix2Panel() {
		int rows = Integer.parseInt(matrix2Rows.getText());
		int columns = Integer.parseInt(matrix2Columns.getText());
		
		JPanel p1 = new JPanel();
		input2 = new JTextField[rows * columns];
		for (int i = 0; i < input2.length; i++){
			input2[i] = new JTextField(3);
			input2[i].setHorizontalAlignment(SwingConstants.CENTER);
			input2[i].getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "calculate");
			input2[i].getActionMap().put("calculate", new AbstractAction(){

				public void actionPerformed(ActionEvent e) {
					calculate();
				}
				
			});
			p1.add(input2[i]);
		}
		p1.setLayout(new GridLayout(rows, columns, 3, 3));
		
		JPanel p2 = new JPanel();
		p2.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 2, Color.BLACK));
		p2.add(p1);
		
		JPanel p3 = new JPanel();
		p3.add(p2);
		
		JPanel p4 = new JPanel();
		p4.add(new JLabel("Size  = "));
		p4.add(matrix2Rows);
		p4.add(new JLabel(" x "));
		p4.add(matrix2Columns);
		
		space2 = (int) ((p4.getPreferredSize().getWidth() - p3.getPreferredSize().getWidth()) / 2);
		
		JPanel p5 = new JPanel();
		p5.setLayout(new BorderLayout());
		p5.add(p4, BorderLayout.NORTH);
		p5.add(p3, BorderLayout.CENTER);
		
		matrix2R = rows;
		matrix2Panel.removeAll();
		matrix2Panel.setLayout(new BorderLayout());
		matrix2Panel.add(p5);
		if (space1 > 0 && space2 > 0){
			if (space1 > space2){
				matrix2Panel.add(Box.createRigidArea(new Dimension(space1 - space2, 0)), BorderLayout.WEST);
			} else {
				matrix1Panel.add(Box.createRigidArea(new Dimension(space2 - space1, 0)), BorderLayout.EAST);
			}
		} else if (space1 > 0) {
			matrix2Panel.add(Box.createRigidArea(new Dimension(space1, 0)), BorderLayout.WEST);
		} else if (space2 > 0) {
			matrix1Panel.add(Box.createRigidArea(new Dimension(space2, 0)), BorderLayout.EAST);
		}
	}
	
	private void setMatrix3Panel() {
		int rows = matrix1R;
		int columns;
		if (currentOperation == 1 || currentOperation == 2){
			columns = input1.length / matrix1R;
		} else {
			columns = input2.length / matrix2R;
		}

		JPanel p1 = new JPanel();
		output = new JTextField[rows * columns];
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
		
		matrix3Panel.removeAll();
		matrix3Panel.setLayout(new BorderLayout());
		matrix3Panel.add(Box.createRigidArea(new Dimension(5, 30)), BorderLayout.NORTH);	
		matrix3Panel.add(p3);
		if (space2 > 0){
			matrix3Panel.add(Box.createRigidArea(new Dimension(space2, 0)), BorderLayout.WEST);
		}
	}
	
	private void setOperationPanel() {
		JLabel operation;
		if (currentOperation == OperationsPanel.ADDITION){
			operation = new JLabel("+");
		} else if (currentOperation == OperationsPanel.SUBTRACTION) {
			operation = new JLabel("-");
		} else {
			operation = new JLabel("x");
		}
		
		operationPanel.removeAll();
		operationPanel.setLayout(new BorderLayout());
		operationPanel.add(Box.createRigidArea(new Dimension(0, 30)), BorderLayout.NORTH);
		operationPanel.add(operation);
	}
	
	public void clear() {
		for (int i = 0; i < input1.length; i++){
			input1[i].setText("");
		}
		for (int i = 0; i < input2.length; i++){
			input2[i].setText("");
		}
		for (int i = 0; i < output.length; i++){
			output[i].setText("");
		}
		setFocus();
	}

	public void calculate() {
		try {
			int rows1 = matrix1R;
			int columns1 = input1.length / matrix1R;
			double[][] array1 = new double[rows1][columns1];
			for (int i = 0; i < input1.length; i++){
				array1[i / columns1][i % columns1] = Double.parseDouble(((JTextField) input1[i]).getText());
			}
			
			int rows2 = matrix2R;
			int columns2 = input2.length / matrix2R;
			double[][] array2 = new double[rows2][columns2];
			for (int i = 0; i < input2.length; i++){
				array2[i / columns2][i % columns2] = Double.parseDouble(((JTextField) input2[i]).getText());
			}
			
			Matrix r;
			if (currentOperation == 1){
				r = new Matrix(array1).add(new Matrix(array2));
			} else if (currentOperation == 2){
				r = new Matrix(array1).subtract(new Matrix(array2));
			} else {
				r = new Matrix(array1).multiply(new Matrix(array2));
			}

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
			setOperationPanel();
			setMatrix1Panel();
			setMatrix2Panel();
			setMatrix3Panel();
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
