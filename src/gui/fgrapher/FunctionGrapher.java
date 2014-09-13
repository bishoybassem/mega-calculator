package gui.fgrapher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import engine.Graph;
import gui.MegaCalculator;
import gui.MessageDialog;

@SuppressWarnings("serial")
public class FunctionGrapher extends JFrame {
	
	private DrawingPanel drawingPanel;
	private JTextField function;
	private JTextField a;
	private JTextField b;
	private JDialog functionDialog;
	private JDialog viewsDialog;
	private JDialog rangesDialog;
	private JButton[] buttons;
	
	private ArrayList<Graph> graphs;
	
	public FunctionGrapher(){
		super("Function Grapher");
		drawingPanel = new DrawingPanel();

		setIconImage(MegaCalculator.images.get("fg.png").getImage());
		setToolBar();
		setFunctionDialog();
		setViewsDialog();
		setRangesDialog();
		graphs = new ArrayList<Graph>();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		add(drawingPanel);
		pack();
		setLocationRelativeTo(null);
	}	
	
	private void setToolBar(){
		JToolBar toolBar = new JToolBar(JToolBar.VERTICAL){
			
			public void paintComponent(Graphics g) { 
			    super.paintComponent(g);
			    Graphics2D g2d = (Graphics2D) g; 
			    int w = getWidth();
			    int h = getHeight();
			    GradientPaint gp = new GradientPaint(0, 0, Color.GRAY.brighter(), w, 0, Color.WHITE);
			    g2d.setPaint(gp);
			    g2d.fillRect(0, 0, w, h);
			}
			
		};
		toolBar.setFloatable(false);
        
        JButton add = createButton("graph.png", "Add new function");
        add.setOpaque(false);
        add.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				functionDialog.setVisible(true);
				functionDialog.setLocationRelativeTo(null);
			}
        	
        });
        
        JButton erase = createButton("eraser.png", "Clear");
        erase.setOpaque(false);
        erase.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				graphs = new ArrayList<Graph>();
				drawingPanel.clear();
				clickButton(0);
			}
        	
        });
        
        JButton customize = createButton("settings.png", "Customize axes");
        customize.setOpaque(false);
        customize.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				rangesDialog.setVisible(true);
				rangesDialog.setLocationRelativeTo(null);
			}
        	
        });
        
        JButton view = createButton("magnifying glass.png", "Change current view");
        view.setOpaque(false);
        view.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				viewsDialog.setVisible(true);
				viewsDialog.setLocationRelativeTo(null);
			}
        	
        });
        
        JButton capture = createButton("camera.png", "Capture screen");
        capture.setOpaque(false);
        capture.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				saveImage();
			}
        	
        });
        
        toolBar.addSeparator(new Dimension(0, 5));
        toolBar.add(add);
        toolBar.addSeparator(new Dimension(0, 5));
        toolBar.add(erase);
        toolBar.addSeparator(new Dimension(0, 5));
        toolBar.add(view);
        toolBar.addSeparator(new Dimension(0, 5));
        toolBar.add(customize);
        toolBar.addSeparator(new Dimension(0, 5));
        toolBar.add(capture);
        
        add(toolBar, BorderLayout.WEST);
	}
	
	private void setFunctionDialog() {
		function = new JTextField(20);
		function.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "graph");
		function.getActionMap().put("graph", new AbstractAction(){

			public void actionPerformed(ActionEvent e) {
				graph(false);
			}
			
		});
		
		JButton graph = new JButton("Graph \u0083(x)");
		graph.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				graph(false);
			}
			
		});
		graph.setFocusable(false);
		
		JButton graph1stDer = new JButton("Graph \u0083'(x)");
		graph1stDer.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				graph(true);
			}
			
		});
		graph1stDer.setFocusable(false);
		
		JPanel p1 = new JPanel();
		p1.setOpaque(false);
		p1.add(new JLabel(MegaCalculator.images.get("f(x).png")));
		p1.add(new JLabel(" = "));
		p1.add(function);
		
		JPanel p2 = new JPanel();
		p2.setOpaque(false);
		p2.add(graph);
		p2.add(graph1stDer);
		
		JPanel p3 = new JPanel() {
        	
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
        p3.setLayout(new BorderLayout());
        p3.add(p1, BorderLayout.NORTH);
		p3.add(p2, BorderLayout.SOUTH);
		
		functionDialog = new JDialog(this, "Graph function");
		functionDialog.setIconImage(getIconImage());
		functionDialog.setLayout(new BorderLayout());
		functionDialog.setResizable(false);
		functionDialog.add(p3);
		functionDialog.pack();
	}
		
	private void setViewsDialog() {
		buttons = new JButton[9];
		buttons[0] = new JButton(MegaCalculator.images.get("AllQuads.png"));
		buttons[1] = new JButton(MegaCalculator.images.get("1stQuad.png"));
		buttons[2] = new JButton(MegaCalculator.images.get("2ndQuad.png"));
		buttons[3] = new JButton(MegaCalculator.images.get("3rdQuad.png"));
		buttons[4] = new JButton(MegaCalculator.images.get("4thQuad.png"));
		buttons[5] = new JButton(MegaCalculator.images.get("1st2ndQuad.png"));
		buttons[6] = new JButton(MegaCalculator.images.get("2nd3rdQuad.png"));
		buttons[7] = new JButton(MegaCalculator.images.get("3rd4thQuad.png"));
		buttons[8] = new JButton(MegaCalculator.images.get("4th1stQuad.png"));

		buttons[0].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				clickButton(0);			
			}
			
		});
		buttons[1].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				clickButton(1);
			}
			
		});
		buttons[2].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				clickButton(2);
			}
			
		});
		buttons[3].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				clickButton(3);
			}
			
		});
		buttons[4].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				clickButton(4);
			}
			
		});
		buttons[5].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				clickButton(5);
			}
			
		});
		buttons[6].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				clickButton(6);
			}
			
		});
		buttons[7].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				clickButton(7);
			}
			
		});
		buttons[8].addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				clickButton(8);
			}
			
		});
		
		JPanel p1 = new JPanel();
		p1.setOpaque(false);
		
		JPanel p2 = new JPanel();
		p2.setOpaque(false);
		
		for (int i = 0; i < buttons.length; i++){
			buttons[i].setBorder(new LineBorder(Color.BLACK, 1));
			if (i < 5){
				p1.add(buttons[i]);
			} else {
				p2.add(buttons[i]);
			}
		}	
		buttons[0].setEnabled(false);
		
		JPanel p3 = new JPanel() {
        	
        	public void paintComponent(Graphics g) {
        	    if (!isOpaque()) {
        	        super.paintComponent(g);
        	        return;
        	    }
        	    Graphics2D g2d = (Graphics2D) g;
        	    int w = getWidth();
        	    int h = getHeight();
        	    
        	    GradientPaint gp1 = new GradientPaint(0, 0, new Color(210, 210, 210), 0, h / 2, Color.WHITE);
        	    GradientPaint gp2 = new GradientPaint(0, h / 2, Color.WHITE, 0, h, new Color(210, 210, 210));
        	    g2d.setPaint(gp1);
        	    g2d.fillRect(0, 0, w, h / 2);
        	    g2d.setPaint(gp2);
        	    g2d.fillRect(0, h / 2, w, h);
        	 
        	    setOpaque(false);
        	    super.paintComponent(g);
        	    setOpaque(true);
        	}
        	
        };
        p3.setLayout(new BorderLayout());
        p3.add(p1, BorderLayout.NORTH);
		p3.add(p2, BorderLayout.SOUTH);
		
		viewsDialog = new JDialog(this, "Views");
		viewsDialog.setIconImage(getIconImage());
		viewsDialog.setLayout(new BorderLayout());
		viewsDialog.setResizable(false);
		viewsDialog.add(p3);
		viewsDialog.pack();		
	}
	
	private void setRangesDialog() {
		a = new JTextField("10", 4);
		a.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "set");
		a.getActionMap().put("set", new AbstractAction(){

			public void actionPerformed(ActionEvent e) {
				changeRanges();
			}
			
		});
		
		b = new JTextField("10", 4);
		b.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "set");
		b.getActionMap().put("set", new AbstractAction(){

			public void actionPerformed(ActionEvent e) {
				changeRanges();
			}
			
		});
		
		JButton set = new JButton("Set");
		set.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
				changeRanges();
			}
			
		});
		set.setFocusable(false);
		
		JLabel la = new JLabel("X-Axis range  = ");
		
		JLabel lb = new JLabel("Y-Axis range  = ");
		
		JPanel p1 = new JPanel();
		p1.setOpaque(false);
		p1.add(la);
		p1.add(a);
		
		JPanel p2 = new JPanel();
		p2.setOpaque(false);
		p2.add(lb);
		p2.add(b);
		
		JPanel p3 = new JPanel();
		p3.setOpaque(false);
		p3.add(set);
		
		JPanel p4 = new JPanel() {
        	
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
        p4.setLayout(new BorderLayout());
        p4.add(p1, BorderLayout.NORTH);
        p4.add(p2);
		p4.add(p3, BorderLayout.SOUTH);
		
		rangesDialog = new JDialog(this, "Ranges");
		rangesDialog.setIconImage(getIconImage());
		rangesDialog.setLayout(new BorderLayout());
		rangesDialog.setResizable(false);
		rangesDialog.add(p4);
		rangesDialog.pack();
	}
	
	private JButton createButton(String fileName, String toolTipText) {
		JButton button = new JButton(MegaCalculator.images.get(fileName)){
			
			public Point getToolTipLocation(MouseEvent event) { 
        		return new Point(event.getX() + 10, event.getY() + 20); 
       		}
			
		};
		button.setToolTipText(toolTipText);
		button.setFocusable(false);
		return button;
	}
	
	private void clickButton(int n) {
		for (int i = 0; i < buttons.length; i++){
			buttons[i].setEnabled(i != n);
		}
		drawingPanel.setView(n);	
	}
	
	private void changeRanges() {
		boolean flag = false;
		try {
			int x = Integer.parseInt(a.getText());
			if (x <= 0)
				throw new IllegalArgumentException();
			
			flag = true;
			int y = Integer.parseInt(b.getText());
			if (y <= 0)
				throw new IllegalArgumentException();
			
			if (x != drawingPanel.getXRange()){
				ArrayList<Point2D[]> points = new ArrayList<Point2D[]>();
				for (int i = 0; i < graphs.size(); i++){
					points.add(graphs.get(i).getPoints(-x, x, 1200));
				}
				drawingPanel.setGraphs(points);
				drawingPanel.setXRange(x);
			}
			if (y != drawingPanel.getYRange()){
				drawingPanel.setYRange(y);
			}
		} catch (Exception ex){
			new MessageDialog(this, "Invalid range\nPlease check your input!", MessageDialog.ERROR).setVisible(true);
			a.setText(drawingPanel.getXRange() + "");
			b.setText(drawingPanel.getYRange() + "");
			if (flag){
				b.requestFocus();
			} else {
				a.requestFocus();
			}
		}
	}
	
	private void graph(boolean isDerivative) {
		try {
			Graph g = new Graph(function.getText(), isDerivative);
			drawingPanel.addGraph(g.getPoints(-drawingPanel.getXRange(), drawingPanel.getXRange(), 1200));
			graphs.add(g);
		} catch (RuntimeException ex){
			new MessageDialog(this, "Invalid function\nPlease check your input!", MessageDialog.ERROR).setVisible(true);
		}
	}
	
	private void saveImage() {
		JFileChooser jfc = new JFileChooser();
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.addChoosableFileFilter(new FileNameExtensionFilter("JPG files", "jpg"));
		jfc.setSelectedFile(new File(new SimpleDateFormat("dd-MM HH-mm-ss").format(new Date()) + ".jpg"));

		int result = jfc.showSaveDialog(this);
		if (result == JFileChooser.CANCEL_OPTION || result == JFileChooser.ERROR_OPTION)
			return;

		File file;
		if (!jfc.getSelectedFile().getName().endsWith(".jpg")){
			file = new File(jfc.getSelectedFile() + ".jpg");
		} else {
			file = jfc.getSelectedFile();
		}
	    
	    BufferedImage bi = drawingPanel.capture();
		try {
			ImageIO.write(bi , "jpg", file);
		} catch (Exception ex) {
			new MessageDialog(this, "Image cannot be saved\nPlease try again!", MessageDialog.ERROR).setVisible(true);
		}
	}
	
}
