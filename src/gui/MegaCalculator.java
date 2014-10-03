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
	private MessageDialog notationsDialog;
	private MessageDialog aboutDialog;
	
	private JButton switchView;
	private JButton switchAngle;
	private JPanel menu;
	private JPanel main; 

	public static final HashMap<String, ImageIcon> IMAGES;
	public static final HashMap<String, AudioClip> AUDIO;
	
	static {
		IMAGES = new HashMap<String, ImageIcon>();
		IMAGES.put("fg.png", new ImageIcon(MegaCalculator.class.getResource("resources/fg.png")));
		IMAGES.put("fd.png", new ImageIcon(MegaCalculator.class.getResource("resources/fd.png")));
		IMAGES.put("fi.png", new ImageIcon(MegaCalculator.class.getResource("resources/fi.png")));
		IMAGES.put("es.png", new ImageIcon(MegaCalculator.class.getResource("resources/es.png")));
		IMAGES.put("mc.png", new ImageIcon(MegaCalculator.class.getResource("resources/mc.png")));
		IMAGES.put("uc.png", new ImageIcon(MegaCalculator.class.getResource("resources/uc.png")));
		IMAGES.put("help.png", new ImageIcon(MegaCalculator.class.getResource("resources/help.png")));
		IMAGES.put("info1.png", new ImageIcon(MegaCalculator.class.getResource("resources/info1.png")));
		IMAGES.put("info2.png", new ImageIcon(MegaCalculator.class.getResource("resources/info2.png")));
		IMAGES.put("error.png", new ImageIcon(MegaCalculator.class.getResource("resources/error.png")));
		IMAGES.put("exit.png", new ImageIcon(MegaCalculator.class.getResource("resources/exit.png")));
		IMAGES.put("cal1.png", new ImageIcon(MegaCalculator.class.getResource("resources/cal1.png")));
		IMAGES.put("cal2.png", new ImageIcon(MegaCalculator.class.getResource("resources/cal2.png")));
		IMAGES.put("AllQuads.png", new ImageIcon(MegaCalculator.class.getResource("resources/AllQuads.png")));
		IMAGES.put("1st2ndQuad.png", new ImageIcon(MegaCalculator.class.getResource("resources/1st2ndQuad.png")));
		IMAGES.put("1stQuad.png", new ImageIcon(MegaCalculator.class.getResource("resources/1stQuad.png")));
		IMAGES.put("2nd3rdQuad.png", new ImageIcon(MegaCalculator.class.getResource("resources/2nd3rdQuad.png")));
		IMAGES.put("2ndQuad.png", new ImageIcon(MegaCalculator.class.getResource("resources/2ndQuad.png")));
		IMAGES.put("3rd4thQuad.png", new ImageIcon(MegaCalculator.class.getResource("resources/3rd4thQuad.png")));
		IMAGES.put("3rdQuad.png", new ImageIcon(MegaCalculator.class.getResource("resources/3rdQuad.png")));
		IMAGES.put("4th1stQuad.png", new ImageIcon(MegaCalculator.class.getResource("resources/4th1stQuad.png")));
		IMAGES.put("4thQuad.png", new ImageIcon(MegaCalculator.class.getResource("resources/4thQuad.png")));
		IMAGES.put("camera.png", new ImageIcon(MegaCalculator.class.getResource("resources/camera.png")));
		IMAGES.put("eraser.png", new ImageIcon(MegaCalculator.class.getResource("resources/eraser.png")));
		IMAGES.put("graph.png", new ImageIcon(MegaCalculator.class.getResource("resources/graph.png")));
		IMAGES.put("magnifying glass.png", new ImageIcon(MegaCalculator.class.getResource("resources/magnifying glass.png")));
		IMAGES.put("settings.png", new ImageIcon(MegaCalculator.class.getResource("resources/settings.png")));
		IMAGES.put("f(x).png", new ImageIcon(MegaCalculator.class.getResource("resources/f(x).png")));
		IMAGES.put("differential.png", new ImageIcon(MegaCalculator.class.getResource("resources/differential.png")));
		IMAGES.put("ddx.png", new ImageIcon(MegaCalculator.class.getResource("resources/ddx.png")));
		IMAGES.put("integral.png", new ImageIcon(MegaCalculator.class.getResource("resources/integral.png")));
		IMAGES.put("a.png", new ImageIcon(MegaCalculator.class.getResource("resources/a.png")));
		IMAGES.put("b.png", new ImageIcon(MegaCalculator.class.getResource("resources/b.png")));
		
		AUDIO = new HashMap<String, AudioClip>();
		AUDIO.put("error.wav", Applet.newAudioClip(MegaCalculator.class.getResource("resources/error.wav"))); 
		AUDIO.put("notify.wav", Applet.newAudioClip(MegaCalculator.class.getResource("resources/notify.wav"))); 
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

		notationsDialog = new MessageDialog(this, MessageDialog.NOTATIONS);
		aboutDialog = new MessageDialog(this, MessageDialog.ABOUT);

		switchAngle = new JButton("Radians");
		switchAngle.setFocusable(false);
		switchAngle.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				CalculatorInterface.degrees = !CalculatorInterface.degrees;
				switchAngle.setText((CalculatorInterface.degrees)? "Radians" : "Degrees");
				setCurrentView(current instanceof ImageView);
			}
        	
        });
		
		switchView = new JButton("Simple view");
		switchView.setFocusable(false);
		switchView.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				setCurrentView(current instanceof SimpleView);
			}
        	
        });
		
		JButton clear = new JButton("Clear");
		clear.setFocusable(false);
		clear.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				setCurrentView(current instanceof ImageView);
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setIconImages(Arrays.asList(IMAGES.get("cal1.png").getImage(), IMAGES.get("cal2.png").getImage()));
		setResizable(false);
		setCurrentView(true);
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
        buttons[0] = new JButton("Function Grapher", IMAGES.get("fg.png")); 
        buttons[1] = new JButton("Function Differentiator", IMAGES.get("fd.png")); 
        buttons[2] = new JButton("Function Integrator", IMAGES.get("fi.png")); 
        buttons[3] = new JButton("Linear Equations Solver", IMAGES.get("es.png")); 
        buttons[4] = new JButton("Matrix Calculator", IMAGES.get("mc.png")); 
        buttons[5] = new JButton("Unit Converter", IMAGES.get("uc.png")); 
        buttons[6] = new JButton("Notations", IMAGES.get("help.png"));
        buttons[7] = new JButton("About", IMAGES.get("info1.png"));
        buttons[8] = new JButton("Exit", IMAGES.get("exit.png"));
        
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

			public void actionPerformed(ActionEvent e) {
				new FunctionGrapher().setVisible(true);
			}
        	
        });
        buttons[1].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				new FunctionDifferentiator().setVisible(true);
			}
        	
        });
        buttons[2].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				new FunctionIntegrator().setVisible(true);
			}
        	
        });
        buttons[3].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				new LinearEquationsSolver().setVisible(true);
			}
        	
        });
        buttons[4].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				new MatrixCalculator().setVisible(true);
			}
        	
        });
        buttons[5].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				new UnitConverter().setVisible(true);
			}
        	
        });
        buttons[6].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				notationsDialog.setVisible(true);
			}
        	
        });
        buttons[7].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				aboutDialog.setVisible(true);
			}
        	
        });
        buttons[8].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
        	
        });
	}

	private void setCurrentView(boolean isImage) {
		CalculatorInterface newOne;
		if (isImage){
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
