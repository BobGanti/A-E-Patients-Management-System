import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DropMode;

public class WelcomeFrame {

	private JFrame frmWelcomeWindow;
	private JTextField txtWelcome;
	private JTextField lblDisplay;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeFrame window = new WelcomeFrame();
					window.frmWelcomeWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WelcomeFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWelcomeWindow = new JFrame();
		frmWelcomeWindow.setTitle("Welcome Window");
		frmWelcomeWindow.setResizable(false);
		frmWelcomeWindow.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmWelcomeWindow.setBackground(Color.LIGHT_GRAY);
		frmWelcomeWindow.setBounds(100, 100, 450, 300);
		frmWelcomeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWelcomeWindow.getContentPane().setLayout(null);
		
		JLabel label = new JLabel(" (Reception, Nurse, Doctor)");
		label.setBounds(226, 73, 158, 24);
		frmWelcomeWindow.getContentPane().add(label);
		
		txtWelcome = new JTextField();
		txtWelcome.setDropMode(DropMode.INSERT);
		txtWelcome.setBounds(111, 95, 196, 30);
		frmWelcomeWindow.getContentPane().add(txtWelcome);
		txtWelcome.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Enter Staff Category\r\n");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(88, 72, 172, 24);
		frmWelcomeWindow.getContentPane().add(lblNewLabel);
		
	//****************************************************************************************	
		JButton btnOKWelcome = new JButton("OK");
		btnOKWelcome.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String tmp_staff_category = txtWelcome.getText();
					
				if(tmp_staff_category.toUpperCase().equals("RECEPTION") || tmp_staff_category.toUpperCase().equals("RECEPTIONIST"))
				{
					ReceptionPanel recP = new ReceptionPanel();
					
					recP.setVisible(true);		
					
					frmWelcomeWindow.dispose();
				}
				else if(tmp_staff_category.toUpperCase().equals("NURSE"))
				{
					NursePanel2 nursepanel = new NursePanel2();					
					nursepanel.setVisible(true);					
					frmWelcomeWindow.dispose();
				}
				
				else if(tmp_staff_category.toUpperCase().equals("DOCTOR"))
					{
						Doctor doctor = new Doctor();
						doctor.setVisible(true);
						frmWelcomeWindow.dispose();
					}
				
				else
				{
					String Message = "Please, stick to the rules.\n You"+"'"+"re either a Receptionist, a Nurse or a Doctor."
							+ "\nCharacter not case sentitive, so don't worry";
					JOptionPane.showMessageDialog(null, Message);
				}
				
				lblDisplay.setText(tmp_staff_category);
				
		
			}
		});
		btnOKWelcome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnOKWelcome.setBounds(171, 145, 89, 23);
		frmWelcomeWindow.getContentPane().add(btnOKWelcome);
		
		JLabel lblNewLabel_1 = new JLabel("Welcome to the Emergency Department");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_1.setBounds(45, 0, 354, 50);
		frmWelcomeWindow.getContentPane().add(lblNewLabel_1);
	}
}
