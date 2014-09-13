package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class MessageDialog extends JDialog {
	
	public static final int ERROR = 0;
	public static final int RESULT = 1;
	public static final int ABOUT = 2; 
	public static final int NOTATIONS = 3; 
	
	private int type;
	private static String aboutText;
	private static String notationsText;
	
	static {
		aboutText = readTextFile("resources/about.txt");
		notationsText = readTextFile("resources/notations.txt");
	}
	
	public MessageDialog(JFrame main, int type) {
		this(main, null, type);
	}
	
	public MessageDialog(JFrame main, String message, final int type) {
		super(main, true);
		this.type = type;

		String fileName = null;
		if (type == ERROR) {
			setTitle("Error");
			fileName = "error.png";
		} else if (type == RESULT) {
			setTitle("Result");
			fileName = "info2.png";
		} else if (type == ABOUT) {
			setTitle("About");
			message = aboutText;
			fileName = "cal2.png";
		} else {
			setTitle("Notations");
			message = notationsText;
		}
				
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
		
		JTextArea text = new JTextArea(message);
		text.setFocusable(false);
		if (type == NOTATIONS) {
			text.setRows(15);
			text.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 15));
		} else {
			text.setOpaque(false);
			text.setBackground(new Color(238, 238, 238));
		}
		
		JPanel p1 = new JPanel();
		p1.setOpaque(false);
		if (type == NOTATIONS) {
			JScrollPane scrollPane = new JScrollPane(text);
			scrollPane.setFocusable(false);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			
			p1.add(scrollPane);
			p1.setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
		} else {
			p1.setLayout(new FlowLayout(0, 10, FlowLayout.LEFT));
			p1.add(new JLabel(MegaCalculator.images.get(fileName)));
			p1.add(text);
			p1.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
		}
		
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
        	    
        	    if (type == NOTATIONS) {
        	    	GradientPaint gp1 = new GradientPaint(0, 0, new Color(210, 210, 210), w / 2, 0, Color.WHITE);
            	    g2d.setPaint(gp1);
            	    g2d.fillRect(0, 0, w / 2, h);
        	    	GradientPaint gp2 = new GradientPaint(w / 2, 0, Color.WHITE, w, 0, new Color(210, 210, 210));
            	    g2d.setPaint(gp2);
            	    g2d.fillRect(w / 2, 0, w, h);
        	    } else {
            	    g2d.setPaint(Color.WHITE);
            	    g2d.fillRect(0, 0, w / 2, h);
            	    GradientPaint gp = new GradientPaint(w / 4, 0, Color.WHITE, w, 0, new Color(210, 210, 210));
            	    g2d.setPaint(gp);
            	    g2d.fillRect(w / 4, 0, w, h);
        	    }
        	 
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
	
	public static String readTextFile(String path) {
		String text = "";
		Scanner sc = null;
		try {
			sc = new Scanner(MessageDialog.class.getResourceAsStream(path));
			while (sc.hasNext()){
				text += sc.nextLine();
				if (sc.hasNext()){
					text += "\n";
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (sc != null){
				sc.close();
			}	
		}
		return text;
	}
		
}

