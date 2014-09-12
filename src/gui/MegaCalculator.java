package gui;


import gui.esolver.LinearEquationsSolver;
import gui.fdifferentiator.FunctionDifferentiator;
import gui.fgrapher.FunctionGrapher;
import gui.fintegrator.FunctionIntegrator;
import gui.mcalculator.MatrixCalculator;
import gui.uconverter.UnitConverter;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class MegaCalculator extends JFrame {
	
	private CalculatorInterface current;
	private NotationsDialog notationsDialog;
	private MessageDialog messageDialog;
	
	private JButton switchView;
	private JButton switchAngle;
	private JPanel menu;
	private JPanel main; 

	public static HashMap<String, ImageIcon> images;
	public static HashMap<String, AudioClip> audio;
	
	static {
		images = new HashMap<String, ImageIcon>();
		images.put("fg.png", new ImageIcon(MegaCalculator.class.getResource("resources/fg.png")));
		images.put("fd.png", new ImageIcon(MegaCalculator.class.getResource("resources/fd.png")));
		images.put("fi.png", new ImageIcon(MegaCalculator.class.getResource("resources/fi.png")));
		images.put("es.png", new ImageIcon(MegaCalculator.class.getResource("resources/es.png")));
		images.put("mc.png", new ImageIcon(MegaCalculator.class.getResource("resources/mc.png")));
		images.put("uc.png", new ImageIcon(MegaCalculator.class.getResource("resources/uc.png")));
		images.put("help.png", new ImageIcon(MegaCalculator.class.getResource("resources/help.png")));
		images.put("info1.png", new ImageIcon(MegaCalculator.class.getResource("resources/info1.png")));
		images.put("info2.png", new ImageIcon(MegaCalculator.class.getResource("resources/info2.png")));
		images.put("error.png", new ImageIcon(MegaCalculator.class.getResource("resources/error.png")));
		images.put("exit.png", new ImageIcon(MegaCalculator.class.getResource("resources/exit.png")));
		images.put("cal1.png", new ImageIcon(MegaCalculator.class.getResource("resources/cal1.png")));
		images.put("cal2.png", new ImageIcon(MegaCalculator.class.getResource("resources/cal2.png")));
		images.put("AllQuads.png", new ImageIcon(MegaCalculator.class.getResource("resources/AllQuads.png")));
		images.put("1st2ndQuad.png", new ImageIcon(MegaCalculator.class.getResource("resources/1st2ndQuad.png")));
		images.put("1stQuad.png", new ImageIcon(MegaCalculator.class.getResource("resources/1stQuad.png")));
		images.put("2nd3rdQuad.png", new ImageIcon(MegaCalculator.class.getResource("resources/2nd3rdQuad.png")));
		images.put("2ndQuad.png", new ImageIcon(MegaCalculator.class.getResource("resources/2ndQuad.png")));
		images.put("3rd4thQuad.png", new ImageIcon(MegaCalculator.class.getResource("resources/3rd4thQuad.png")));
		images.put("3rdQuad.png", new ImageIcon(MegaCalculator.class.getResource("resources/3rdQuad.png")));
		images.put("4th1stQuad.png", new ImageIcon(MegaCalculator.class.getResource("resources/4th1stQuad.png")));
		images.put("4thQuad.png", new ImageIcon(MegaCalculator.class.getResource("resources/4thQuad.png")));
		images.put("camera.png", new ImageIcon(MegaCalculator.class.getResource("resources/camera.png")));
		images.put("eraser.png", new ImageIcon(MegaCalculator.class.getResource("resources/eraser.png")));
		images.put("graph.png", new ImageIcon(MegaCalculator.class.getResource("resources/graph.png")));
		images.put("magnifying glass.png", new ImageIcon(MegaCalculator.class.getResource("resources/magnifying glass.png")));
		images.put("settings.png", new ImageIcon(MegaCalculator.class.getResource("resources/settings.png")));
		images.put("f(x).png", new ImageIcon(MegaCalculator.class.getResource("resources/f(x).png")));
		images.put("differential.png", new ImageIcon(MegaCalculator.class.getResource("resources/differential.png")));
		images.put("ddx.png", new ImageIcon(MegaCalculator.class.getResource("resources/ddx.png")));
		images.put("integral.png", new ImageIcon(MegaCalculator.class.getResource("resources/integral.png")));
		images.put("a.png", new ImageIcon(MegaCalculator.class.getResource("resources/a.png")));
		images.put("b.png", new ImageIcon(MegaCalculator.class.getResource("resources/b.png")));
		
		audio = new HashMap<String, AudioClip>();
		audio.put("error.wav", Applet.newAudioClip(MegaCalculator.class.getResource("resources/error.wav"))); 
		audio.put("notify.wav", Applet.newAudioClip(MegaCalculator.class.getResource("resources/notify.wav"))); 
	}
	
	public MegaCalculator(){
		super("Mega Calculator");
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		UIManager.put("Label.font", new Font("sansserif", Font.PLAIN, 17));
		UIManager.put("Button.font", new Font("sansserif", Font.PLAIN, 17));
		UIManager.put("TextField.font", new Font("sansserif", Font.PLAIN, 17));
		UIManager.put("TextArea.font", new Font("sansserif", Font.PLAIN, 17));
		UIManager.put("ComboBox.font", new Font("sansserif", Font.PLAIN, 17));

		notationsDialog = new NotationsDialog(this);
		messageDialog = new MessageDialog(this, MessageDialog.ABOUT);

		switchAngle = new JButton("Radians");
		switchAngle.setFocusable(false);
		switchAngle.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				switchCurrentAngle();
			}
        	
        });
		
		switchView = new JButton("Simple view");
		switchView.setFocusable(false);
		switchView.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				switchCurrentView();
			}
        	
        });
		
		JButton clear = new JButton("Clear");
		clear.setFocusable(false);
		clear.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				clear();
			}
        	
        });

		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
		buttonsPanel.setOpaque(false);
		buttonsPanel.add(clear);
		buttonsPanel.add(switchAngle);
		buttonsPanel.add(switchView);
		
		main = new JPanel(new BorderLayout(0, 0));
		main.setOpaque(false);
		main.add(buttonsPanel, BorderLayout.NORTH);
		main.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.WEST);
		main.add(Box.createRigidArea(new Dimension(10, 0)), BorderLayout.EAST);
		main.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.SOUTH);
		
		setMenu();
		
		JPanel p1 = new JPanel() {
			
			public void paintComponent(Graphics g) {
        	    if (!isOpaque()) {
        	        super.paintComponent(g);
        	        return;
        	    }
        	    Graphics2D g2d = (Graphics2D) g;
        	    int w = getWidth();
        	    int h = getHeight();
        	    
        	    GradientPaint gp = new GradientPaint(0, 0, new Color(210, 210, 210), w, 0, Color.WHITE);
        	    g2d.setPaint(gp);
        	    g2d.fillRect(0, 0, w, h);
        	 
        	    setOpaque(false);
        	    super.paintComponent(g);
        	    setOpaque(true);
        	}
			
		};
		p1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(210, 210, 210)));
		p1.add(menu);
		
		getContentPane().setBackground(Color.WHITE);
		add(main);
		add(p1, BorderLayout.WEST);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setIconImages(Arrays.asList(new Image[]{images.get("cal1.png").getImage(), images.get("cal2.png").getImage()}));
		setResizable(false);
		
		switchCurrentView();
		
		setLocationRelativeTo(null);
	}
	
	private void setMenu(){
		menu = new JPanel(new BorderLayout(0, 0));
		menu.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		menu.setOpaque(false);
		
		JPanel p1 = new JPanel(new GridLayout(6, 0, 0, 2));
		p1.setOpaque(false);
		
		JPanel p2 = new JPanel(new GridLayout(3, 0, 0, 2));
		p2.setOpaque(false);
		
		JButton[] buttons = new JButton[9];
        buttons[0] = new JButton("Function Grapher", images.get("fg.png")); 
        buttons[1] = new JButton("Function Differentiator", images.get("fd.png")); 
        buttons[2] = new JButton("Function Integrator", images.get("fi.png")); 
        buttons[3] = new JButton("Linear Equations Solver", images.get("es.png")); 
        buttons[4] = new JButton("Matrix Calculator", images.get("mc.png")); 
        buttons[5] = new JButton("Unit Converter", images.get("uc.png")); 
        buttons[6] = new JButton("Notations", images.get("help.png"));
        buttons[7] = new JButton("About", images.get("info1.png"));
        buttons[8] = new JButton("Exit", images.get("exit.png"));
        
        for (int i = 0; i < buttons.length; i++){
        	buttons[i].setHorizontalAlignment(JButton.LEADING);
        	buttons[i].setIconTextGap(15);
        	buttons[i].setFocusable(false);
        	buttons[i].setMaximumSize(new Dimension(buttons[i].getWidth(), buttons[i].getHeight()));
        	if (i < 6) {
        		p1.add(buttons[i]);
        	} else {
        		p2.add(buttons[i]);
        	}
        }
        
        menu.add(p1, BorderLayout.NORTH);
        menu.add(Box.createRigidArea(new Dimension(0, 20)));
        menu.add(p2, BorderLayout.SOUTH);
        
        buttons[0].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				new FunctionGrapher().setVisible(true);
			}
        	
        });
        buttons[1].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				new FunctionDifferentiator().setVisible(true);
			}
        	
        });
        buttons[2].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				new FunctionIntegrator().setVisible(true);
			}
        	
        });
        buttons[3].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				new LinearEquationsSolver().setVisible(true);
			}
        	
        });
        buttons[4].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				new MatrixCalculator().setVisible(true);
			}
        	
        });
        buttons[5].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				new UnitConverter().setVisible(true);
			}
        	
        });
        buttons[6].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				notationsDialog.refresh();
				notationsDialog.setVisible(true);
				notationsDialog.setLocationRelativeTo(null);
			}
        	
        });
        buttons[7].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				messageDialog.setVisible(true);
			}
        	
        });
        buttons[8].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
        	
        });
	}
	
	private void switchCurrentView() {
		CalculatorInterface.degrees = true;
		switchAngle.setText("Radians");
		CalculatorInterface newOne;
		if (current == null || current instanceof SimpleView){
			switchView.setText("Simple view");
			newOne = new ImageView(this);
		} else {
			switchView.setText("Image view");
			newOne = new SimpleView(this);
		}
		main.add(newOne);
		if (current != null){
			main.remove(current);
		}
		current = newOne;
		main.repaint();
		pack();
		current.setFocus();
	}
	
	private void switchCurrentAngle() {
		CalculatorInterface.degrees = !CalculatorInterface.degrees;
		switchAngle.setText((CalculatorInterface.degrees)? "Radians" : "Degrees");
		CalculatorInterface newOne;
		if (current instanceof ImageView){
			newOne = new ImageView(this);
		} else {
			newOne = new SimpleView(this);
		}
		main.add(newOne);
		main.remove(current);
		current = newOne;
		main.repaint();
		pack();
		current.setFocus();
	}
	
	private void clear() {
		CalculatorInterface.degrees = true;
		switchAngle.setText("Radians");
		CalculatorInterface newOne;
		if (current instanceof ImageView){
			switchView.setText("Simple view");
			newOne = new ImageView(this);
		} else {
			switchView.setText("Image view");
			newOne = new SimpleView(this);
		}
		main.add(newOne);
		if (current != null){
			main.remove(current);
		}
		current = newOne;
		main.repaint();
		pack();
		current.setFocus();
	}
	
	public static void main(String[] args){
		new MegaCalculator().setVisible(true);
	}
	
}
