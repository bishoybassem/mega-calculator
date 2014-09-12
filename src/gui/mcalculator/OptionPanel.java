package gui.mcalculator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public abstract class OptionPanel extends JPanel{
	
	protected JFrame mainFrame;
	protected JPanel equalPanel;
	
	public OptionPanel(JFrame mainFrame) {
		this.mainFrame = mainFrame;
		
		equalPanel = new JPanel();
		setEqualPanel();
	}
	
	protected void setEqualPanel() {		
		equalPanel.removeAll();
		equalPanel.setLayout(new BorderLayout());
		equalPanel.add(Box.createRigidArea(new Dimension(0, 30)), BorderLayout.NORTH);
		equalPanel.add(new JLabel("="));
	}
	
	protected JTextField generateTextField() {
		JTextField textField = new JTextField("2", 3);
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "set size");
		textField.getActionMap().put("set size", new AbstractAction(){

			public void actionPerformed(ActionEvent e) {
				changeMatrixSize();
			}
			
		});
		return textField;
	}
		
	protected abstract void clear();
	
	protected abstract void calculate();
	
	protected abstract void changeMatrixSize();
	
	protected abstract void setFocus();
	
}
