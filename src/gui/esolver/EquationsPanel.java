package gui.esolver;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import engine.Equations;
import gui.MessageDialog;

@SuppressWarnings("serial")
public class EquationsPanel extends JPanel {

	private int unknowns;
	private double[] savedConst;
	private double[][] savedCoef;
	
	private JComponent[][] coefficients;
	private JFrame mainFrame;

	public EquationsPanel(JFrame mainFrame, int unknowns) {
		this.mainFrame = mainFrame;
		this.unknowns = unknowns;
		
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(unknowns + 1, unknowns + 2, 10, 5));
		p1.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
		
		coefficients = new JComponent[unknowns + 1][unknowns + 2];
		for (int i = 0; i < coefficients[0].length; i++){
			if (i < unknowns){
				coefficients[0][i] = new JLabel(((char) ('A' + i)) + "", JLabel.CENTER);
			} else {
				coefficients[0][i] = new JLabel();
			}
			p1.add(coefficients[0][i]);
		}
		for (int i = 1; i < coefficients.length; i++){
			coefficients[i][unknowns] = new JLabel("=", JLabel.CENTER);
		}
		
		for (int i = 1; i < coefficients.length; i++){
			for (int j = 0; j < coefficients[0].length; j++){
				if (j != unknowns) {
					coefficients[i][j] = new JTextField(5);
					((JTextField) coefficients[i][j]).setHorizontalAlignment(SwingConstants.CENTER);
					coefficients[i][j].getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "Solve");
					coefficients[i][j].getActionMap().put("Solve", new AbstractAction(){

						public void actionPerformed(ActionEvent e) {
							solve();
						}
						
					});
				}
				p1.add(coefficients[i][j]);
			}
		}
		
		setLayout(new BorderLayout(0, 5));
		add(p1);
	}
		
	public void solve() {
		try {
			prepareInput();
			String[] unknowns = new Equations(savedCoef, savedConst).getUnknowns();
			String result = "";
			if (unknowns[0].equals("Infinity")){
				result += "There is an infinite number of\nsolutions for the given equations";
			} else if (unknowns[0].equals("Undefined")){
				result += "There is no solution\nfor the given equations";
			} else {
				result += "Solution\n";
				for (int i = 0; i < unknowns.length; i++){
					result += String.format("%s = %s", ((char) ('A' + i)) + "", unknowns[i]);
					if (i != unknowns.length - 1){
						result += "\n";
					}
				}
			}
			new MessageDialog(mainFrame, result, MessageDialog.RESULT).setVisible(true);
		} catch (Exception ex){
			new MessageDialog(mainFrame, "Invalid input\nPlease check your input!", MessageDialog.ERROR).setVisible(true);
		}
	}
	
	private void prepareInput() {
		savedCoef = new double[unknowns][unknowns];
		savedConst = new double[unknowns];
		
		for (int i = 1; i < coefficients.length; i++){
			for (int j = 0; j < unknowns; j++){
				if (((JTextField) coefficients[i][j]).getText().equals(""))
					throw new RuntimeException();

				String in = ((JTextField) coefficients[i][j]).getText();
				if (in.indexOf('/') > 0){
					String[] s = in.replaceAll(" ", "").split("/");
					if (s.length != 2)
						throw new RuntimeException();
	
					savedCoef[i - 1][j] = Double.parseDouble(s[0]) / Double.parseDouble(s[1]);
				} else {
					savedCoef[i - 1][j] = Double.parseDouble(((JTextField) coefficients[i][j]).getText());
				}
			}
			if (((JTextField) coefficients[i][coefficients[0].length - 1]).getText().equals(""))
				throw new RuntimeException();
	
			savedConst[i - 1] = Double.parseDouble(((JTextField) coefficients[i][coefficients[0].length - 1]).getText());
		}
	}
	
	public void clear() {
		for (int i = 1; i < coefficients.length; i++){
			for (int j = 0; j < unknowns; j++){
				((JTextField) coefficients[i][j]).setText("");
			}
			((JTextField) coefficients[i][coefficients[0].length - 1]).setText("");
		}
		setFocus();
	}
	
	public void setFocus() {
		coefficients[1][0].requestFocus();
	}
	
}