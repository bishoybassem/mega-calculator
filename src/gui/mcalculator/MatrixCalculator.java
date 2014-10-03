package gui.mcalculator;

import gui.MegaCalculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MatrixCalculator extends JFrame {
	 
	private OptionPanel optionPanel;
	private JComboBox<String> comboBox;

	public MatrixCalculator() {
		super("Matrix Calculator");
		
		comboBox = new JComboBox<String>(new String[]{"Determinant evaluation", "Matrix transpose", "Matrix addition", "Matrix subtraction", "Matrix multiplication"});
		comboBox.setFocusable(false);
		comboBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				changeFunctionPanel();
			}

		});
		
		JPanel p1 = new JPanel() {
        	
        	public void paintComponent(Graphics g) {
        	    if (!isOpaque()) {
        	        super.paintComponent(g);
        	        return;
        	    }
        	    
        	    Graphics2D g2d = (Graphics2D) g;
        	    int w = getWidth();
        	    int h = getHeight();
        	    GradientPaint gp = new GradientPaint(0, 0, new Color(210, 210, 210), 0, h, new Color(238, 238, 238));
        	    g2d.setPaint(gp);
        	    g2d.fillRect(0, 0, w, h);
        	 
        	    setOpaque(false);
        	    super.paintComponent(g);
        	    setOpaque(true);
        	}
        	
        };
		p1.add(new JLabel("Operation   =  "));
		p1.add(comboBox);
		
		JButton setSize = new JButton("Set size");	
		setSize.setFocusable(false);
		setSize.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				optionPanel.changeMatrixSize();
			}

		});
		
		JButton calculate = new JButton("Calculate");	
		calculate.setFocusable(false);
		calculate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				optionPanel.calculate();
			}

		});
		
		JButton clear = new JButton("Clear");
		clear.setFocusable(false);
		clear.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				optionPanel.clear();
			}

		});
		
		JPanel p2 = new JPanel() {
        	
        	public void paintComponent(Graphics g) {
        	    if (!isOpaque()) {
        	        super.paintComponent(g);
        	        return;
        	    }
        	    Graphics2D g2d = (Graphics2D) g;
        	    int w = getWidth();
        	    int h = getHeight();
        	     
        	    GradientPaint gp = new GradientPaint(0, 0, new Color(238, 238, 238), 0, h, new Color(210, 210, 210));

        	    g2d.setPaint(gp);
        	    g2d.fillRect(0, 0, w, h);
        	 
        	    setOpaque(false);
        	    super.paintComponent(g);
        	    setOpaque(true);
        	}
        	
        };
		p2.add(setSize);
		p2.add(calculate);
		p2.add(clear);
		
		optionPanel = new DeterminantPanel(this);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setIconImage(MegaCalculator.IMAGES.get("mc.png").getImage());
		
		setLayout(new BorderLayout(0, 5));
		add(p1, BorderLayout.NORTH);
		add(optionPanel);
		add(p2, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
		
		optionPanel.setFocus();
	}

	private void changeFunctionPanel() {
		remove(optionPanel);
		if (comboBox.getSelectedIndex() == 0){
			optionPanel = new DeterminantPanel(this);
		} else if (comboBox.getSelectedIndex() == 1){
			optionPanel = new TransposePanel(this);
		} else if (comboBox.getSelectedIndex() == 2){
			optionPanel = new OperationsPanel(this, OperationsPanel.ADDITION);
		} else if (comboBox.getSelectedIndex() == 3){
			optionPanel = new OperationsPanel(this, OperationsPanel.SUBTRACTION);
		} else if (comboBox.getSelectedIndex() == 4){
			optionPanel = new OperationsPanel(this, OperationsPanel.MULTIPLICATION);
		}
		add(optionPanel);
		pack();
		setLocationRelativeTo(null);
		optionPanel.setFocus();
	}
	
}