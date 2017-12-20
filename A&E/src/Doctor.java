import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import java.awt.Font;


import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;

public class Doctor extends JFrame {

	private JPanel contentPane;
	private JTextField txtFirstname;
	private JTextField txtLastname;
	private JTextField txtAge;
	private JTextField txtPriority;
	private JTextField txtid;
	private JTextArea txtHealth;
	private JTextArea txtTreatment;
	
	Node n = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Doctor frame = new Doctor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public String stHealth = "";
	public String stAssessment = "";

	Connection connection = null;
			
	/**
	 * Create the frame.
	 */
	public Doctor() 
	{
		setTitle("Doctor's Panel");
		setResizable(false);
		ISaver dll = new DoublyLinkedList();

		ISaver dbconn = new DataBaseConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 627, 476);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(228, 45, 375, 170);
		contentPane.add(scrollPane);
		
		JTextArea txtOverallHealth = new JTextArea();
		txtOverallHealth.setBackground(new Color(230, 230, 250));
		txtOverallHealth.setEditable(false);
		scrollPane.setViewportView(txtOverallHealth);
		txtOverallHealth.addMouseListener(new MouseAdapter() {

		});
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(228, 262, 375, 170);
		contentPane.add(scrollPane_1);
		
		JTextArea txtTreat = new JTextArea();
		txtTreat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				txtTreat.setText("");
			}
		});
		scrollPane_1.setViewportView(txtTreat);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			private AbstractButton txtDocTreat;

			public void actionPerformed(ActionEvent e) 
			{
				try {	
						Patients p = new Patients();
				
					int tmp_id = Integer.parseInt(txtid.getText());
					String tmp_fname = txtFirstname.getText();
					String tmp_lname = txtLastname.getText();
					int tmp_age = Integer.parseInt(txtAge.getText());	
					String tmp_health = txtOverallHealth.getText();
					int tmp_priority = Integer.parseInt(txtPriority.getText());
					String tmp_Treat = "\n\tDiagnostics & Treatment\n ***********************\n" + txtTreat.getText() + "\n\n";
					
					
					p.GetId();
					p.SetFirstname(tmp_fname);
					p.SetLastname(tmp_lname);
					p.SetAGe(tmp_age);
					p.SetHealth(tmp_health);
					p.SetPriority(tmp_priority);
					p.SetNurseAssessment(tmp_health);
					p.SetTreatment(tmp_Treat);
					
					Connection connection = dbconn.UpdateDoctor(p, tmp_id);
					
					Connection changeTableConnection = ((DataBaseConnection) dbconn).ChangeTable(p, tmp_id);
					
					Connection deletePatientConnection  = ((DataBaseConnection) dbconn).Delete(p, tmp_id);
					
				
					JOptionPane.showMessageDialog(null, "Updated");			
				
					txtid.setText("");
					txtFirstname.setText("");
					txtLastname.setText("");
					txtAge.setText("");
					txtOverallHealth.setText("");
					txtPriority.setText("");
					txtTreat.setText("");
					
					
					String printing = ("Patient:  " +p.GetFirstname()+" "+p.GetLastname()+ "\nAge: " +p.GetAge()
					+ "\n" + p.GetNurseAssessment()+ "\t"+ p.GetTreatment()); 
					
					System.out.println(printing);
					
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, e1);
				}
			}
				
		});
		btnSubmit.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnSubmit.setBounds(70, 361, 80, 30);
		contentPane.add(btnSubmit);
		
		JButton btnNext = new JButton("==>");
		btnNext.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{				
				// My input
				try 
				{
					connection = dbconn.connectToDB();
					String query = "select * from Patients order by Priority desc, id asc";
					PreparedStatement pstmt = connection.prepareStatement(query);
					ResultSet rs = pstmt.executeQuery();
					
					while(rs.next())
					{
						Patients patient = new Patients();
						patient.SetId(rs.getInt("id"));
						patient.SetFirstname(rs.getString("Firstname"));
						patient.SetLastname(rs.getString("Lastname"));
						patient.SetAGe(rs.getInt("Age"));
						patient.SetHealth(rs.getString("Health"));						
						patient.SetPriority(rs.getInt("Priority"));
						patient.SetNurseAssessment(rs.getString("Assessment"));
						patient.SetTreatment(rs.getString("Treatment"));
						
						dll.Add(patient);
									
					}
					
					rs.close();
					dbconn.closeConnection();
				}
		
				catch(Exception e2) 
				{
						e2.printStackTrace();	
				}

				
				if(n == null || n.getNext() == null)
				{
					n = ((DoublyLinkedList) dll).getNode(1);
				}
				
				else
				{
					n = n.getNext();
				}
				
				Patients p = (Patients)n.getData();

				txtid.setText(Integer.toString(p.GetId()));
				txtFirstname.setText(p.GetFirstname());
				txtLastname.setText(p.GetLastname());
				txtAge.setText(Integer.toString(p.GetAge()));
				txtOverallHealth.setText(p.GetHealth()+p.GetNurseAssessment());
				txtPriority.setText(Integer.toString(p.GetPriority()));
				txtTreat.setText(p.GetTreatment());	
				
				stHealth = stHealth + p.GetHealth();
				stAssessment = p.GetNurseAssessment();
			
			}
				
		});
		btnNext.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnNext.setBounds(70, 402, 80, 30);
		contentPane.add(btnNext);
		
		txtFirstname = new JTextField();
		txtFirstname.setBackground(new Color(230, 230, 250));
		txtFirstname.setEditable(false);
		txtFirstname.setBounds(98, 133, 110, 25);
		contentPane.add(txtFirstname);
		txtFirstname.setColumns(10);
		
		JLabel lblHealthOverview = new JLabel("Health Overview");
		lblHealthOverview.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblHealthOverview.setBounds(350, 28, 114, 14);
		contentPane.add(lblHealthOverview);
		
		JLabel lblFirstname = new JLabel("Firstname :");
		lblFirstname.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblFirstname.setBounds(20, 137, 81, 14);
		contentPane.add(lblFirstname);
		
		JLabel lblLastname = new JLabel("Lastname :");
		lblLastname.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblLastname.setBounds(22, 173, 71, 14);
		contentPane.add(lblLastname);
		
		JLabel lblAge = new JLabel("Age :");
		lblAge.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblAge.setBounds(59, 209, 42, 14);
		contentPane.add(lblAge);
		
		JLabel lblPriorityNo = new JLabel("Priority No. :");
		lblPriorityNo.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPriorityNo.setBounds(12, 245, 81, 14);
		contentPane.add(lblPriorityNo);
		
		txtLastname = new JTextField();
		txtLastname.setBackground(new Color(230, 230, 250));
		txtLastname.setEditable(false);
		txtLastname.setBounds(98, 169, 110, 25);
		contentPane.add(txtLastname);
		txtLastname.setColumns(10);
		
		txtAge = new JTextField();
		txtAge.setBackground(new Color(230, 230, 250));
		txtAge.setEditable(false);
		txtAge.setBounds(98, 205, 110, 25);
		contentPane.add(txtAge);
		txtAge.setColumns(10);
		
		txtPriority = new JTextField();
		txtPriority.setBackground(new Color(230, 230, 250));
		txtPriority.setEditable(false);
		txtPriority.setBounds(98, 241, 110, 25);
		contentPane.add(txtPriority);
		txtPriority.setColumns(10);
		
		txtid = new JTextField();
		txtid.setEditable(false);
		txtid.setBackground(new Color(230, 230, 250));
		txtid.setBounds(98, 99, 110, 25);
		contentPane.add(txtid);
		txtid.setColumns(10);
		
		JLabel lblTreatment = new JLabel("Treatment");
		lblTreatment.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblTreatment.setBounds(389, 245, 75, 14);
		contentPane.add(lblTreatment);
		
		JLabel lblId = new JLabel("Id :");
		lblId.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblId.setBounds(70, 103, 30, 14);
		contentPane.add(lblId);
	}
}
