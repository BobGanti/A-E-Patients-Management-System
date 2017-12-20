
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataBaseConnection implements ISaver
{
	Connection connection = null;
	Statement stmt = null;
	private String tableName;
	
	//************ Get connection to DB ************************
	public Connection connectToDB()
	{		
		try 
		{
			//Connect to the DB
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:DLListDB.sqlite");
			return connection;
		}
		catch(Exception e) {
			return null;
		}				
	}

	public Patients readTable() 
	{	
		Patients  p = null;;
		try
		{
			connectToDB();
			this.stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Patients");	
			
			while(rs.next()) 
			{
				
				int id = rs.getInt("id");
				String firstname = rs.getString("Firstname");
				String lastname = rs.getString("Lastname");
				int age = rs.getInt("Age");
				String health = rs.getString("Health");
				int priority = rs.getInt("Priority");
				String nurseAssessment = rs.getString("Assessment");
				String treatment = rs.getString("Treatment");
				
				p = new Patients(id, firstname, lastname, age, health, priority, nurseAssessment, treatment);				
				
			}		
			
			rs.close();
			closeConnection();
			return p;
		}
				
		catch(Exception e) 
		{
			System.err.println("Error!" + e.getMessage());					
		}
		return null;

	} 

	//**************************** Add patient ***********************************
	public void Add(Patients patient)
	{
		connectToDB();
		//this.tableName = tableName; 

		String savePatientQuery = "INSERT INTO Patients (Firstname, Lastname, Age, Health) VALUES ('"+patient.GetFirstname()+"', "+ "'"+patient.GetLastname()+"', " + "'"+patient.GetAge()+"', " + "'"+patient.GetHealth()+"'"+")";
		
		ExecuteQuery(savePatientQuery);
		
		closeConnection();
		
	}
	
//*************************** Update patient ******************************************************************************************
		//******** Updates from the Reception Panel *********
	public Connection UpdateReception(Patients patient, int id) 
	{
		try
		{			
			connectToDB();	
			
			String receptionQuery = "UPDATE Patients SET Firstname='"+patient.GetFirstname()+"' ,Lastname='"+patient.GetLastname()+"' ,"
					+ "Age='"+patient.GetAge()+"' ,Health='"+patient.GetHealth()+"' "
							+ "WHERE id='"+id+"'";	
			
			PreparedStatement pstmt = connection.prepareStatement(receptionQuery);
			pstmt.execute();
			
			
			pstmt.close();
			closeConnection();
			return connection;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	//************ Updates from Nurse Panel *************
	public Connection UpdateNurse(Patients patient, int id) 
	{
		try
		{			
			connectToDB();	
			
			String nurseQuery = "UPDATE Patients SET Priority='"+patient.GetPriority()+"' ,Assessment='"+patient.GetNurseAssessment()+"' "				
							+ "WHERE id='"+id+"'";	
			
			PreparedStatement pstmt = connection.prepareStatement(nurseQuery);
			pstmt.execute();
						
			pstmt.close();
			closeConnection();
			return connection;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	//************ Updates from Doctor Panel *************
	public Connection UpdateDoctor(Patients patient, int id) 
	{
		try
		{			
			connectToDB();	
			
			String doctorQuery = "UPDATE Patients SET Treatment='"+patient.GetTreatment()+"' "				
							+ "WHERE id='"+id+"'";	
			
			PreparedStatement pstmt = connection.prepareStatement(doctorQuery);
			pstmt.execute();
						
			pstmt.close();
			closeConnection();
			return connection;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public Connection Delete(Patients p, int id)
	{
		try 
		{
			connectToDB();
			String deleteQuery = "delete from Patients where id = '"+id+"'";
			
			PreparedStatement pstmt = connection.prepareStatement(deleteQuery);
			pstmt.execute();
			
			pstmt.close();
			closeConnection();
			return connection;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public Connection ChangeTable(Patients patient, int id)
	{
		try {
			
			connectToDB();
			//Patients tmp_patient = readTable();
			
			//this.tableName = tableName; 
	
			String changeTQuery = "INSERT INTO Treated_Patients (Firstname, Lastname, Age, Health, Priority, Assessment, Treatment) VALUES ('"+patient.GetFirstname()+"', "+ "'"+patient.GetLastname()+"', " + "'"+patient.GetAge()+"', " + "'"+patient.GetHealth()+"', " + "'"+patient.GetPriority()+"', " + "'"+patient.GetNurseAssessment()+"', " + "'"+patient.GetTreatment()+"'"+")";
			ExecuteQuery(changeTQuery);
			
			
			closeConnection();
			return connection;
		}
		catch(Exception e)
		{
			return null;
		}
		
	}
	
	public void ExecuteQuery(String query) 
	{
		
		try 
		{
			this.stmt = connection.createStatement();
			stmt.executeQuery(query);
		}
		catch(Exception e) 
		{
			//Error
		}
		
	}
	
	//******* Close connection *************
	public void closeConnection() 
	{
		try 
		{
			connection.close();
		}
		catch(Exception e) 
		{
			// Error
		}
	}

	//******** Returns count of DB entries *********************
	@Override
	public int GetQueue()
	{
		int count = 0;
		try
		{
			// Get size
			connectToDB();
			
			String queryCount = "SELECT COUNT(*) AS rowcount FROM Patients";
			PreparedStatement stmt = connection.prepareStatement(queryCount);
			ResultSet r = stmt.executeQuery();
			r.next();
			count = r.getInt("rowCount");			
			
			closeConnection();
		}
		catch(Exception e2) 
		{
			e2.printStackTrace();
		}
		return count;
	}
	
}	
	
	
	
	
	
	
	