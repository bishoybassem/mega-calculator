package gui.esolver;

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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LinearEquationsSolver extends JFrame {

	private EquationsPanel equationsPanel;
	private JComboBox<String> comboBox;

	public LinearEquationsSolver() {
		super("Linear Equations Solver");
		equationsPanel = new EquationsPanel(this, 2);
		
		String[] options = new String[9];
		for (int i = 0; i < options.length; i++) {
			options[i] = (i + 2) + " unknowns";
		}
		
		comboBox = new JComboBox<String>(options);
		comboBox.setFocusable(false);
		comboBox.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				changeSize();
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
		p1.add(comboBox);

		JButton solve = new JButton("Solve");
		solve.setFocusable(false);
		solve.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				equationsPanel.solve();
			}

		});

		JButton clear = new JButton("Clear");
		clear.setFocusable(false);
		clear.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				equationsPanel.clear();
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
		p2.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
		p2.add(solve);
		p2.add(clear);
		
		setLayout(new BorderLayout(0, 0));
		add(p1, BorderLayout.NORTH);
		add(equationsPanel);
		add(p2, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setIconImage(MegaCalculator.IMAGES.get("es.png").getImage());
		pack();
		setLocationRelativeTo(null);
		equationsPanel.setFocus();
	}
	
	private void changeSize() {
		remove(equationsPanel);
		equationsPanel = new EquationsPanel(this, comboBox.getSelectedIndex() + 2);
		add(equationsPanel);
		pack();
		setLocationRelativeTo(null);
		equationsPanel.setFocus();
	}
	
}

