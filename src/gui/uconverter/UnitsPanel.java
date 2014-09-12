package gui.uconverter;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

import engine.units.AngleUnits;
import engine.units.AreaUnits;
import engine.units.LengthUnits;
import engine.units.MassUnits;
import engine.units.Prefix;
import engine.units.TemperatureUnits;
import engine.units.TimeUnits;
import engine.units.VolumeUnits;
import gui.MessageDialog;


@SuppressWarnings("serial")
public class UnitsPanel extends JPanel {
	
	private int type;
	
	private JFrame mainFrame;
	private JTextField quantity;
	private JTextField result;
	private JComboBox<Object> prefixes1;
	private JComboBox<Object> units1;
	private JComboBox<Object> prefixes2;
	private JComboBox<Object> units2;
	
	public UnitsPanel(JFrame mainFrame, int type){
		this.mainFrame = mainFrame;
		setType(type);
		
		prefixes1 = new JComboBox<Object>(Prefix.values());
		prefixes1.setEnabled(isEnabled(0));
		prefixes1.setFocusable(false);
		
		units1.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				prefixes1.setSelectedIndex(0);
				prefixes1.setEnabled(isEnabled(units1.getSelectedIndex()));
			}

		});
		units1.setFocusable(false);
		
		quantity = new JTextField(15);
		quantity.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "calculate");
		quantity.getActionMap().put("calculate", new AbstractAction(){

			public void actionPerformed(ActionEvent arg0) {
				calculate();
			}
			
		});
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 3));
		p1.add(quantity);
		p1.add(prefixes1);
		p1.add(units1);
		p1.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 1), "From"));
		
		prefixes2 = new JComboBox<Object>(Prefix.values());
		prefixes2.setEnabled(isEnabled(0));
		prefixes2.setFocusable(false);
		
		units2.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				prefixes2.setSelectedIndex(0);
				prefixes2.setEnabled(isEnabled(units2.getSelectedIndex()));
			}

		});
		units2.setFocusable(false);
		
		result = new JTextField(15);
		result.setEditable(false);
		result.setBorder(new LineBorder(Color.GRAY, 1));
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 3));
		p2.add(result);
		p2.add(prefixes2);
		p2.add(units2);
		p2.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 1), "To"));
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(p1);
		add(p2);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}
		
	public void calculate(){
		try {
			double q = Double.parseDouble(quantity.getText());
			Prefix p1;
			Prefix p2;
			if (prefixes1.isEnabled()){
				p1 = (Prefix) prefixes1.getSelectedItem();
			} else {
				p1 = Prefix.NONE;
			}
			if (prefixes2.isEnabled()){
				p2 = (Prefix) prefixes2.getSelectedItem();
			} else {
				p2 = Prefix.NONE;
			}
			double r = 0;			
			if (type == 0){
				r = LengthUnits.convert(q, p1, (LengthUnits) units1.getSelectedItem(), p2, (LengthUnits) units2.getSelectedItem());
			} else if (type == 1){
				r = AreaUnits.convert(q, p1, (AreaUnits) units1.getSelectedItem(), p2, (AreaUnits) units2.getSelectedItem());
			} else if (type == 2){
				r = VolumeUnits.convert(q, p1, (VolumeUnits) units1.getSelectedItem(), p2, (VolumeUnits) units2.getSelectedItem());
			} else if (type == 3){
				r = MassUnits.convert(q, p1, (MassUnits) units1.getSelectedItem(), p2, (MassUnits) units2.getSelectedItem());
			} else if (type == 4){
				r = TimeUnits.convert(q, p1, (TimeUnits) units1.getSelectedItem(), p2, (TimeUnits) units2.getSelectedItem());
			} else if (type == 5) {
				r = AngleUnits.convert(q, p1, (AngleUnits) units1.getSelectedItem(), p2, (AngleUnits) units2.getSelectedItem());
			} else if (type == 6) {
				r = TemperatureUnits.convert(q, (TemperatureUnits) units1.getSelectedItem(), (TemperatureUnits) units2.getSelectedItem());
			} 
			result.setText(r + "");
		} catch (Exception e){
			new MessageDialog(mainFrame, "Invalid input\nPlease check your input!", MessageDialog.ERROR).setVisible(true);
		}
	}
	
	public void clear(){
		quantity.setText("");
		result.setText("");
		setFocus();
	}
	
	private boolean isEnabled(int index) {
		if (index == 0){
			if (type != 1 && type != 5 && type != 6){
				return true;
			}
		}
		return false;
	}
	
	private void setType(int type) {
		if (type == 0){
			units1 = new JComboBox<Object>(LengthUnits.values());
			units2 = new JComboBox<Object>(LengthUnits.values());
		} else if (type == 1){
			units1 = new JComboBox<Object>(AreaUnits.values());
			units2 = new JComboBox<Object>(AreaUnits.values());
		} else if (type == 2){
			units1 = new JComboBox<Object>(VolumeUnits.values());
			units2 = new JComboBox<Object>(VolumeUnits.values());
		} else if (type == 3){
			units1 = new JComboBox<Object>(MassUnits.values());
			units2 = new JComboBox<Object>(MassUnits.values());
		} else if (type == 4){
			units1 = new JComboBox<Object>(TimeUnits.values());
			units2 = new JComboBox<Object>(TimeUnits.values());
		} else if (type == 5){
			units1 = new JComboBox<Object>(AngleUnits.values());
			units2 = new JComboBox<Object>(AngleUnits.values());
		} else if (type == 6){
			units1 = new JComboBox<Object>(TemperatureUnits.values());
			units2 = new JComboBox<Object>(TemperatureUnits.values());
		}
		this.type = type;
	}
	
	public void setFocus() {
		quantity.requestFocus();
	}
	
}
