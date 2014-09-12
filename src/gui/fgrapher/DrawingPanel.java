package gui.fgrapher;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel{
	
	private ArrayList<Point2D[]> graphs;
	private Color[] colors;
	private int[][] distances;
	private int lastView;
	private int currentView;
	private int xRange;
	private int yRange;
	private double l2o;
	private double n2o;
	private boolean animate;
	private Timer animator;

	public DrawingPanel() {
		super();
		l2o = 300;
		n2o = 300;
		xRange = 10;
		yRange = 10;
		graphs = new ArrayList<Point2D[]>();
		
		colors = new Color[]{Color.RED, Color.BLUE, new Color(34, 177, 76), Color.MAGENTA, Color.ORANGE};
		distances = new int[][]{
				{300, 300},
				{0, 600},
				{600, 600},
				{600, 0},
				{0, 0},
				{300, 600},
				{600, 300},
				{300, 0},
				{0, 300}
		};
		
		animator = new Timer(25, new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				animate = !animate();
				if (!animate){
					animator.stop();
				}
				repaint();
			}
			
		});
		
		setBackground(new Color (238, 238, 238));
	}
	
	private void drawAxis(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.draw(new Line2D.Double(l2o + 30, 30, l2o + 30, 630));
		g2d.draw(new Line2D.Double(30, n2o + 30, 630, n2o + 30));
	}
	
	private void drawLines(Graphics2D g2d) {
		g2d.setColor(new Color(220, 220, 220));
		g2d.drawRect(30, 30, 600, 600);
		for (double i = l2o % 30; i < 600; i += 30){
			if (i != l2o){
				g2d.draw(new Line2D.Double(i + 30, 30, i + 30, 630));
			}
		}
		for (double i = n2o % 30; i < 600; i += 30){
			if (i != n2o){
				g2d.draw(new Line2D.Double(30, i + 30, 630, i + 30));
			}
		}
	}
	
	private void drawDashes(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		for (double i = l2o % 30; i <= 600; i += 30){
			if (i != l2o){
				g2d.draw(new Line2D.Double(i + 30, n2o - 3 + 30, i + 30, n2o + 3 + 30));
			}
		}
		for (double i = n2o % 30; i <= 600; i += 30){
			if (i != n2o){
				g2d.draw(new Line2D.Double(l2o - 3 + 30, i + 30, l2o + 3 + 30, i + 30));
			}
		}
	}
	
	private void drawPoints(Graphics2D g2d , double x, double y) {
		for (int i = 0; i < graphs.size(); i++){
			g2d.setColor(colors[i % colors.length]);
			Point2D[] points = graphs.get(i);
			double p1x = 0;
			double p1y = 0;
			double p2x = 0;
			double p2y = 0;
			int start;
			int end;
			if (currentView == 0 || currentView == 5 || currentView == 7){
				start = 0;
				end = points.length - 1;
			} else if (currentView == 1 || currentView == 4 || currentView == 8){
				start = (points.length - 1) / 2;
				end = points.length - 1;
			} else {
				start = 0;
				end = (points.length - 1) / 2;
			}
			for (int j = start; j < end; j++){
				if (points[j] != null && points[j + 1] != null){
					if (currentView == 0){
						p1x = (points[j].getX() + xRange) * x;
						p1y = 600 - (points[j].getY() + yRange) * y;
						p2x = (points[j + 1].getX() + xRange) * x;
						p2y = 600 - (points[j + 1].getY() + yRange) * y;
					} else if (currentView == 1){
						p1x = points[j].getX() * x;
						p1y = 600 - points[j].getY() * y;
						p2x = points[j + 1].getX() * x;
						p2y = 600 - points[j + 1].getY() * y;
					} else if (currentView == 2){
						p1x = 600 + points[j].getX() * x;
						p1y = 600 - points[j].getY() * y;
						p2x = 600 + points[j + 1].getX() * x;
						p2y = 600 - points[j + 1].getY() * y;
					} else if (currentView == 3){
						p1x = 600 + points[j].getX() * x;
						p1y = points[j].getY() * -y;
						p2x = 600 + points[j + 1].getX() * x;
						p2y = points[j + 1].getY() * -y;
					} else if (currentView == 4){
						p1x = points[j].getX() * x;
						p1y = points[j].getY() * -y;
						p2x = points[j + 1].getX() * x;
						p2y = points[j + 1].getY() * -y;
					} else if (currentView == 5){
						p1x = (points[j].getX() + xRange) * x;
						p1y = 600 - points[j].getY() * y;
						p2x = (points[j + 1].getX() + xRange) * x;
						p2y = 600 - points[j + 1].getY() * y;
					} else if (currentView == 6){
						p1x = 600 + points[j].getX() * x;
						p1y = 600 - (points[j].getY() + yRange) * y;
						p2x = 600 + points[j + 1].getX() * x;
						p2y = 600 - (points[j + 1].getY() + yRange) * y;
					} else if (currentView == 7){
						p1x = (points[j].getX() + xRange) * x;
						p1y = points[j].getY() * -y;
						p2x = (points[j + 1].getX() + xRange) * x;
						p2y = points[j + 1].getY() * -y;
					} else if (currentView == 8){
						p1x = points[j].getX() * x;
						p1y = 600 - (points[j].getY() + yRange) * y;
						p2x = points[j + 1].getX() * x;
						p2y = 600 - (points[j + 1].getY() + yRange) * y;
					}
					g2d.draw(new Line2D.Double(p1x + 30, p1y + 30, p2x + 30, p2y + 30));
				}
			}
		}
		
	}
	
	private void drawXNumbers(Graphics2D g2d, FontMetrics fm, double x, double y, boolean below) {
		g2d.setColor(Color.BLACK);
		double scale = 0;
		String n;
		boolean flag = false;
		int stringY = (int) (n2o + 30 + (below ? fm.getAscent() : - fm.getAscent() / 2));
		for (int i = (int) l2o + 30; i <= 600; i += 30){
			scale += 30.0 / x;
			n = String.format("%.1f", scale);
			if (flag){
				g2d.drawString(n, i - (fm.stringWidth(n) / 2) + 30, stringY);
			}
			flag = !flag;
		}
		scale = 0;
		for (int i = (int) l2o - 30; i >= 0; i -= 30){
			scale -= 30.0 / x;
			n = String.format("%.1f", scale);
			if (flag){
				g2d.drawString(n, i - (fm.stringWidth(n) / 2) + 30, stringY);
			}
			flag = !flag;
		}
	}
	
	private void drawYNumbers(Graphics2D g2d, FontMetrics fm, double x, double y, boolean before) {
		g2d.setColor(Color.BLACK);
		double scale = 0;
		String n;
		boolean flag = false;
		int stringX;
		for (int i = (int) n2o + 30; i <= 600; i += 30){
			scale -= 30.0 / y;
			n = String.format("%.1f", scale);
			stringX = (int) (l2o + 30 + (before ? - fm.stringWidth(n) - 10 : 10));
			if (flag){
				g2d.drawString(n, stringX, i + (fm.getAscent() / 2) + 30);
			}
			flag = !flag;
		}
		scale = 0;
		for (int i = (int) n2o - 30; i >= 0; i -= 30){
			scale += 30.0 / y;
			n = String.format("%.1f", scale);
			stringX = (int) (l2o + 30 + (before ? - fm.stringWidth(n) - 10 : 10));
			if (flag){
				g2d.drawString(n, stringX, i + (fm.getAscent() / 2) + 30);
			}
			flag = !flag;
		}
	}
		
	private void trim(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 660, 30);
		g2d.fillRect(0, 0, 30, 660);
		g2d.fillRect(631, 0, 30, 660);
		g2d.fillRect(0, 631, 660, 30);
	}
	
	public void setView(int view) {
		lastView = currentView;
		currentView = view;
		l2o = distances[lastView][0];
		n2o = distances[lastView][1];
		animator.start();
	}
	
	public void reset() {
		l2o = 300;
		n2o = 300;
		xRange = 10;
		yRange = 10;
		graphs = new ArrayList<Point2D[]>();
		repaint();
	}
	
	public void clear() {
		graphs = new ArrayList<Point2D[]>();
	}
	
	public void setGraphs(ArrayList<Point2D[]> graphs) {
		this.graphs = graphs;
	}

	public void addGraph(Point2D[] points) {
		graphs.add(points);
		repaint();
	}
	
	private boolean animate() {
		boolean xMoved = false;
		boolean yMoved = false;
		if (l2o > distances[currentView][0]){
			l2o -= 15;
		} else if (l2o < distances[currentView][0]){
			l2o += 15;
		} else {
			xMoved = true;
		}
		
		if (n2o > distances[currentView][1]){
			n2o -= 15;
		} else if (n2o < distances[currentView][1]){
			n2o += 15;
		} else {
			yMoved = true;
		}
		return xMoved && yMoved;
	}
	
	public void setXRange(int xRange) {
		this.xRange = xRange;
		repaint();
	}
	
	public void setYRange(int yRange) {
		this.yRange = yRange;
		repaint();
	}
	
	public int getXRange() {
		return xRange;
	}
	
	public int getYRange() {
		return yRange;
	}
	
	public BufferedImage capture() {
		BufferedImage bi = new BufferedImage(660, 660, BufferedImage.TYPE_INT_RGB);
		paintComponent(bi.createGraphics());
		
		BufferedImage resized = new BufferedImage(1320, 1320, BufferedImage.TYPE_INT_RGB);
		resized.getGraphics().drawImage(bi, 0, 0, 1320, 1320, null);
		return resized;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(new Font("sansserif", Font.PLAIN, 17));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		double x = 600.0 / xRange;
		double y = 600.0 / yRange;
		FontMetrics fm = g.getFontMetrics();
		
		drawLines(g2d);
		drawDashes(g2d);

		if (animate){
			trim(g2d);
		} else if (currentView == 0){
			x = 300.0 / xRange;
			y = 300.0 / yRange;
			drawPoints(g2d, x, y);
			trim(g2d);
			drawXNumbers(g2d, fm, x, y, true);
			drawYNumbers(g2d, fm, x, y, false);
		} else if (currentView < 5){
			drawPoints(g2d, x, y);
			trim(g2d);
			if (currentView == 1){
				drawXNumbers(g2d, fm, x, y, true);
				drawYNumbers(g2d, fm, x, y, false);
			} else if (currentView == 2){
				drawXNumbers(g2d, fm, x, y, true);
				drawYNumbers(g2d, fm, x, y, true);
			} else if (currentView == 3){
				drawXNumbers(g2d, fm, x, y, false);
				drawYNumbers(g2d, fm, x, y, true);
			} else if (currentView == 4){
				drawXNumbers(g2d, fm, x, y, false);
				drawYNumbers(g2d, fm, x, y, false);
			}
		} else if (currentView == 5 || currentView == 7){
			x = 300.0 / xRange;
			y = 600.0 / yRange;
			drawPoints(g2d, x, y);
			trim(g2d);
			if (currentView == 5){
				drawXNumbers(g2d, fm, x, y, true);
				drawYNumbers(g2d, fm, x, y, false);
			} else {
				drawXNumbers(g2d, fm, x, y, false);
				drawYNumbers(g2d, fm, x, y, false);
			}
		} else {
			x = 600.0 / xRange;
			y = 300.0 / yRange;
			drawPoints(g2d, x, y);
			trim(g2d);
			if (currentView == 6){
				drawXNumbers(g2d, fm, x, y, true);
				drawYNumbers(g2d, fm, x, y, true);
			} else {
				drawXNumbers(g2d, fm, x, y, true);
				drawYNumbers(g2d, fm, x, y, false);
			}
		}
		drawAxis(g2d);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(660, 660);
	}
	
}
