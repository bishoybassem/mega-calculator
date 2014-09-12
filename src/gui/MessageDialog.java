package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class MessageDialog extends JDialog {
	
	public static final int ERROR = 0;
	public static final int RESULT = 1;
	public static final int ABOUT = 2; 
	
	private int type;
	
	public MessageDialog(JFrame main, int type) {
		this(main, null, type);
	}
	
	public MessageDialog(JFrame main, String message, int type) {
		super(main, (type == 0)? "Error" : ((type == 1)? "Result" : "About"), true);
		
		this.type = type;
		
		String fileName;
		if (type == 0) {
			fileName = "error.png";
		} else if (type == 1) {
			fileName = "info2.png";
		} else {
			fileName = "cal2.png";
			message = "Mega Calculator\nCreated by\nBishoy Bassem Morris";
		}
		
		JLabel image = new JLabel(MegaCalculator.images.get(fileName));
		JTextArea text = new JTextArea(message);
		text.setOpaque(false);
		text.setEditable(false);
		text.setFocusable(false);
		text.setBackground(new Color(238, 238, 238));
		
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		});
		ok.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "exit");
		ok.getActionMap().put("exit", new AbstractAction(){

			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
			
		});
		
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(0, 10, FlowLayout.LEFT));
		p1.setOpaque(false);
		p1.add(image);
		p1.add(text);
		p1.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
		
		JPanel p2 = new JPanel();
		p2.setOpaque(false);
		p2.add(ok);
		
		JPanel p3 = new JPanel() {
        	
        	public void paintComponent(Graphics g) {
        	    if (!isOpaque()) {
        	        super.paintComponent(g);
        	        return;
        	    }
        	    Graphics2D g2d = (Graphics2D) g;
        	    int w = getWidth();
        	    int h = getHeight();
        	    
        	    GradientPaint gp = new GradientPaint(w / 4, 0, Color.WHITE, w, 0, new Color(210, 210, 210));
        	    g2d.setPaint(Color.WHITE);
        	    g2d.fillRect(0, 0, w / 2, h);
        	    g2d.setPaint(gp);
        	    g2d.fillRect(w / 4, 0, w, h);
        	 
        	    setOpaque(false);
        	    super.paintComponent(g);
        	    setOpaque(true);
        	}
        	
        };
        p3.setLayout(new BorderLayout());
        p3.add(p1);
		p3.add(p2, BorderLayout.SOUTH);
		
		setResizable(false);
		add(p3);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void setVisible(boolean isVisible) {
		if (isVisible) {
			if (type == 0) {
				MegaCalculator.audio.get("error.wav").play();
			} else if (type == 1) {
				MegaCalculator.audio.get("notify.wav").play();
			}
		}
		super.setVisible(isVisible);
	}
		
}

