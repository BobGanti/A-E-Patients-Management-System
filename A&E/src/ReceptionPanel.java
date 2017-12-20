import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.UIManager;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class ReceptionPanel extends JFrame {

	private JPanel ReceptionPanel;
	private JTable table;
	private JTextField txtPatientFname;
	private JTextField txtPatientLname;
	private JTextField txtPatientAge;
	private JTextField txtNewdllSize;
	private JTextField txtQueue;
	private JTextField txtid;
	Connection connection = null;
	Node n = null;
	private JTextField txtAgeSave;
	private JTextField txtLnSave;
	private JTextField txtFnSave;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReceptionPanel frame = new ReceptionPanel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public ReceptionPanel() 
	{
		setTitle("Reception");
		setResizable(false);
		
		ISaver dll = new DoublyLinkedList();
		DoublyLinkedList Ndll = new DoublyLinkedList();
		ISaver dbconn = new DataBaseConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 759, 510);
		ReceptionPanel = new JPanel();
		ReceptionPanel.setBackground(Color.LIGHT_GRAY);
		ReceptionPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, Color.GRAY));
		setContentPane(ReceptionPanel);
		ReceptionPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Patient's Health Condition");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setBounds(129, 11, 240, 31);
		ReceptionPanel.add(lblNewLabel);
		
		table = new JTable();
		table.setBounds(128, 190, 1, 1);
		ReceptionPanel.add(table);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 40, 435, 155);
		ReceptionPanel.add(scrollPane);
		
		JTextArea txtCondition = new JTextArea();
		scrollPane.setViewportView(txtCondition);
		txtCondition.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				txtCondition.setText("");
			}
		});
		
		JPanel panelRec = new JPanel();
		panelRec.setBackground(UIManager.getColor("Slider.focus"));
		panelRec.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(112, 128, 144), new Color(128, 128, 128)));
		panelRec.setBounds(463, 40, 275, 435);
		ReceptionPanel.add(panelRec);
		panelRec.setLayout(null);
		
		JLabel lblQueue = new JLabel("New List");
		lblQueue.setHorizontalAlignment(SwingConstants.CENTER);
		lblQueue.setBounds(44, 12, 75, 17);
		panelRec.add(lblQueue);
		lblQueue.setForeground(new Color(0, 0, 0));
		lblQueue.setFont(new Font("Times New Roman", Font.BOLD, 16));
		
		txtNewdllSize = new JTextField();
		txtNewdllSize.setBounds(56, 30, 50, 25);
		panelRec.add(txtNewdllSize);
		txtNewdllSize.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		txtNewdllSize.setBackground(new Color(230, 230, 250));
		txtNewdllSize.setEditable(false);
		txtNewdllSize.setHorizontalAlignment(SwingConstants.CENTER);
		txtNewdllSize.setColumns(10);
		
		JLabel lblQueue_1 = new JLabel("Queue");
		lblQueue_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblQueue_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblQueue_1.setBounds(168, 12, 46, 14);
		panelRec.add(lblQueue_1);
		
		txtQueue = new JTextField();
		txtQueue.setBounds(168, 30, 50, 25);
		panelRec.add(txtQueue);
		txtQueue.setHorizontalAlignment(SwingConstants.CENTER);
		txtQueue.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		txtQueue.setEditable(false);
		txtQueue.setColumns(10);
		txtQueue.setBackground(new Color(230, 230, 250));
		
		//(Update Patient NB: Only the Receptionist can Update personal details)
//********************************************************************************************		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				try {	
					Patients p = new Patients();
					//connection = dbconn.connectToDB();
					
					int tmp_id = Integer.parseInt(txtid.getText());
					String tmp_fname = txtPatientFname.getText();
					String tmp_lname = txtPatientLname.getText();
					int tmp_age = Integer.parseInt(txtPatientAge.getText());					
					String tmp_health = txtCondition.getText() + "\n ***********************\n\n";
				/*	
					String updatePatientQuery = "UPDATE Patients SET id='"+txtid.getText()+"' ,"
							+ "Firstname='"+txtPatientFname.getText()+"' ,Lastname='"+tmp_lname+"' ,"
							+ "Age='"+txtPatientAge.getText()+"',Health='"+tmp_health+"' "
									+ "WHERE id='"+txtid.getText()+"'";	
					*/
					p.GetId();
					p.SetFirstname(tmp_fname);
					p.SetLastname(tmp_lname);
					p.SetAGe(tmp_age);
					p.SetHealth(tmp_health);
					
					Connection connection = dbconn.UpdateReception(p, tmp_id);
			
					//PreparedStatement pstmt = connection.prepareStatement(updatePatientQuery);
					//pstmt.execute();
					
					JOptionPane.showMessageDialog(null, "Data Updated");										
				
					txtid.setText("");
					txtPatientFname.setText("");
					txtPatientLname.setText("");
					txtPatientAge.setText("");
					txtCondition.setText("");
					
					//pstmt.close();
					//dbconn.closeConnection();
				}
				catch(Exception e1)
				{
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnUpdate.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnUpdate.setBounds(21, 339, 80, 30);
		panelRec.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					int tmp_id = Integer.parseInt(txtid.getText());
					String tmp_fname =txtPatientFname.getText();
					String tmp_lname = txtPatientLname.getText();
					int tmp_age = Integer.parseInt(txtPatientAge.getText());
					
					Patients p = new Patients();
					p.GetId();
					p.SetFirstname(tmp_fname);
					p.SetLastname(tmp_lname);
					p.SetAGe(tmp_age);
				
					
					connection = ((DataBaseConnection) dbconn).Delete(p, tmp_id);
					
					JOptionPane.showMessageDialog(null,  tmp_fname+ " " + tmp_lname + " Deleted!");
					
					//Connection delConnection  = ((DataBaseConnection) dbconn).ChangeTable(p, tmp_id);
				}
			
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, ex);
				
				}
			}
		});
		btnDelete.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnDelete.setBounds(177, 339, 80, 30);
		panelRec.add(btnDelete);		
		//********************************************************************************************	
		
			// (Traversing the dll to next patient)
		//*********************************************************************************************
			JButton button = new JButton("==>");
			button.setBounds(101, 399, 75, 25);
			panelRec.add(button);
			button.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					try 
					{
						connection = dbconn.connectToDB();
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
						n = ((DoublyLinkedList) dll).getNode(1);
					}
					
					else
					{
						n = n.getNext();
					}
					
					Patients p = (Patients)n.getData();

					txtid.setText(Integer.toString(p.GetId()));
					txtPatientFname.setText(p.GetFirstname());
					txtPatientLname.setText(p.GetLastname());
					txtPatientAge.setText(Integer.toString(p.GetAge()));
					txtCondition.setText("\t"+p.GetHealth());
					
					dbconn.closeConnection();
				}
			});
	//********************************************************************************************			
			button.setFont(new Font("Times New Roman", Font.BOLD, 20));
			
			int queue = dbconn.GetQueue();
			txtQueue.setText(Integer.toString(queue));
			
			JPanel panel = new JPanel();
			panel.setBounds(0, 127, 275, 201);
			panelRec.add(panel);
			panel.setLayout(null);
			panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(112, 128, 144), new Color(128, 128, 128)));
			panel.setBackground(UIManager.getColor("Slider.focus"));
			
		
			txtPatientAge = new JTextField();
			txtPatientAge.addKeyListener(new KeyAdapter() {
				
	//****************<-- Validating Input* Makes sound if character is not digit -->**************************
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
	//*************************************************************************************************************
			});
			txtPatientAge.setBounds(105, 160, 45, 25);
			panel.add(txtPatientAge);
			txtPatientAge.setColumns(10);
			
			txtPatientLname = new JTextField();
			txtPatientLname.setBounds(105, 115, 140, 25);
			panel.add(txtPatientLname);
			txtPatientLname.setColumns(10);
			
			txtPatientFname = new JTextField();
			txtPatientFname.setBounds(105, 70, 140, 25);
			panel.add(txtPatientFname);
			txtPatientFname.setColumns(10);
			
			txtid = new JTextField();
			txtid.setBackground(new Color(230, 230, 250));
			txtid.setEditable(false);
			txtid.setBounds(105, 25, 45, 25);
			panel.add(txtid);
			txtid.setColumns(10);
			
			JLabel lblPatientName = new JLabel("Firstname :");
			lblPatientName.setBounds(24, 70, 72, 31);
			panel.add(lblPatientName);
			lblPatientName.setFont(new Font("Tahoma", Font.BOLD, 12));
			
			JLabel lblPatientLastname = new JLabel("Lastname :");
			lblPatientLastname.setBounds(24, 117, 72, 31);
			panel.add(lblPatientLastname);
			lblPatientLastname.setFont(new Font("Tahoma", Font.BOLD, 12));
			
			JLabel lblNewLabel_1 = new JLabel("Age :");
			lblNewLabel_1.setBounds(59, 162, 37, 21);
			panel.add(lblNewLabel_1);
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
			
			JLabel lblId = new JLabel("id :");
			lblId.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblId.setBounds(73, 28, 37, 21);
			panel.add(lblId);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(28, 195, 275, 280);
			ReceptionPanel.add(panel_1);
			panel_1.setLayout(null);
			panel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(112, 128, 144), new Color(128, 128, 128)));
			panel_1.setBackground(SystemColor.controlDkShadow);
			
			//****************<-- Validating Input* Makes sound if character is not digit -->**************************	
			txtAgeSave = new JTextField();
			txtAgeSave.addKeyListener(new KeyAdapter() {
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
			txtAgeSave.setColumns(10);
			txtAgeSave.setBounds(105, 160, 45, 25);
			panel_1.add(txtAgeSave);
			
			txtLnSave = new JTextField();
			txtLnSave.setColumns(10);
			txtLnSave.setBounds(105, 115, 140, 25);
			panel_1.add(txtLnSave);
			
			txtFnSave = new JTextField();
			txtFnSave.setColumns(10);
			txtFnSave.setBounds(105, 70, 140, 25);
			panel_1.add(txtFnSave);
			
			JLabel label = new JLabel("Firstname :");
			label.setFont(new Font("Tahoma", Font.BOLD, 12));
			label.setBounds(24, 70, 72, 31);
			panel_1.add(label);
			
			JLabel label_1 = new JLabel("Lastname :");
			label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
			label_1.setBounds(24, 117, 72, 31);
			panel_1.add(label_1);
			
			JLabel label_2 = new JLabel("Age :");
			label_2.setFont(new Font("Tahoma", Font.BOLD, 12));
			label_2.setBounds(59, 162, 37, 21);
			panel_1.add(label_2);
			
			JButton btnSaveReception = new JButton("Save");
			btnSaveReception.setBounds(105, 222, 72, 33);
			panel_1.add(btnSaveReception);
			btnSaveReception.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) 
				{
					String tmp_fname = txtFnSave.getText();
					String tmp_lname = txtLnSave.getText();
					int tmp_age = Integer.parseInt(txtAgeSave.getText());
					String tmp_condtion = "\n\tHealth Condition\n  ***********************************************\n " 
					+ txtCondition.getText()+"\n\n";
					
					
					Patients patient = new Patients(tmp_fname,  tmp_lname, tmp_age, tmp_condtion);
					
					 // New registration session
					Ndll.Add(patient);
					
					dbconn.Add(patient); // Persisting the newly registered patients to DB
					JOptionPane.showMessageDialog(null, "Data Saved");
					
					// (Reset textFields to empty strings)
					txtFnSave.setText("");
					txtLnSave.setText("");
					txtAgeSave.setText("");
					txtCondition.setText("");
					
					//(Dispay size of the new registration session)/ Read only
					txtNewdllSize.setText(Integer.toString(Ndll.size())); 
					
					txtQueue.setText(Integer.toString(dbconn.GetQueue()));
	
				}

				private void print(String string) {
					// TODO Auto-generated method stub
					
				}
			});
			btnSaveReception.setFont(new Font("Times New Roman", Font.BOLD, 18));
		
	}	
}
