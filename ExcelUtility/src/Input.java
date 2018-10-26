import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.sourceforge.jdatepicker.DateModel;
import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import net.sourceforge.jdatepicker.util.JDatePickerUtil;

public class Input extends JFrame implements ActionListener {
	String message = "";
	JPanel panel = new JPanel();
	static JTextField planField = new JTextField(40);
	static JTextField endDateField = new JTextField(10);
	
	UtilDateModel model = new UtilDateModel();
    JDatePanelImpl datePanel = new JDatePanelImpl(model);
    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
	static JButton button = new JButton("Submit");
	static JFrame jf = new JFrame();
	public void method(){
		
        button.addActionListener(new Input());
        planField.setName("Plan Name");
        endDateField.setName("EndDate");
        
        jf.setSize(500, 500);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(panel);
        panel.add(planField);
        panel.add(endDateField);
        panel.add(datePicker);
        panel.add(button);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Worker work = new Worker();
		try {
			List<String[]> schema = work.ReadSchemaFile("D:\\jars\\Input.xlsx", "Sheet1");
			work.plan = planField.getText();
			work.date = endDateField.getText();
			work.date = datePicker.getModel().getValue().toString();
			System.out.println(work.date);
			message = message + work.Execute(schema);
			
		} catch (InvalidFormatException e) {
			message = message+e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			message = message+e.getMessage();
			e.printStackTrace();
		} catch (Exception e) {
			message = message+e.getMessage();
			e.printStackTrace();
		}
		JFrame fr = new JFrame();
		fr.setSize(500, 500);
        fr.setVisible(true);
		fr.add(new JTextArea(message));
		
	}

}
