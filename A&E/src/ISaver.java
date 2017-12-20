import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.text.TableView;

public interface ISaver 
{	
	public void Add(Patients patient);
	
	public Connection UpdateReception(Patients p, int id);
	public Connection UpdateNurse(Patients p, int id);
	public Connection UpdateDoctor(Patients p, int id);
	
	public Connection connectToDB();

	public void closeConnection();

	public int GetQueue();

}
