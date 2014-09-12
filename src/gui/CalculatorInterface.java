package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public abstract class CalculatorInterface extends JPanel {
	
	protected JFrame mainFrame;
	protected JTextField state;
	protected JTextArea input;
	protected JTextField result;
	protected JScrollPane scrollPane;
	public static boolean degrees = true;
	
	public CalculatorInterface(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		
		state = new JTextField(25);
		state.setFocusable(false);
		state.setEditable(false);
		state.setForeground(new Color(45, 43, 118));
		state.setBackground(new Color(213, 229, 218));
		state.setFont(new Font("sansserif", Font.PLAIN, 10));
		
		input = new JTextArea(1, 25);
		input.setForeground(new Color(45, 43, 118));
		input.setBackground(new Color(213, 229, 218));
		
		result = new JTextField(25);
		result.setFocusable(false);
		result.setEditable(false);
		result.setFont(new Font("sansserif", Font.PLAIN, 16));
		result.setForeground(new Color(45, 43, 118));
		result.setBackground(new Color(213, 229, 218));
		result.setHorizontalAlignment(SwingConstants.RIGHT);
		
		scrollPane = new JScrollPane(input);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	}
	
	public abstract void setFocus();
	
}
