package gui;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class NotationsDialog extends JDialog {
	
	private JTextArea textArea;
	private String notations;
	
	public NotationsDialog(JFrame main) {
		super(main, "Notations");
		
		notations = readFile("resources/notations.txt");
		
		textArea = new JTextArea(notations);
		textArea.setRows(15);
		textArea.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 15));
		textArea.setFocusable(false);
		
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

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setFocusable(false);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JPanel p1 = new JPanel();
		p1.setOpaque(false);
		p1.add(scrollPane);
		p1.setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
		
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
        p3.add(p1);
        p3.add(p2, BorderLayout.SOUTH);
        
        add(p3);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}
	
	public void refresh() {
		textArea.setText(notations);
		textArea.setCaretPosition(0);
	}
	
	public static String readFile(String path) {
		String text = "";
		Scanner sc = null;
		try {
			sc = new Scanner(NotationsDialog.class.getResourceAsStream(path));
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

