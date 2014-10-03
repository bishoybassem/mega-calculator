package gui.uconverter;


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
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class UnitConverter extends JFrame {
	
	private UnitsPanel unitsPanel;
	private JComboBox<String> types;
	
	private static String[] type = new String[]{"Length", "Area", "Volume", "Mass", "Time", "Angle", "Temperature"};
	
	public UnitConverter(){
		super("Unit Converter");
		unitsPanel = new UnitsPanel(this, 0);

		types = new JComboBox<String>(type);
		types.setFocusable(false);
		types.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				changeType();
				unitsPanel.setFocus();
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
		p1.add(types);
		
		JButton convert = new JButton("Convert");
		convert.setFocusable(false);
		convert.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				unitsPanel.calculate();
			}
			
		});
		
		JButton clear = new JButton("Clear");
		clear.setFocusable(false);
		clear.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				unitsPanel.clear();
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
		p2.add(convert);
		p2.add(clear);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setIconImage(MegaCalculator.IMAGES.get("uc.png").getImage());
		add(p1, BorderLayout.NORTH);
		add(unitsPanel);
		add(p2, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
	}
	
	private void changeType() {
		remove(unitsPanel);
		unitsPanel = new UnitsPanel(this, types.getSelectedIndex());
		add(unitsPanel);
		pack();
		setLocationRelativeTo(null);
	}
	
}
