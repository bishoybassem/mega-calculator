package gui.fdifferentiator;

import gui.MegaCalculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class FunctionDifferentiator extends JFrame {

	private DifferentialPanel differentialPanel;
	
	public FunctionDifferentiator() {
		super("Function Differentiator");
		differentialPanel = new DifferentialPanel(this);
		differentialPanel.setOpaque(false);
		
		JButton calculate = new JButton("Calculate");
		calculate.setFocusable(false);
		calculate.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				differentialPanel.calculate();
			}
			
		});
		
		JButton clear = new JButton("Clear");
		clear.setFocusable(false);
		clear.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				differentialPanel.clear();
			}
			
		});
		
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
		p1.setOpaque(false);
		p1.add(calculate);
		p1.add(clear);
		p1.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		
		JPanel p2 = new JPanel() {
			
			public void paintComponent(Graphics g) {
			    if (!isOpaque()) {
			        super.paintComponent(g);
			        return;
			    }
			    Graphics2D g2d = (Graphics2D) g;
			    int w = getWidth();
			    int h = getHeight();
			    
			    GradientPaint gp1 = new GradientPaint(0, 0, new Color(210, 210, 210), w / 2, 0, Color.WHITE);
			    GradientPaint gp2 = new GradientPaint(w / 2, 0, Color.WHITE, w, 0, new Color(210, 210, 210));
			    g2d.setPaint(gp1);
			    g2d.fillRect(0, 0, w / 2, h);
			    g2d.setPaint(gp2);
			    g2d.fillRect(w / 2, 0, w, h);
			 
			    setOpaque(false);
			    super.paintComponent(g);
			    setOpaque(true);
			}
			
		};
		p2.setLayout(new BorderLayout());
		p2.add(differentialPanel);
		p2.add(p1, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setIconImage(MegaCalculator.IMAGES.get("fd.png").getImage());
		add(p2);
		pack();
		setLocationRelativeTo(null);
	}
			
}
