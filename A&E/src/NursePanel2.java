

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NursePanel2 extends JFrame {

	private JPanel contentPane;
	private JTable table;
	
	public String OverallHealth = " ";
	Node n = null;
	
		
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NursePanel2 frame = new NursePanel2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;
	private JTextField txtSearch;
	private JTextField txtFirstname;
	private JTextField txtLastname;
	private JTextField txtid;
	private JLabel lblid;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_3;
	private JTextArea txtAssessment;
	private JLabel lblPriorityNo;
	private JTextField txtPriority;
	private JTextField txtAge;
	private JScrollPane scrollPane;
	private JTextField txtQueue;
	
	public void GetJTable()
	{
		try 
		{
			String query = "select * from Patients";
			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch(Exception e2) 
		{
			e2.printStackTrace();
		}
	}

	
	/**
	 * Create the frame.
	 */
	public NursePanel2() 
	{
		setTitle("Nurse Panel");
		ISaver dbconn = new DataBaseConnection();
		DoublyLinkedList dll = new DoublyLinkedList();
		
		connection = dbconn.connectToDB();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 738, 491);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(220, 220, 220));
		contentPane.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.BLACK, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLoadPatients = new JButton("Refresh");
		btnLoadPatients.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnLoadPatients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GetJTable();
				
			}
		});
		btnLoadPatients.setBounds(309, 13, 296, 32);
		contentPane.add(btnLoadPatients);
		
		JScrollPane tblLoadscrollPane = new JScrollPane();
		tblLoadscrollPane.setBackground(new Color(240, 255, 255));
		tblLoadscrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		tblLoadscrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		tblLoadscrollPane.addMouseListener(new MouseAdapter() {

		});
		
		tblLoadscrollPane.setBounds(247, 55, 450, 150);
		contentPane.add(tblLoadscrollPane);
		
		table = new JTable();
		table.setForeground(new Color(255, 0, 255));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				// When an item in JTable is clicked
				try 
				{
					int row = table.getSelectedRow();
					String id_ = (table.getModel().getValueAt(row, 0)).toString();
					String query = "select * from Patients where id = '"+id_+"'";
					PreparedStatement pstmt = connection.prepareStatement(query);
					ResultSet rs = pstmt.executeQuery();
					
					while(rs.next())
					{
						txtid.setText(rs.getString("id"));
						txtFirstname.setText(rs.getString("Firstname"));
						txtLastname.setText(rs.getString("Lastname"));
						txtAge.setText(rs.getString("Age"));
						txtAssessment.setText(rs.getString("Health"));
						//txtHealth.setText(rs.getString("Health"));
					}
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
				}
				catch(Exception e2) 
				{
					e2.printStackTrace();	
				}
			}
		});
		tblLoadscrollPane.setViewportView(table);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBackground(Color.GRAY);
		btnSubmit.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {	
					
					Patients p = new Patients();
					
					int tmp_id = Integer.parseInt(txtid.getText());
					String tmp_fname = txtFirstname.getText();
					String tmp_lname = txtLastname.getText();
					int tmp_age = Integer.parseInt(txtAge.getText());					
					String tmp_assessment = "\n\tNurse Assessment)\n **********************************************************\n" + txtAssessment.getText() + "\n\n";
					int tmp_priority_1 = Integer.parseInt(txtPriority.getText());
					
					boolean isValidPriority = p.SetPriority(tmp_priority_1);  // Get Priority range validation
					if(isValidPriority)
					{
						int tmp_priority = tmp_priority_1;
						
						p.GetId();
						p.SetFirstname(tmp_fname);
						p.SetLastname(tmp_lname);
						p.SetAGe(tmp_age);
						p.SetNurseAssessment(tmp_assessment);
						
						Connection connection = dbconn.UpdateNurse(p, tmp_id);
					
						JOptionPane.showMessageDialog(null, "Updated");
						
					}
					else
						{
						JOptionPane.showMessageDialog(null, "Priority must be betwee 1 and 10");
						}
					
					
					//pstmt.close();
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnSubmit.setBounds(487, 419, 80, 25);
		contentPane.add(btnSubmit);
		
		txtSearch = new JTextField();
		txtSearch.setBackground(new Color(248, 248, 255));
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				try {
					
					dbconn.connectToDB();
					String query = "select * from Patients where lastname=?";
					PreparedStatement pstmt = connection.prepareStatement(query);
					pstmt.setString(1, txtSearch.getText());
					ResultSet rs = pstmt.executeQuery();
					
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
					rs.close();
					dbconn.closeConnection();
				}
				catch(Exception e3) {
					e3.printStackTrace();
				}
			}
		});
		txtSearch.setBounds(100, 51, 137, 23);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Search by Lastname");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel.setBounds(100, 31, 155, 14);
		contentPane.add(lblNewLabel);
		
		txtFirstname = new JTextField();
		txtFirstname.setEditable(false);
		txtFirstname.setFont(new Font("Times New Roman", Font.BOLD, 14));
		txtFirstname.setForeground(new Color(255, 69, 0));
		txtFirstname.setBackground(new Color(230, 230, 250));
		txtFirstname.setBounds(107, 129, 130, 25);
		contentPane.add(txtFirstname);
		txtFirstname.setColumns(10);
		
		txtLastname = new JTextField();
		txtLastname.setEditable(false);
		txtLastname.setFont(new Font("Times New Roman", Font.BOLD, 14));
		txtLastname.setForeground(new Color(255, 69, 0));
		txtLastname.setBackground(new Color(230, 230, 250));
		txtLastname.setBounds(107, 165, 130, 25);
		contentPane.add(txtLastname);
		txtLastname.setColumns(10);
		
		txtid = new JTextField();
		txtid.setEditable(false);
		txtid.setFont(new Font("Times New Roman", Font.BOLD, 14));
		txtid.setForeground(new Color(255, 69, 0));
		txtid.setBackground(new Color(230, 230, 250));
		txtid.setBounds(107, 94, 130, 25);
		contentPane.add(txtid);
		txtid.setColumns(10);
		
		lblid = new JLabel("id :\r\n");
		lblid.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblid.setBounds(82, 97, 27, 14);
		contentPane.add(lblid);
		
		lblNewLabel_1 = new JLabel("Firstname :");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_1.setBounds(31, 133, 80, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblLastname = new JLabel("Lastname :");
		lblLastname.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblLastname.setBounds(31, 177, 80, 14);
		contentPane.add(lblLastname);
		
		JLabel lblNewLabel_2 = new JLabel("Age :");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_2.setBounds(67, 202, 39, 28);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Health Overview");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_3.setBounds(315, 228, 111, 24);
		contentPane.add(lblNewLabel_3);
		
		lblPriorityNo = new JLabel("Priority No. ");
		lblPriorityNo.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblPriorityNo.setBounds(218, 229, 87, 23);
		contentPane.add(lblPriorityNo);
		
		txtQueue = new JTextField();
		txtQueue.setBackground(new Color(230, 230, 250));
		txtQueue.setEditable(false);
		txtQueue.setBounds(626, 216, 39, 20);
		contentPane.add(txtQueue);
		txtQueue.setColumns(10);
		
		txtPriority = new JTextField();
		txtPriority.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) 
			{
				char c = e.getKeyChar();
				if(!(Character.isDigit(c)|| (c==KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE))
				{
					getToolkit().beep();
					e.consume();
				}
			}
		});
		txtPriority.setHorizontalAlignment(SwingConstants.CENTER);
		txtPriority.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(230, 230, 250), new Color(169, 169, 169)));
		txtPriority.setBounds(228, 250, 40, 25);
		contentPane.add(txtPriority);
		txtPriority.setColumns(10);
		
		txtAge = new JTextField();
		txtAge.setEditable(false);
		txtAge.setHorizontalAlignment(SwingConstants.CENTER);
		txtAge.setForeground(new Color(255, 69, 0));
		txtAge.setBackground(new Color(230, 230, 250));
		txtAge.setFont(new Font("Times New Roman", Font.BOLD, 14));
		txtAge.setBounds(107, 202, 40, 25);
		contentPane.add(txtAge);
		txtAge.setColumns(10);
		
		JButton btnNewButton = new JButton("==>");
		btnNewButton.setBackground(Color.GRAY);
		btnNewButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					String query = "select * from Patients";
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
						
						dll.Add(patient);
									
					}
					
					rs.close();
					
				}
		
				catch(Exception e2) 
				{
						e2.printStackTrace();	
				}

				
				if(n == null || n.getNext() == null)
				{
					n = dll.getNode(1);
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
				txtAssessment.setText(p.GetHealth());
				txtPriority.setText(Integer.toString(p.GetPriority()));
		
			}
		});
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
		btnNewButton.setBounds(607, 418, 75, 25);
		contentPane.add(btnNewButton);
		
		JLabel lblQueue = new JLabel("Queue : ");
		lblQueue.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblQueue.setBounds(570, 216, 54, 14);
		contentPane.add(lblQueue);
		
		JLabel lblNewLabel_4 = new JLabel("(Add Vital Signs here)");
		lblNewLabel_4.setBounds(421, 234, 130, 14);
		contentPane.add(lblNewLabel_4);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(311, 250, 386, 158);
		contentPane.add(scrollPane);
		
		txtAssessment = new JTextArea();
		scrollPane.setViewportView(txtAssessment);
		txtAssessment.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//OverallHealth = OverallHealth + txtAssessment.getText();
				txtAssessment.setText(" ");
			}
		});
		txtAssessment.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, new Color(211, 211, 211)));
		
		GetJTable();
		
		txtQueue.setText(Integer.toString(dbconn.GetQueue()));
		
	}
}
